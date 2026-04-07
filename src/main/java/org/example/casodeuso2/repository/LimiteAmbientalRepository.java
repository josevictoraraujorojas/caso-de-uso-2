package org.example.casodeuso2.repository;

import org.example.casodeuso2.model.LimiteAmbiental;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LimiteAmbientalRepository extends Neo4jRepository<LimiteAmbiental, Long> {

}
