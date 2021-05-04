package de.fhkiel.advancedjava.controller;

import de.fhkiel.advancedjava.domain.disturbance.Disturbance;
import de.fhkiel.advancedjava.domain.stop.Stop;
import de.fhkiel.advancedjava.domain.stop.queryresults.NodePath;
import de.fhkiel.advancedjava.domain.trafficline.Section;
import de.fhkiel.advancedjava.dto.disturbance.RequestDisturbanceDto;
import de.fhkiel.advancedjava.dto.path.PathDto;
import de.fhkiel.advancedjava.dto.stop.RequestStopDto;
import de.fhkiel.advancedjava.dto.stop.ResponseStopDto;
import de.fhkiel.advancedjava.dto.section.ResponseSectionDto;
import de.fhkiel.advancedjava.mapper.DisturbanceMapper;
import de.fhkiel.advancedjava.mapper.PathMapper;
import de.fhkiel.advancedjava.mapper.SectionMapper;
import de.fhkiel.advancedjava.mapper.StopMapper;
import de.fhkiel.advancedjava.service.StopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@RestController()
@RequestMapping("/stops")
@Api()
public class StopController {

    private final StopMapper stopMapper;
    private final SectionMapper sectionMapper;
    private final DisturbanceMapper disturbanceMapper;
    private final PathMapper pathMapper;
    private final StopService stopService;

    @Autowired
    public StopController(StopService stopService, StopMapper stopMapper, SectionMapper sectionMapper, DisturbanceMapper disturbanceMapper, PathMapper pathMapper) {
        this.stopMapper = stopMapper;
        this.sectionMapper = sectionMapper;
        this.disturbanceMapper = disturbanceMapper;
        this.pathMapper = pathMapper;
        this.stopService = stopService;
    }

    @ApiOperation(
            value = "Get all existent stops"
    )
    @GetMapping
    public ResponseEntity<Iterable<ResponseStopDto>> getAll() {
        return new ResponseEntity<>(stopMapper.stopsToDTOs(stopService.getAllStops()), HttpStatus.OK);
    }


    @ApiOperation(
            value = "Create a Stop on the transportation network"
    )
    @PostMapping
    public ResponseEntity<ResponseStopDto> add(
            @RequestBody RequestStopDto requestDTO) {
        Stop stop = stopMapper.DTOToStop(requestDTO);
        ResponseStopDto responseDTO = stopMapper.stopToDTO(stopService.saveStop(stop));
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "Open stop"
    )
    @PutMapping("/{id}/open")
    public ResponseEntity<ResponseStopDto> open(
            @PathVariable() Integer id) {
        return new ResponseEntity<>(stopMapper.stopToDTO(stopService.openStop(id)), HttpStatus.OK);
    }

    @ApiOperation(
            value = "Close stop"
    )
    @PutMapping("/{id}/close")
    public ResponseEntity<ResponseStopDto> close(@PathVariable Integer id) {
        return new ResponseEntity<>(stopMapper.stopToDTO(stopService.closeStop(id)), HttpStatus.OK);
    }

    @ApiOperation(
            value = "Get the stop associated with the provided Id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStopDto> getById(
            @PathVariable Integer id) {
        return new ResponseEntity<>(stopMapper.stopToDTOWithDisturbance(stopService.getStopById(id)), HttpStatus.OK);
    }

    @ApiOperation(
            value = "Get a section by it's attributes"
    )
    @GetMapping("/{startStopId}/{endStopId}/{type}")
    public ResponseEntity<ResponseSectionDto> getSectionByAttributes(
            @PathVariable Integer startStopId,
            @PathVariable Integer endStopId,
            @PathVariable String type
    ) {
        return new ResponseEntity<>(sectionMapper.sectionDtoWithDisturbances(stopService.getSectionWException(startStopId, endStopId, type)), HttpStatus.OK);
    }

    @ApiOperation(
            value = "Add disturbance to a stop"
    )
    @PostMapping("/{id}/disturbance")
    public ResponseEntity<ResponseStopDto> addDisturbance(@PathVariable Integer id, @RequestBody RequestDisturbanceDto requestDto) {
        Disturbance disturbance = disturbanceMapper.DTOToDisturbance(requestDto);
        Stop stop = stopService.addDisturbance(id, disturbance);
        return new ResponseEntity<>(stopMapper.stopToDTOWithDisturbance(stop), HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "Resolve a disturbance in a stop"
    )
    @PostMapping("/{stopId}/disturbance/resolve/{description}")
    public ResponseEntity<ResponseStopDto> resolveDisturbance(@PathVariable Integer stopId, @PathVariable String description) {
        Stop stop = stopService.resolveDisturbance(stopId, description);
        return new ResponseEntity<>(stopMapper.stopToDTOWithDisturbance(stop), HttpStatus.OK);
    }

    @ApiOperation(
            value = "Add disturbance to a section between two stops"
    )
    @PostMapping("/{startStopId}/{endStopId}/{type}/disturbance")
    public ResponseEntity<ResponseSectionDto> addDisturbance(
            @RequestBody RequestDisturbanceDto dto,
            @PathVariable Integer startStopId,
            @PathVariable Integer endStopId,
            @PathVariable String type) {
        Disturbance disturbance = disturbanceMapper.DTOToDisturbance(dto);
        //resolve the disturbance
        Section section = stopService.addDisturbance(disturbance, startStopId, endStopId, type);
        ResponseSectionDto responseDto = sectionMapper.sectionDtoWithDisturbances(section);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "Resolve a disturbance in a section between two stops"
    )
    @PostMapping("/{startStopId}/{endStopId}/{type}/disturbance/resolve/{description}")
    public ResponseEntity<ResponseSectionDto> resolveDisturbance(
            @PathVariable Integer startStopId,
            @PathVariable Integer endStopId,
            @PathVariable String type,
            @PathVariable String description
    ) {
        Section section = stopService.resolveDisturbance(startStopId, endStopId, type, description);
        ResponseSectionDto responseDto = sectionMapper.sectionDtoWithDisturbances(section);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Update the connection type between different types of lines"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ResponseStopDto> updateConnectionTime(@PathVariable Integer id, @RequestParam Integer connectionTime) {
        return new ResponseEntity<>(stopMapper.stopToDTO(stopService.updateConnectionTime(id, connectionTime)), HttpStatus.OK);
    }

    @ApiOperation(
            value = "Get the shortest path between two stops"
    )
    @GetMapping("/path")
    public ResponseEntity<PathDto> shortestPathBetweenStops(
            @RequestParam String startStopName,
            @RequestParam String endStopName) {

        Optional<Stop> startStop = stopService.getStopByName(startStopName);
        Optional<Stop> endStop = stopService.getStopByName(endStopName);
        if (startStop.isEmpty())
            return new ResponseEntity<>(buildInvalidNameDto("startStopName", startStopName), HttpStatus.BAD_REQUEST);
        if (endStop.isEmpty())
            return new ResponseEntity<>(buildInvalidNameDto("endStopName", endStopName), HttpStatus.BAD_REQUEST);
        Iterable<NodePath> path = stopService.getShortestPathBetweenStops(startStop.get(), endStop.get());
        return new ResponseEntity<>(pathMapper.pathToDTO(path), HttpStatus.OK);
    }

    private PathDto buildInvalidNameDto(String parameter, String value) {
        String mostSimilarName = stopService.getMostSimilarStopName(value);
        return pathMapper.PathToDTO(parameter, value, mostSimilarName);
    }

}
