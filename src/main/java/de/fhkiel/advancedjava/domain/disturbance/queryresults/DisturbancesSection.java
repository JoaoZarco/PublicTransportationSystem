package de.fhkiel.advancedjava.domain.disturbance.queryresults;

import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class DisturbancesSection {

    public String startStop;
    public String endStop;
    public String type;
    public Long amount;

    //mapping
    private DisturbancesSection() {

    }

}
