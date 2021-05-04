package de.fhkiel.advancedjava.domain.common;

import de.fhkiel.advancedjava.domain.disturbance.Disturbance;
import de.fhkiel.advancedjava.domain.stop.Stop;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DisturbanceUserTests {

    @Test
    public void ensureDisturbanceIsAddedToStop(){
        Stop stop = new Stop(1,"name","city","OPENED", List.of("BUS"));
        Disturbance disturbance = new Disturbance("Fire");

        stop.addDisturbance(disturbance);

        Assertions.assertTrue(stop.isOutOfOrder());
        Assertions.assertTrue(stop.getDisturbances().contains(disturbance));
    }

    @Test
    public void ensureDisturbanceIsResolvedAndStopIsNotOutOfOrder(){
        Stop stop = new Stop(1,"name","city","OPENED", List.of("BUS"));
        String description = "Fire";
        Disturbance disturbance = new Disturbance(description);

        stop.addDisturbance(disturbance);
        stop.resolveDisturbance(description);

        Assertions.assertFalse(stop.isOutOfOrder());
    }
}
