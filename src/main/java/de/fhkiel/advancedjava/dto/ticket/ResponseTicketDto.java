package de.fhkiel.advancedjava.dto.ticket;

import de.fhkiel.advancedjava.dto.stop.ResponseStopDto;

public class ResponseTicketDto {

    public ResponseStopDto startStop;
    public ResponseStopDto endStop;
    public Integer amount;

    public ResponseTicketDto(ResponseStopDto startStop, ResponseStopDto endStop, Integer amount) {
        this.startStop = startStop;
        this.endStop = endStop;
        this.amount = amount;
    }
}
