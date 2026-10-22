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

## Documentação da API (Swagger / OpenAPI)

Após iniciar a aplicação, acesse a documentação interativa:

- **Swagger UI:** http://localhost:8080/swagger-ui/index.html
- **Especificação OpenAPI (JSON):** http://localhost:8080/v3/api-docs

Se a aplicação estiver configurada em outra porta, substitua `8080` pela porta utilizada. O Springdoc identifica os endpoints dos controllers e gera os esquemas dos DTOs usados como parâmetros e respostas.
