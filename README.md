# Backend — Caso de Uso 2: Monitoramento Ambiental

Este repositório integra o backend do segundo caso de uso do TCC sobre IoT integrado a bancos de dados de séries temporais e bancos orientados a grafos no IF Goiano – Campus Urutaí.

## Objetivo

Receber e processar medições ambientais enviadas por um ESP32 com sensores, relacionadas ao ambiente de criação de suínos. O backend integra a comunicação MQTT à persistência e à associação dos registros com os dispositivos e locais monitorados.

## Contexto da arquitetura

O fluxo geral considerado no TCC é:

```text
Sensores ambientais
       |
       v
      ESP32
       |
       v
  MQTT broker
       |
       v
 Aplicação backend
   |           |
   v           v
InfluxDB     Neo4j
```

O **InfluxDB** armazena medições ao longo do tempo. O **Neo4j** representa entidades e relações, como dispositivos, sensores, ambientes e locais. A aplicação faz a integração entre as mensagens recebidas e os dados de contexto disponíveis.

## Sensores e dados

A montagem do segundo caso de uso contempla:

- **DHT22/AM2301:** temperatura e umidade relativa do ar;
- **MQ-135:** leitura analógica relacionada à qualidade do ar, cuja conversão em concentração depende de calibração;
- **LDR:** leitura relativa da luminosidade.

O firmware publica as medições pelo tópico MQTT `ambientedata`, conforme a configuração documentada. O formato exato dos campos deve permanecer compatível com o produtor ESP32 e com o consumidor da aplicação.

## Tecnologias da arquitetura

- Java e Spring Boot, para a camada de aplicação, conforme a implementação do backend;
- MQTT, para comunicação com o dispositivo;
- InfluxDB, para armazenamento de séries temporais;
- Neo4j, para entidades e relacionamentos.

## Configuração e execução

Configure os endereços, portas, credenciais e identificadores dos serviços de mensageria e armazenamento utilizados pela aplicação. Mantenha credenciais fora do repositório.

Se o projeto utilizar Maven, o comando de execução habitual é:

```bash
./mvnw spring-boot:run
```

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Antes de executar, confira o `pom.xml`, os arquivos de configuração e os requisitos de Java e dos serviços externos. As instruções específicas do código têm prioridade sobre estes exemplos gerais.

## Relação com o firmware

O firmware de aquisição do caso de uso está em [esp-caso-uso-2](https://github.com/josevictoraraujorojas/esp-caso-uso-2).

## Observações

- A disponibilidade dos dados depende da conexão entre ESP32, broker MQTT e backend.
- A qualidade das medições depende dos sensores, da montagem e da calibração.
- Os detalhes de endpoints, classes, variáveis de ambiente e esquema de persistência devem acompanhar a implementação atual do repositório.

## Procedimento dos testes experimentais

### Objetivo e fluxo avaliado

Verificar a recepção das mensagens ambientais publicadas no tópico MQTT `ambientedata`, seu processamento pela aplicação, a escrita das medições no InfluxDB e a criação de eventos/relacionamentos no Neo4j quando prevista pela lógica do caso de uso.

### Preparação

1. Configure e inicie o broker MQTT, InfluxDB e Neo4j.
2. Confira no ambiente local do projeto os endereços, portas, credenciais, bucket/organização e demais parâmetros exigidos pelo backend.
3. Crie uma pasta para os resultados e execute a aplicação na raiz do repositório. No Windows, se houver Maven Wrapper:

```powershell
New-Item -ItemType Directory -Force -Path "$HOME\TCC-Experimentos"
.mvnw.cmd spring-boot:run 2>&1 |
  Tee-Object -FilePath "$HOME\TCC-Experimentos\09-backend-caso2.log"
```

Se não houver wrapper, use `mvn spring-boot:run` com Maven instalado. Inicie o backend antes do firmware [esp-caso-uso-2](https://github.com/josevictoraraujorojas/esp-caso-uso-2).

### Execução do cenário

1. Inicie um assinante no tópico `ambientedata` antes de iniciar o ESP32 e grave a saída em arquivo.
2. Execute o firmware de aquisição ambiental e registre o log serial.
3. Aguarde o marcador de fim do firmware (limite configurado de até 100 mensagens de dados publicadas).
4. Guarde o log do backend durante toda a execução e anote o horário inicial e final.

### Instrumentação e leitura dos logs

Procure pelos seguintes marcadores, quando aplicáveis:

- `[EXPERIMENTO][MQTT_RECEBIDO]`: mensagem recebida pelo listener;
- `[EXPERIMENTO][INFLUX_INICIO]`: início da tentativa de escrita no InfluxDB;
- `[EXPERIMENTO][INFLUX_PERSISTIDO]`: chamada de escrita sem erro reportado;
- `[EXPERIMENTO][NEO4J_EVENTO_PERSISTIDO]`: evento do grafo criado/persistido, conforme a condição implementada;
- `[EXPERIMENTO][PROCESSAMENTO_OK]`: processamento concluído;
- `[EXPERIMENTO][FALHA_PROCESSAMENTO]` ou `[EXPERIMENTO][INFLUX_FALHA]`: falhas registradas.

No PowerShell, filtre os marcadores com:

```powershell
Select-String -Path "$HOME\TCC-Experimentos\09-backend-caso2.log" -Pattern "\[EXPERIMENTO\]"
```

### Conferência dos resultados

- No InfluxDB, consulte o bucket `caso_uso_2`, medição `medicoes_ambientais`, no intervalo de tempo do teste.
- No Neo4j, consulte a quantidade de nós e relacionamentos antes e depois do teste e confira se as entidades/relacionamentos esperados foram criados.
- Registre mensagens observadas no broker, recebidas pelo backend, escritas sem erro, registros efetivamente encontrados no InfluxDB, eventos confirmados no Neo4j e falhas.

A chamada de escrita sem erro não substitui a consulta ao banco. A criação de evento no Neo4j pode depender de condições do processamento e não precisa ocorrer uma vez para cada mensagem ambiental. Use arquivos separados para cada repetição e registre dados preexistentes para não atribuí-los incorretamente ao experimento.
