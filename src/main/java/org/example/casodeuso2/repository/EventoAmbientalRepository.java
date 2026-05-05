package org.example.casodeuso2.repository;

import org.example.casodeuso2.model.EventoAmbiental;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventoAmbientalRepository extends Neo4jRepository<EventoAmbiental, Long> {
    @Query("""
MATCH (e:EventoAmbiental)-[:DERIVA_DE]->(esp:ESP32)
WHERE id(esp) = $esp32Id AND e.tipo = $tipo AND e.ativo = true
RETURN e LIMIT 1
""")
    Optional<EventoAmbiental> buscarEventoAtivo(Long esp32Id, String tipo);

    @Query("""
MATCH (e:EventoAmbiental)
WHERE id(e) = $id
SET e.ativo = false
""")
    void desativarEvento(Long id);
}
