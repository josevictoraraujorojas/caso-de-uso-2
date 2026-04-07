package org.example.casodeuso2.repository;

import org.example.casodeuso2.model.Sensor;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends Neo4jRepository<Sensor, Long> {
}
