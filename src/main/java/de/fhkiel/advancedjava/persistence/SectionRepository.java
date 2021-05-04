package de.fhkiel.advancedjava.persistence;

import de.fhkiel.advancedjava.domain.disturbance.queryresults.DisturbancesSection;
import de.fhkiel.advancedjava.domain.trafficline.Section;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;
import java.util.Set;

public interface SectionRepository extends Neo4jRepository<Section, Long> {

    @Query("MATCH (stop1{id:$0})<-[:BEGINS_IN]-(s:Section {type:$2})-[:ENDS_IN]->(stop2:Stop{id:$1})" +
            "RETURN id(s)")
    Optional<Long> findIdByStopIdsAndType(Integer startStopId, Integer endStopId, String type);

    @Query("MATCH(ss:SectionStop {stopId:$0 , type: $2})-[c:CONNECTED_TO]->(ss2:SectionStop {stopId: $1})" +
            "DELETE c")
    void deactivateSection(Integer startStopId, Integer endStopId, String type);

    @Query("MATCH result = (ss1:SectionStop)<-[:MANAGES]-(s1:Stop {id : $0})<-[]-(s:Section {outOfOrder:false})-[]->(s2:Stop {outOfOrder:false,state:'OPENED'})-[:MANAGES]->(ss2:SectionStop)" +
            "RETURN result")
    Iterable<Section> findConnectableSectionsByStopId(Integer stopId);

    @Query("MATCH (s:Section)-[:AS_A]->(d:Disturbance)," +
            "(s1:Stop)<-[:BEGINS_IN]-(s)-[:ENDS_IN]->(s2:Stop)" +
            "RETURN s1.name AS startStop,s2.name AS endStop, s.type AS type,count(d) AS amount")
    Set<DisturbancesSection> totalNumberOfDisturbancePerSection();

    @Query("MATCH(ss1:SectionStop)-[c:CONNECTED_TO]->(ss2:SectionStop) RETURN count(c)")
    Long getTotalNumberOfConnections();
}
