package org.example.casodeuso2.repository;

import com.influxdb.client.DeleteApi;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import org.example.casodeuso2.model.AmbienteData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AmbienteRepositoryImpl implements AmbienteRepository {

    private final InfluxDBClient influxDBClient;

    @Value("${influx.bucket}")
    private String bucket;

    @Value("${influx.org}")
    private String org;

    public AmbienteRepositoryImpl(InfluxDBClient influxDBClient) {
        this.influxDBClient = influxDBClient;
    }

    @Override
    public void salvarSensorData(AmbienteData data) {
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        data.setTimestamp(Instant.now());
        writeApi.writeMeasurement(WritePrecision.MS, data);
    }

    @Override
    public List<AmbienteData> consultarSensoresData() {

        String flux = String.format(
                "from(bucket:\"%s\") " +
                        "|> range(start: 0) " +
                        "|> filter(fn: (r) => r[\"_measurement\"] == \"medicoes_ambientais\") " +
                        "|> sort(columns: [\"_time\"], desc: false) " +
                        "|> yield(name: \"sorted\")",
                bucket
        );

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(flux, org);

        List<AmbienteData> dados = new ArrayList<>();

        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {

                String esp32Id = (String) record.getValueByKey("esp32_id");
                String field = (String) record.getValueByKey("_field");
                Object value = record.getValueByKey("_value");

                Instant timestamp = record.getTime();

                AmbienteData existente = dados.stream()
                        .filter(d -> d.getEsp32Id().equals(esp32Id) && d.getTimestamp().equals(timestamp))
                        .findFirst()
                        .orElseGet(() -> {
                            AmbienteData novo = new AmbienteData();
                            novo.setEsp32Id(esp32Id);
                            novo.setTimestamp(timestamp);
                            dados.add(novo);
                            return novo;
                        });

                if (field != null && value instanceof Number) {
                    double val = ((Number) value).doubleValue();
                    if (field.equals("temperatura")) {
                        existente.setTemperatura(val);
                    } else if (field.equals("humidade")) {
                        existente.setHumidade(val);
                    } else if (field.equals("qualidad_ar")) {
                        existente.setQualidadeAr(val);
                    }
                }
            }
        }
        return dados;
    }

    @Override
    public void deletarSensorData(String esp32Id) {
        if (esp32Id == null || esp32Id.isEmpty()) {
            throw new IllegalArgumentException("sensorId não pode ser nulo ou vazio");
        }

        DeleteApi deleteApi = influxDBClient.getDeleteApi();

        try {
            OffsetDateTime start = OffsetDateTime.parse("1970-01-01T00:00:00Z");  // Data muito antiga
            OffsetDateTime stop = OffsetDateTime.now();  // Data atual

            String predicate = "_measurement=\"sensor\" AND sensor_id = \"" + esp32Id + "\"";

            deleteApi.delete(start, stop, predicate, bucket, org);

            System.out.println("Dados do sensor " + esp32Id + " deletados com sucesso.");

        } catch (Exception e) {

            System.err.println("Erro ao deletar dados do sensor: " + e.getMessage());
        }
    }

}

