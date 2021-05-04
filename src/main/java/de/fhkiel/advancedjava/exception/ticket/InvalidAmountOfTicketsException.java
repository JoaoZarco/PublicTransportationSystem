package de.fhkiel.advancedjava.exception.ticket;

public class InvalidAmountOfTicketsException extends IllegalArgumentException {

    public InvalidAmountOfTicketsException(String m) {
        super(m);
    }
}
