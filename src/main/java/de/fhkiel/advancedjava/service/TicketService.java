package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.domain.ticket.TicketTransaction;
import de.fhkiel.advancedjava.domain.ticket.queryresults.ConnectionTicket;
import de.fhkiel.advancedjava.persistence.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TicketService {

    public final TicketRepository repository;
    public final StopService stopService;

    @Autowired
    public TicketService(TicketRepository repository, StopService stopService) {
        this.repository = repository;
        this.stopService = stopService;
    }

    public TicketTransaction add(TicketTransaction ticketTransaction) {
        return repository.save(ticketTransaction);
    }

    public Long getTotalNumberOfTickets() {
        return repository.getTotalNumberOfTickets();
    }

    public Set<ConnectionTicket> getNumberOfTicketsPerConnection() {
        return repository.getNumberOfTicketsPerConnection();
    }
}
