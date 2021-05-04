package de.fhkiel.advancedjava.persistence;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralRepository extends Neo4jRepository<Object, Long> {

    //clean database
    @Query("MATCH (n) DETACH DELETE n")
    void cleanDatabase();
}
