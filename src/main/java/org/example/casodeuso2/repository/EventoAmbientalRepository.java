package org.example.casodeuso2.repository;

import org.example.casodeuso2.model.EventoAmbiental;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoAmbientalRepository extends Neo4jRepository<EventoAmbiental, Long> {
}
