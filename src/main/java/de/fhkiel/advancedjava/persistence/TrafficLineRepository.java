package de.fhkiel.advancedjava.persistence;

import de.fhkiel.advancedjava.domain.trafficline.Section;
import de.fhkiel.advancedjava.domain.trafficline.TrafficLine;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TrafficLineRepository extends Neo4jRepository<TrafficLine, Integer> {


    @Query("MATCH result = (t:TrafficLine)-[CONTAINS]->(ts:TrafficLineSection)-[IS_IN]->" +
            "(s:Section)-[]->(:Stop)\n" +
            "RETURN result\n" +
            "ORDER BY ts.position")
    Iterable<TrafficLine> findAllWithOrderedSecions();

    @Query("MATCH(tl:TrafficLine{id:$0})-[:CONTAINS]->(s:Section) RETURN s")
    Iterable<Section> getTrafficLineSections(Integer id);


}
