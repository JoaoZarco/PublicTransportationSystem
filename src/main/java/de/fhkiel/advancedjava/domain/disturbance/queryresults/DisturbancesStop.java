package de.fhkiel.advancedjava.domain.disturbance.queryresults;

import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class DisturbancesStop {
    public String stop;
    public Long amount;

    //mapping
    private DisturbancesStop() {

    }
}
