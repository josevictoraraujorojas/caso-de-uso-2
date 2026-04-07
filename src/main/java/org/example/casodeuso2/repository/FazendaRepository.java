package org.example.casodeuso2.repository;

import org.example.casodeuso2.model.Fazenda;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FazendaRepository extends Neo4jRepository<Fazenda,Long> {

}
