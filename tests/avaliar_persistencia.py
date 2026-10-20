#!/usr/bin/env python3
"""Compara mensagens de entrada com registros exportados do armazenamento.

Entradas e registros persistidos devem ser arquivos JSONL normalizados, com um
objeto JSON por linha e os nomes de campos previstos no tipo selecionado.
O script não acessa os bancos diretamente; ele analisa evidências exportadas.
"""
import argparse
import collections
import json
import math
import re
import sys
from pathlib import Path

MAC_RE = re.compile(r"^(?:[0-9A-Fa-f]{2}:){5}[0-9A-Fa-f]{2}$")
SCHEMAS = {
    "energia": {
        "required": ["tensao", "corrente", "potencia", "energiaKwh", "frequencia", "fatorPotencia", "esp32Mac"],
        "numeric": ["tensao", "corrente", "potencia", "energiaKwh", "frequencia", "fatorPotencia"],
        "mac": ["esp32Mac"],
        "identity": ["tensao", "corrente", "potencia", "energiaKwh", "frequencia", "fatorPotencia", "esp32Mac"],
    },
    "wifi": {
        "required": ["cliente", "bssid", "rssi", "canal", "tamanho"],
        "numeric": ["rssi", "canal", "tamanho"],
        "mac": ["cliente", "bssid"],
        "identity": ["cliente", "bssid", "rssi", "canal", "tamanho"],
    },
    "ambiente": {
        "required": ["valor", "campo", "sensor", "esp32Mac"],
        "numeric": ["valor"],
        "mac": ["esp32Mac"],
        "identity": ["valor", "campo", "sensor", "esp32Mac"],
    },
}


def extrair_json(linha):
    inicio = linha.find("{")
    if inicio < 0:
        return None, "sem_json"
    try:
        objeto, _ = json.JSONDecoder().raw_decode(linha[inicio:])
    except json.JSONDecodeError as exc:
        return None, f"json_invalido: {exc.msg}"
    if not isinstance(objeto, dict):
        return None, "o payload JSON não é um objeto"
    return objeto, None


def validar(dado, schema):
    faltantes = [chave for chave in schema["required"] if chave not in dado]
    if faltantes:
        return "campos_ausentes: " + ", ".join(faltantes)
    for chave in schema["numeric"]:
        valor = dado[chave]
        if isinstance(valor, bool) or not isinstance(valor, (int, float)):
            return f"campo_nao_numerico: {chave}"
        if not math.isfinite(float(valor)):
            return f"campo_nao_finito: {chave}"
    for chave in schema["mac"]:
        valor = dado[chave]
        if not isinstance(valor, str) or not MAC_RE.fullmatch(valor):
            return f"mac_invalido: {chave}"
    return None


def normalizar_valor(chave, valor, schema):
    if chave in schema["numeric"]:
        return round(float(valor), 6)
    if chave in schema["mac"] and isinstance(valor, str):
        return valor.upper()
    return valor


def assinatura(dado, schema):
    return tuple((chave, normalizar_valor(chave, dado[chave], schema))
                 for chave in schema["identity"])


def ler_arquivo(caminho, schema):
    validos = []
    invalidos = []
    ignoradas = 0
    with caminho.open("r", encoding="utf-8", errors="replace") as arquivo:
        for numero, linha in enumerate(arquivo, start=1):
            dado, erro = extrair_json(linha)
            if erro == "sem_json":
                ignoradas += 1
                continue
            if erro:
                invalidos.append({"linha": numero, "motivo": erro})
                continue
            erro = validar(dado, schema)
            if erro:
                invalidos.append({"linha": numero, "motivo": erro})
            else:
                validos.append(dado)
    return validos, invalidos, ignoradas


