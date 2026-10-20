# Procedimento experimental — backend do Caso de Uso 2

## Objetivo
Comparar mensagens ambientais recebidas com registros exportados do InfluxDB, verificando correspondências dos campos e contagens.

## Captura das mensagens de entrada
O firmware publica as variáveis ambientais no tópico ambientedata. Capture as mensagens durante uma janela de experimento:

    mosquitto_sub -h HOST_DO_BROKER -p 1883 -t ambientedata > entrada_ambiente.jsonl

Use o mesmo broker e intervalo de execução do backend. Anote horário inicial/final, número de mensagens e eventuais reinicializações.

## Preparação do arquivo persistido
Exporte do InfluxDB os registros do mesmo período e normalize para JSONL com um objeto por linha e os campos valor, campo, sensor e esp32Mac. Não invente registros nem modifique valores para obter correspondência. Se a persistência altera nomes ou unidades, registre o mapeamento e adapte a comparação antes de executar.

## Execução
Valide primeiro o comparador:

    py tests\avaliar_persistencia.py --autoteste

Depois compare entrada e persistência:

    py tests\avaliar_persistencia.py --tipo ambiente --entrada entrada_ambiente.jsonl --persistidos persistidos_ambiente.jsonl --saida resultado_ambiente.json

O relatório apresenta quantidade de mensagens válidas, registros persistidos, correspondências, registros ausentes/extras e taxas de persistência e integridade por campos. Divergências causam código de saída diferente de zero.

## Verificação do Neo4j
No Neo4j Browser, consulte os rótulos e relacionamentos existentes e compare-os com as associações previstas entre dispositivo, sensores e local monitorado. Consultas exploratórias sem pressupor rótulos específicos:

    MATCH (n) RETURN labels(n) AS rotulos, count(*) AS quantidade;
    MATCH (a)-[r]->(b) RETURN labels(a) AS origem, type(r) AS relacao, labels(b) AS destino, count(*) AS quantidade;

Registre os resultados e declare previamente o que era esperado para cada dispositivo e local.

## Protocolo sugerido
Realize cinco repetições e procure coletar pelo menos 30 mensagens ambientais por execução. Aguarde o tempo de aquecimento e calibração previsto no firmware antes de iniciar a janela avaliada.

## Limitação
O comparador depende de uma exportação fiel e normalizada do InfluxDB. Ele não acessa os bancos diretamente e não mede a precisão física do DHT22, MQ-135 ou LDR.
