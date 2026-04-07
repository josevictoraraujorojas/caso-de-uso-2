package org.example.casodeuso2.repository;

import org.example.casodeuso2.model.VariavelAmbiente;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariavelAmbienteRepository extends Neo4jRepository<VariavelAmbiente, Long> {
}
