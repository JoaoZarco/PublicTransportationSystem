package de.fhkiel.advancedjava.persistence;


import de.fhkiel.advancedjava.domain.stop.Stop;
import de.fhkiel.advancedjava.domain.disturbance.queryresults.DisturbancesStop;
import de.fhkiel.advancedjava.domain.stop.queryresults.NodePath;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface StopRepository extends Neo4jRepository<Stop, Integer> {


    Optional<Stop> findByName(String name);

    @Query("MATCH(s:Stop) RETURN s.name")
    Iterable<String> getAllStopNames();


    @Query("MATCH(s:Stop {id:$0})-[:MANAGES]->(ss:SectionStop)-[c:CONNECTED_TO]-(ss2:SectionStop)" +
            "WHERE ss.stopId <> ss2.stopId " +
            "DELETE c")
    void deactivateStop(Integer stopId);


    @Query("MATCH (start:Access {stopId : $0, isStartingAccess:true}), (end:Access {stopId: $1,isEndingAccess:true})\n" +
            "            CALL gds.alpha.shortestPath.stream({\n" +
            "              nodeProjection: 'Access',\n" +
            "              relationshipProjection: {\n" +
            "                CONNECTED_TO: {\n" +
            "                  type: 'CONNECTED_TO',\n" +
            "                  properties: 'cost',\n" +
            "                  orientation: 'NATURAL'\n" +
            "                }\n" +
            "            },\n" +
            "              startNode: start,\n" +
            "              endNode: end,\n" +
            "              relationshipWeightProperty: 'cost'\n" +
            "            })\n" +
            "            YIELD nodeId, cost\n" +
            "            RETURN gds.util.asNode(nodeId).stopId AS stopId,gds.util.asNode(nodeId).type AS type, cost AS cost")
    List<NodePath> shortestPathBetweenSectionStops(Integer startStopId, Integer endStopId);

    @Query("MATCH(d:Disturbance) RETURN count(d)")
    Long getTotalNumberOfDisturbances();

    @Query("MATCH (s:Stop)-[:AS_A]->(d:Disturbance) RETURN s.name AS stop,count(d) AS amount")
    Set<DisturbancesStop> getNumberOfDisturbancePerStop();

    @Query("MATCH(s:Stop) RETURN count(s)")
    Long getTotalNumberOfStops();

    @Query("MATCH(s:Stop)-[:MANAGES]->(ss:SectionStop {type:'BUS'}) RETURN count(s)")
    Long getNumberOfBusStops();

    @Query("MATCH(s:Stop)-[:MANAGES]->(ss:SectionStop {type:'SUBWAY'}) RETURN count(s)")
    Long getNumberOfSubwayStops();

    @Query("MATCH(s:Stop)-[:MANAGES]->(ss:SectionStop {type:'SUBURBAN_TRAIN'}) RETURN count(s)")
    Long getNumberOfSuburbanTrainStops();
}
