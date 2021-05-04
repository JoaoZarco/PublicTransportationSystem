package de.fhkiel.advancedjava.domain.ticket.queryresults;

import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class ConnectionTicket {
    public String startStop;
    public String endStop;
    public Long amount;

    //mapping
    private ConnectionTicket() {

    }
}
