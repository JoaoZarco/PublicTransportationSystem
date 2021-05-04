package de.fhkiel.advancedjava.persistence;

import de.fhkiel.advancedjava.domain.ticket.TicketTransaction;
import de.fhkiel.advancedjava.domain.ticket.queryresults.ConnectionTicket;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Set;

public interface TicketRepository extends Neo4jRepository<TicketTransaction, Long> {

    @Query("MATCH (t:TicketTransaction) RETURN sum(t.amount)")
    Long getTotalNumberOfTickets();

    @Query("MATCH (s1:Stop)<-[:FROM]-(t:TicketTransaction)-[:TO]->(s2:Stop) RETURN s1.name AS startStop, s2.name AS endStop, sum(t.amount) AS amount")
    Set<ConnectionTicket> getNumberOfTicketsPerConnection();
}
