package de.fhkiel.advancedjava.controller;


import de.fhkiel.advancedjava.domain.trafficline.TrafficLine;
import de.fhkiel.advancedjava.dto.trafficline.RequestTrafficLineDto;
import de.fhkiel.advancedjava.dto.trafficline.ResponseTrafficLineDto;
import de.fhkiel.advancedjava.mapper.TrafficLineMapper;
import de.fhkiel.advancedjava.service.TrafficLineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/trafficLines")
@Api
public class TrafficLineController {

    private final TrafficLineService service;
    private final TrafficLineMapper mapper;

    @Autowired
    public TrafficLineController(TrafficLineService service, TrafficLineMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @ApiOperation(
            value = "Add a traffic line"
    )
    @PostMapping
    public ResponseEntity<ResponseTrafficLineDto> addTrafficLine(@RequestBody RequestTrafficLineDto dto) {
        TrafficLine trafficLine = mapper.DTOToTrafficLine(dto);
        trafficLine = service.saveTrafficLine(trafficLine);
        return new ResponseEntity<>(mapper.trafficLineToDTO(trafficLine), HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "Get all traffic lines"
    )
    @GetMapping
    public ResponseEntity<Iterable<ResponseTrafficLineDto>> getAllTrafficLines() {
        Iterable<TrafficLine> trafficLines = service.getAllTrafficLinesOrdered();
        return ResponseEntity.ok(mapper.trafficLinesToDTOs(trafficLines));
    }


}