def executar_autoteste():
    for tipo, schema in SCHEMAS.items():
        amostra = {}
        for chave in schema["required"]:
            if chave in schema["numeric"]:
                amostra[chave] = 12.5
            elif chave in schema["mac"]:
                amostra[chave] = "AA:BB:CC:DD:EE:FF"
            elif chave == "campo":
                amostra[chave] = "temperatura"
            elif chave == "sensor":
                amostra[chave] = "dht22"
            else:
                amostra[chave] = "teste"
        if tipo == "ambiente":
            amostra["campo"] = "temperatura"
            amostra["sensor"] = "dht22"
        assert validar(amostra, schema) is None, f"amostra {tipo} foi rejeitada"
        assert assinatura(amostra, schema) == assinatura(dict(amostra), schema)
    print("AUTOTESTE OK: esquemas e comparação de registros verificados.")
    print("Observação: isso testa o comparador, não prova que a aplicação gravou dados.")
    return 0


def main():
    parser = argparse.ArgumentParser(description="Compara mensagens de entrada e registros persistidos.")
    parser.add_argument("--tipo", choices=sorted(SCHEMAS), help="energia, wifi ou ambiente.")
    parser.add_argument("--entrada", type=Path, help="JSONL das mensagens recebidas/enviadas ao armazenamento.")
    parser.add_argument("--persistidos", type=Path, help="JSONL normalizado exportado do InfluxDB.")
    parser.add_argument("--saida", type=Path, help="Caminho opcional para salvar o resumo JSON.")
    parser.add_argument("--autoteste", action="store_true")
    args = parser.parse_args()

    if args.autoteste:
        return executar_autoteste()
    if not args.tipo or not args.entrada or not args.persistidos:
        parser.error("informe --tipo, --entrada e --persistidos, ou use --autoteste")
    for caminho in (args.entrada, args.persistidos):
        if not caminho.is_file():
            parser.error(f"arquivo não encontrado: {caminho}")

    schema = SCHEMAS[args.tipo]
    entrada, erros_entrada, ignoradas_entrada = ler_arquivo(args.entrada, schema)
    persistidos, erros_persistidos, ignoradas_persistidas = ler_arquivo(args.persistidos, schema)

    contagem_entrada = collections.Counter(assinatura(item, schema) for item in entrada)
    contagem_persistidos = collections.Counter(assinatura(item, schema) for item in persistidos)
    correspondencias = sum((contagem_entrada & contagem_persistidos).values())
    faltantes = sum((contagem_entrada - contagem_persistidos).values())
    extras = sum((contagem_persistidos - contagem_entrada).values())
    total_entrada = len(entrada)

    resumo = {
        "tipo": args.tipo,
        "entrada": str(args.entrada),
        "persistidos": str(args.persistidos),
        "mensagens_entrada_validas": total_entrada,
        "registros_persistidos_validos": len(persistidos),
        "correspondencias": correspondencias,
        "nao_encontradas_no_armazenamento": faltantes,
        "registros_extras_ou_duplicados": extras,
        "mensagens_entrada_invalidas": len(erros_entrada),
        "registros_persistidos_invalidos": len(erros_persistidos),
        "linhas_ignoradas_entrada": ignoradas_entrada,
        "linhas_ignoradas_persistidos": ignoradas_persistidas,
        "taxa_persistencia_percentual": round(100 * len(persistidos) / total_entrada, 2) if total_entrada else 0.0,
        "taxa_integridade_percentual": round(100 * correspondencias / total_entrada, 2) if total_entrada else 0.0,
        "detalhes_invalidos_entrada": erros_entrada[:30],
        "detalhes_invalidos_persistidos": erros_persistidos[:30],
        "observacao": "Comparação por campos selecionados; timestamps do banco não são comparados porque podem ser transformados na persistência.",
    }
    texto = json.dumps(resumo, ensure_ascii=False, indent=2)
    print(texto)
    if args.saida:
        args.saida.parent.mkdir(parents=True, exist_ok=True)
        args.saida.write_text(texto + "\n", encoding="utf-8")

    falhas = (faltantes > 0 or extras > 0 or erros_entrada or erros_persistidos or total_entrada == 0)
    return 1 if falhas else 0


if __name__ == "__main__":
    sys.exit(main())
