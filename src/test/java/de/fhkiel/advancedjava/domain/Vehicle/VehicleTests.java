package de.fhkiel.advancedjava.domain.Vehicle;

import de.fhkiel.advancedjava.domain.stop.Stop;
import de.fhkiel.advancedjava.domain.vehicle.Vehicle;
import de.fhkiel.advancedjava.exception.stop.StopIsNotFunctionalException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class VehicleTests {

    @Test
    public void ensureVehicleIsCreated(){
        String description = "BUS1";

        Vehicle vehicle = new Vehicle(description);


        Assertions.assertNull(vehicle.getId());
        Assertions.assertEquals(false,vehicle.getAssigned());
        Assertions.assertEquals(description,vehicle.getDescription());
        Assertions.assertNull(vehicle.getCurrentStop());
    }


    @Test
    public void ensureSettingFunctionalStopWorks(){
        Vehicle vehicle = new Vehicle("BUS2");
        Stop stop = new Stop(1,"name","city","OPENED", List.of("BUS","SUBWAY"));

        vehicle.setCurrentStop(stop);

        Assertions.assertEquals(stop,vehicle.getCurrentStop());
    }

    @Test
    public void ensureSettingNonFunctionalStopThrowsException(){
        Vehicle vehicle = new Vehicle("BUS2");
        Stop stop = new Stop(1,"name","city", List.of("BUS","SUBWAY"));

        Assertions.assertThrows(StopIsNotFunctionalException.class, () -> vehicle.setCurrentStop(stop));
    }

}
