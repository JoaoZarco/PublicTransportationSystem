package de.fhkiel.advancedjava.controller;

import de.fhkiel.advancedjava.domain.disturbance.queryresults.DisturbancesSection;
import de.fhkiel.advancedjava.domain.disturbance.queryresults.DisturbancesStop;
import de.fhkiel.advancedjava.domain.ticket.queryresults.ConnectionTicket;
import de.fhkiel.advancedjava.mapper.DisturbanceMapper;
import de.fhkiel.advancedjava.mapper.StopMapper;
import de.fhkiel.advancedjava.mapper.TicketMapper;
import de.fhkiel.advancedjava.service.StopService;
import de.fhkiel.advancedjava.service.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@RestController
@RequestMapping("/statistics")
@Api
public class StatisticsController {

    private final StopMapper stopMapper;
    private final DisturbanceMapper disturbanceMapper;
    private final TicketMapper ticketMapper;
    private final StopService stopService;
    private TicketService ticketService;


    public StatisticsController(StopMapper stopMapper,
                                DisturbanceMapper disturbanceMapper,
                                TicketMapper ticketMapper,
                                StopService stopService,
                                TicketService ticketService) {
        this.stopMapper = stopMapper;
        this.disturbanceMapper = disturbanceMapper;
        this.ticketMapper = ticketMapper;
        this.stopService = stopService;
        this.ticketService = ticketService;
    }

    @ApiOperation(
            value = "Gets all kinds of statistics about the transportation system",
            produces = "text/plain"
    )
    @GetMapping(produces = "text/plain")
    public void getStatistics(HttpServletResponse response) throws IOException {
        response.setStatus(200);
        PrintWriter writer = response.getWriter();
        Long totalNumberOfTickets = ticketService.getTotalNumberOfTickets();
        Set<ConnectionTicket> totalNumberOfTicketsPerConnection = ticketService.getNumberOfTicketsPerConnection();

        Long totalNumberOfDisturbances = stopService.getTotalNumberOfDisturbances();
        Set<DisturbancesSection> totalNumberOfDisturbancesPerSection = stopService.getTotalNumberOfDisturbancePerSection();
        Set<DisturbancesStop> totalNumberOfDisturbancesPerStop = stopService.getTotalNumberOfDisturbancesPerStop();

        Long totalNumberOfStops = stopService.getTotalNumberOfStops();
        Long totalNumberOfBusStops = stopService.getTotalNumberOfBusStops();
        Long totalNumberOfSubwayStops = stopService.getTotalNumberOfSubwayStops();
        Long totalNumberOfSuburbanTrainStops = stopService.getTotalNumberOfSuburbanTrainStops();
        Long totalNumberOfConnections = stopService.getTotalNumberOfConnections();

        ticketMapper.writeStatistics(totalNumberOfTickets, totalNumberOfTicketsPerConnection, writer);
        writer.println();
        disturbanceMapper.writeStatistics(totalNumberOfDisturbances, totalNumberOfDisturbancesPerSection, totalNumberOfDisturbancesPerStop, writer);
        writer.println();
        stopMapper.writeStatistics(totalNumberOfStops, totalNumberOfBusStops, totalNumberOfSubwayStops, totalNumberOfSuburbanTrainStops, totalNumberOfConnections, writer);
    }
}
