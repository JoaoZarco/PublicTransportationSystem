package de.fhkiel.advancedjava.controller;

import de.fhkiel.advancedjava.domain.ticket.TicketTransaction;
import de.fhkiel.advancedjava.dto.ticket.RequestTicketDto;
import de.fhkiel.advancedjava.dto.ticket.ResponseTicketDto;
import de.fhkiel.advancedjava.mapper.TicketMapper;
import de.fhkiel.advancedjava.service.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
@Api
public class TicketController {

    private final TicketMapper mapper;
    private final TicketService service;

    @Autowired
    public TicketController(TicketMapper mapper, TicketService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @ApiOperation(
            value = "Buy a certain amount of tickets for a certain connection of stops"
    )
    @PostMapping("/buy")
    public ResponseEntity<ResponseTicketDto> buy(@RequestBody RequestTicketDto dto) {
        TicketTransaction ticketTransaction = mapper.DTOToTicket(dto);
        ticketTransaction = service.add(ticketTransaction);
        return new ResponseEntity<>(mapper.TicketToDTO(ticketTransaction), HttpStatus.CREATED);
    }
}
