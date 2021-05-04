package de.fhkiel.advancedjava.mapper;

import de.fhkiel.advancedjava.domain.stop.Stop;
import de.fhkiel.advancedjava.domain.ticket.TicketTransaction;
import de.fhkiel.advancedjava.domain.ticket.queryresults.ConnectionTicket;
import de.fhkiel.advancedjava.dto.ticket.RequestTicketDto;
import de.fhkiel.advancedjava.dto.ticket.ResponseTicketDto;
import de.fhkiel.advancedjava.service.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

@Component
public class TicketMapper {

    private StopMapper stopMapper;
    private StopService stopService;

    @Autowired
    public TicketMapper(StopMapper stopMapper, StopService stopService) {
        this.stopMapper = stopMapper;
        this.stopService = stopService;
    }

    public TicketTransaction DTOToTicket(RequestTicketDto dto) {
        Stop startStop = stopService.getStopById(dto.startStopId);
        Stop endStop = stopService.getStopById(dto.endStopId);
        return new TicketTransaction(
                startStop,
                endStop,
                dto.amount
        );
    }

    public ResponseTicketDto TicketToDTO(TicketTransaction ticketTransaction) {
        return new ResponseTicketDto(
                stopMapper.stopToDTOWithNoDetail(ticketTransaction.getStartStop()),
                stopMapper.stopToDTOWithNoDetail(ticketTransaction.getEndStop()),
                ticketTransaction.getAmount()
        );
    }

    public void writeStatistics(Long totalNumberOfTickets,
                                Iterable<ConnectionTicket> totalNumberOFTicketsPerConnection,
                                PrintWriter writer) {
        writer.println(
                String.format("Total number of tickets : %d", totalNumberOfTickets)
        );
        writer.println("Total number of tickets per connection : ");
        totalNumberOFTicketsPerConnection.forEach(connectionTicket ->
                writer.println(String.format("%s --> %s : %d",
                        connectionTicket.startStop,
                        connectionTicket.endStop,
                        connectionTicket.amount)
                )
        );
        writer.println();
    }
}
