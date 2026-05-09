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

    private static final String MEASUREMENT = "medicoes_ambientais";

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
        data.setTime(Instant.now());

        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        writeApi.writeMeasurement(WritePrecision.MS, data);
    }

    @Override
    public List<AmbienteData> consultarSensoresData() {
        return executarConsulta("");
    }

    @Override
    public List<AmbienteData> consultarPorEsp32(Long esp32Id) {
        return executarConsulta(
                "|> filter(fn: (r) => r.esp32_id == \"" + esp32Id + "\") "
        );
    }

    @Override
    public List<AmbienteData> consultarPorVariavel(Long variavelId) {
        return executarConsulta(
                "|> filter(fn: (r) => r.variavel_id == \"" + variavelId + "\") "
        );
    }

    @Override
    public void deletarSensorData(String esp32Id) {

        if (esp32Id == null || esp32Id.isBlank()) {
            throw new IllegalArgumentException("esp32Id não pode ser vazio");
        }

        try {

            OffsetDateTime start = OffsetDateTime.parse("1970-01-01T00:00:00Z");
            OffsetDateTime stop = OffsetDateTime.now();

            String predicate =
                    "_measurement=\"" + MEASUREMENT + "\" AND esp32_id=\"" + esp32Id + "\"";

            DeleteApi deleteApi = influxDBClient.getDeleteApi();

            deleteApi.delete(start, stop, predicate, bucket, org);

            System.out.println("Dados deletados com sucesso.");

        } catch (Exception e) {
            System.err.println("Erro ao deletar dados: " + e.getMessage());
        }
    }

    private List<AmbienteData> executarConsulta(String filtro) {

        String flux = """
                from(bucket: "%s")
                |> range(start: 0)
                |> filter(fn: (r) => r._measurement == "%s")
                %s
                |> sort(columns: ["_time"], desc: false)
                """.formatted(bucket, MEASUREMENT, filtro);

        QueryApi queryApi = influxDBClient.getQueryApi();

        List<FluxTable> tables = queryApi.query(flux, org);

        List<AmbienteData> resultado = new ArrayList<>();

        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {
                resultado.add(converterRecord(record));
            }
        }

        return resultado;
    }

    private AmbienteData converterRecord(FluxRecord record) {

        AmbienteData data = new AmbienteData();

        data.setTime(record.getTime());

        Object value = record.getValueByKey("_value");

        if (value instanceof Number number) {
            data.setValor(number.floatValue());
        }

        data.setCurralId(toLong(record.getValueByKey("curral_id")));
        data.setEsp32Id(toLong(record.getValueByKey("esp32_id")));
        data.setFazendaId(toLong(record.getValueByKey("fazenda_id")));
        data.setSensorId(toLong(record.getValueByKey("sensor_id")));
        data.setVariavelId(toLong(record.getValueByKey("variavel_id")));

        return data;
    }

    private Long toLong(Object value) {

        if (value == null) {
            return null;
        }

        return Long.parseLong(value.toString());
    }
}