# Procedimento experimental — backend do Caso de Uso 2

O backend recebe medições ambientais por MQTT, associa os valores a dispositivo, curral, sensor e variável ambiental, e grava as séries temporais no InfluxDB. O objetivo é observar o caminho da mensagem nos logs e confirmar a persistência separadamente no banco.

## 1. Executar o backend

Na pasta raiz do repositório, execute:

```powershell
mvn spring-boot:run
```

Como alternativa, execute a classe `CasoDeUso2Application` pela IDE e mantenha visível o console.

## 2. Enviar medições e observar o recebimento

Ligue o ESP32 com os sensores e aguarde o aquecimento/calibração previsto no firmware. Em outro terminal, também é possível observar o tópico diretamente no broker:

```powershell
mosquitto_sub -h HOST_DO_BROKER -p 1883 -t ambientedata -v
```

Substitua o host pelo broker correto. No console do backend, procure:

```text
[EXPERIMENTO][MQTT_RECEBIDO] id=abcd1234 topico=ambientedata ... payload={...}
```

A linha contém o identificador interno do backend, o tópico e o payload que chegou. Esse identificador é gerado no recebimento e não faz parte da mensagem MQTT enviada pelo ESP32.

## 3. Acompanhar processamento e persistência no InfluxDB

Procure as marcas abaixo para o mesmo `id`:

- `[EXPERIMENTO][MQTT_RECEBIDO]`: a mensagem foi recebida;
- `[EXPERIMENTO][INFLUX_INICIO]`: a aplicação começou a gravar no measurement `medicoes_ambientais`;
- `[EXPERIMENTO][INFLUX_PERSISTIDO]`: a chamada síncrona de gravação retornou sem erro e informa valor, instante e IDs do dispositivo, sensor, variável, curral e fazenda;
- `[EXPERIMENTO][PROCESSAMENTO_OK]`: o processamento da medição foi concluído;
- `[EXPERIMENTO][FALHA_PROCESSAMENTO]` ou `[EXPERIMENTO][INFLUX_FALHA]`: indica falha a investigar.

## 4. Confirmar a persistência no banco

No Data Explorer do InfluxDB, escolha o bucket `caso_uso_2`, o measurement `medicoes_ambientais` e o intervalo de tempo da execução. Compare o valor, timestamp e tags com a linha `INFLUX_PERSISTIDO`. A consulta independente confirma que os registros podem ser recuperados.

No Neo4j Browser, as consultas abaixo ajudam a identificar os rótulos e as relações presentes no banco:

```cypher
MATCH (n)
RETURN labels(n) AS rotulos, count(*) AS quantidade;
```

```cypher
MATCH (a)-[r]->(b)
RETURN labels(a) AS origem, type(r) AS relacao, labels(b) AS destino, count(*) AS quantidade;
```

Registre a estrutura observada e compare-a com a associação esperada entre ESP32, sensor, variável ambiental, curral e fazenda. Eventos ambientais são gravados no grafo quando as regras de limite são acionadas e não existe evento ativo equivalente; não se espera necessariamente um evento novo para cada medição. Quando um evento é criado, o log `[EXPERIMENTO][NEO4J_EVENTO_PERSISTIDO]` mostra o ID retornado pelo repositório e o contexto do evento.

## 5. Dados experimentais

Em cada repetição, registre as mensagens recebidas no broker, as mensagens recebidas no backend, gravações concluídas, dados encontrados no InfluxDB, associações conferidas no Neo4j e erros. Não infira resultados antes de executar o experimento. O valor calculado pelo MQ-135 é uma estimativa que depende de aquecimento e calibração e não substitui uma referência laboratorial independente.
