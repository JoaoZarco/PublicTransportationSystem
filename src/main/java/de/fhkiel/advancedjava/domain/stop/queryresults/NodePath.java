package de.fhkiel.advancedjava.domain.stop.queryresults;

import de.fhkiel.advancedjava.domain.stop.Stop;
import org.springframework.data.neo4j.annotation.QueryResult;


@QueryResult
public class NodePath {
    public Double stopId;
    public Stop stop;
    public Double cost;
    public String type;
}
