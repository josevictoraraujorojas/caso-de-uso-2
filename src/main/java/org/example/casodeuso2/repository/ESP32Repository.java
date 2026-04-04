package org.example.casodeuso2.repository;

import org.example.casodeuso2.model.ESP32;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ESP32Repository extends Neo4jRepository<ESP32, Long> {

    List<ESP32> findByNome(String nome);

    ESP32 findByMacAddress(String macAddress);
}

