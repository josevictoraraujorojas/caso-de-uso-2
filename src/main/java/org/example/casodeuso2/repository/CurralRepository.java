package org.example.casodeuso2.repository;

import org.example.casodeuso2.model.Curral;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurralRepository extends Neo4jRepository<Curral, Long> {
}
