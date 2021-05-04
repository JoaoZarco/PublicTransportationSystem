package de.fhkiel.advancedjava.service;

import de.fhkiel.advancedjava.domain.stop.Stop;
import de.fhkiel.advancedjava.domain.vehicle.Vehicle;
import de.fhkiel.advancedjava.exception.vehicle.InvalidVehicleIdException;
import de.fhkiel.advancedjava.persistence.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleService {

    private final VehicleRepository repository;
    private final StopService stopService;

    @Autowired
    public VehicleService(VehicleRepository repository, StopService stopService) {
        this.repository = repository;
        this.stopService = stopService;
    }

    public Vehicle getVehicle(Long id) {
        return repository.findById(id).orElseThrow(() -> new InvalidVehicleIdException(id));
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        return repository.save(vehicle);
    }

    public Iterable<Vehicle> getAllVehicles() {
        return repository.findAll(2);
    }

    @Transactional
    public Vehicle assignVehicle(Long vehicleId, Integer stopId) {
        //delete the previously relationship to a stop
        repository.deleteVehicleRelationship(vehicleId);
        //get the vehicle by id
        Vehicle vehicle = getVehicle(vehicleId);
        //get the stop by id
        Stop stop = stopService.getStopById(stopId);
        //update vehicle
        vehicle.setCurrentStop(stop);
        vehicle.setAssigned();
        return repository.save(vehicle);
    }

    @Transactional
    public Vehicle assignVehicle(Vehicle vehicle, Integer stopId) {
        //get the stop by id
        Stop stop = stopService.getStopById(stopId);
        //update vehicle
        vehicle.setCurrentStop(stop);
        vehicle.setAssigned();
        return repository.save(vehicle);
    }
}
