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

import static org.springframework.expression.common.ExpressionUtils.toLong;

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
        data.setTime(Instant.now());
        writeApi.writeMeasurement(WritePrecision.MS, data);
    }

    @Override
    public List<AmbienteData> consultarSensoresData() {

        String flux =
                "from(bucket: \"" + bucket + "\") " +
                        "|> range(start: 0) " +
                        "|> filter(fn: (r) => r._measurement == \"medicoes_ambientais\") " +
                        "|> sort(columns: [\"_time\"], desc: false)";

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(flux, org);

        List<AmbienteData> resultado = new ArrayList<>();

        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {

                AmbienteData data = new AmbienteData();

                data.setTime(record.getTime());

                Object value = record.getValueByKey("_value");
                if (value instanceof Number n) {
                    data.setValor(n.doubleValue());
                }

                data.setCurralId(toLong(record.getValueByKey("curral_id")));
                data.setEsp32Id(toLong(record.getValueByKey("esp32_id")));
                data.setFazendaId(toLong(record.getValueByKey("fazenda_id")));
                data.setSensorId(toLong(record.getValueByKey("sensor_id")));
                data.setVariavelId(toLong(record.getValueByKey("variavel_id")));

                resultado.add(data);
            }
        }

        return resultado;
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

    private Long toLong(Object value) {
        if (value == null) return null;
        return Long.parseLong(value.toString());
    }

}

