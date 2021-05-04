package de.fhkiel.advancedjava.controller;

import de.fhkiel.advancedjava.domain.vehicle.Vehicle;
import de.fhkiel.advancedjava.dto.vehicle.RequestVehicleDto;
import de.fhkiel.advancedjava.dto.vehicle.ResponseVehicleDto;
import de.fhkiel.advancedjava.mapper.VehicleMapper;
import de.fhkiel.advancedjava.service.VehicleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
@Api
public class VehicleController {


    private final VehicleMapper mapper;
    private final VehicleService service;

    @Autowired
    public VehicleController(VehicleMapper mapper, VehicleService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @ApiOperation(
            value = "Add a unassigned vehicle to the transportation system"
    )
    @PostMapping
    public ResponseEntity<ResponseVehicleDto> addVehicle(@RequestBody RequestVehicleDto requestDto) {
        Vehicle vehicle = mapper.DTOToVehicle(requestDto);
        vehicle = service.addVehicle(vehicle);
        ResponseVehicleDto responseDto = mapper.vehicleToDTO(vehicle);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "Add and assign a certain vehicle to a stop"
    )
    @PostMapping("assign/stop/{stopId}")
    public ResponseEntity<ResponseVehicleDto> addVehicleWithStop(@RequestBody RequestVehicleDto requestDto, @PathVariable Integer stopId) {
        Vehicle vehicle = mapper.DTOToVehicle(requestDto);
        vehicle = service.assignVehicle(vehicle, stopId);
        ResponseVehicleDto responseDto = mapper.vehicleToDTO(vehicle);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "Assign a vehicle to a stop"
    )
    @PutMapping("/{vehicleId}/assign/stop/{stopId}")
    public ResponseEntity<ResponseVehicleDto> assignVehicleToStop(@PathVariable Long vehicleId, @PathVariable Integer stopId) {
        Vehicle vehicle = service.assignVehicle(vehicleId, stopId);
        ResponseVehicleDto responseDto = mapper.vehicleToDTO(vehicle);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "Get all vehicles"

    )
    @GetMapping()
    public ResponseEntity<Iterable<ResponseVehicleDto>> getAllVehicles() {
        return ResponseEntity.ok(mapper.vehiclesToDTOs(service.getAllVehicles()));
    }

    @ApiOperation(
            value = "Get all vehicles",
            produces = "text/csv"
    )
    @GetMapping(path = "/csv", produces = "text/csv")
    public void getAllVehiclesCSV(HttpServletResponse response) throws IOException {
        response.setStatus(200);
        Iterable<Vehicle> allVehicles = service.getAllVehicles();
        List<ResponseVehicleDto> allVehicleDtos = mapper.vehiclesToDTOs(allVehicles);
        mapper.writeCsvToResponse(response.getWriter(), allVehicleDtos);
    }

}
