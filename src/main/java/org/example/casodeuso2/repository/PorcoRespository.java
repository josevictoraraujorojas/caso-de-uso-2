package org.example.casodeuso2.repository;

import org.example.casodeuso2.model.Porco;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PorcoRespository extends Neo4jRepository<Porco, Long> {
}
