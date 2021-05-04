package de.fhkiel.advancedjava.domain.ticket;

import de.fhkiel.advancedjava.domain.stop.Stop;
import de.fhkiel.advancedjava.exception.ticket.InvalidAmountOfTicketsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TicketTransactionTests {

    @Test
    public void ensureTicketTransactionIsCreated(){

        Integer amount = 10;
        Stop stop1 = new Stop(1,"name","city","OPENED", List.of("BUS"));
        Stop stop2 = new Stop(2,"name","city","OPENED", List.of("BUS"));


        TicketTransaction ticketTransaction = new TicketTransaction(stop1,stop2,amount);

        Assertions.assertEquals(amount,ticketTransaction.getAmount());
        Assertions.assertEquals(stop1,ticketTransaction.getStartStop());
        Assertions.assertEquals(stop2,ticketTransaction.getEndStop());
    }

    @Test
    public void ensureTicketTransactionAmountIsInvalid(){

        Integer amount = 0;
        Stop stop1 = new Stop(1,"name","city","OPENED", List.of("BUS"));
        Stop stop2 = new Stop(2,"name","city","OPENED", List.of("BUS"));


        Assertions.assertThrows(InvalidAmountOfTicketsException.class,
                () -> new TicketTransaction(stop1,stop2,amount));
    }
}
