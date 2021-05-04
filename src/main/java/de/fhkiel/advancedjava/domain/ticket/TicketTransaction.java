package de.fhkiel.advancedjava.domain.ticket;

import de.fhkiel.advancedjava.domain.stop.DomainService;
import de.fhkiel.advancedjava.domain.stop.Stop;
import de.fhkiel.advancedjava.exception.section.InvalidSectionStops;
import de.fhkiel.advancedjava.exception.ticket.InvalidAmountOfTicketsException;
import org.neo4j.ogm.annotation.*;

@NodeEntity
public class TicketTransaction {
    @Id
    @GeneratedValue
    private Long id;
    private Integer amount;

    @Relationship(type = "FROM")
    private Stop startStop;
    @Relationship(type = "TO")
    private Stop endStop;

    public TicketTransaction(Stop startStop, Stop endStop, Integer amount) {
        setStops(startStop, endStop);
        setAmount(amount);
    }

    public Stop getStartStop() {
        return startStop;
    }

    public Stop getEndStop() {
        return endStop;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setStops(Stop startStop, Stop endStop) {
        validateStops(startStop, endStop);
        this.startStop = startStop;
        this.endStop = endStop;
    }

    public void setAmount(Integer amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    private void validateAmount(Integer amount) {
        if (amount < 1)
            throw new InvalidAmountOfTicketsException("Amount of purchased tickets must be at least 1");
    }

    public void validateStops(Stop startStop, Stop endStop) {
        DomainService.validateFunctionality(startStop);
        DomainService.validateFunctionality(endStop);
        if (startStop.equals(endStop))
            throw new InvalidSectionStops("Invalid section Stops, begin stop id cannot be the same as the end stop id");
    }
}
