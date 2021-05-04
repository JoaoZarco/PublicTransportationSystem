package de.fhkiel.advancedjava.controller;


import de.fhkiel.advancedjava.domain.stop.Stop;
import de.fhkiel.advancedjava.domain.trafficline.TrafficLine;
import de.fhkiel.advancedjava.dto.schedule.RequestScheduleDto;
import de.fhkiel.advancedjava.dto.schedule.ResponseScheduleDto;
import de.fhkiel.advancedjava.mapper.ScheduleMapper;
import de.fhkiel.advancedjava.service.ScheduleService;
import de.fhkiel.advancedjava.service.StopService;
import de.fhkiel.advancedjava.service.TrafficLineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/schedule")
@Api
public class ScheduleController {

    private final StopService stopService;
    private final TrafficLineService trafficLineService;
    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

    @Autowired
    public ScheduleController(StopService stopService, TrafficLineService trafficLineService, ScheduleService scheduleService, ScheduleMapper scheduleMapper) {
        this.stopService = stopService;
        this.trafficLineService = trafficLineService;
        this.scheduleService = scheduleService;
        this.scheduleMapper = scheduleMapper;
    }

    @ApiOperation(
            value = "Add a new schedule to the transportation system"
    )
    @PostMapping
    public ResponseEntity<ResponseScheduleDto> postSchedule(@RequestBody RequestScheduleDto dto) {
        //clean Database
        scheduleService.cleanDatabase();

        //Map and save Stops
        Iterable<Stop> stops = scheduleMapper.stopDTOsToStops(dto.stops);
        stops = stopService.saveAllStops(stops);

        //Map and save Traffic Lines
        Iterable<TrafficLine> trafficLines = scheduleMapper.trafficLineDTOsToTrafficLines(dto.trafficLines);
        trafficLines = trafficLineService.saveAllTrafficLines(trafficLines);

        return new ResponseEntity<>(scheduleMapper.scheduleToDto(stops, trafficLines), HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "Backup the current transportation system schedule"
    )
    @GetMapping("/backup")
    public ResponseEntity<String> backupCurrentSchedule() {
        scheduleService.backupSchedule();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
