package de.fhkiel.advancedjava.domain.Disturbance;

import de.fhkiel.advancedjava.domain.disturbance.Disturbance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DisturbanceTests {

    @Test
    public void ensureDisturbanceIsCreated(){

        String description = "Fire";

        Disturbance disturbance = new Disturbance(description);

        Assertions.assertEquals(description,disturbance.getDescription());
        Assertions.assertFalse(disturbance.isResolved());
    }

    @Test
    public void ensureDisturbanceIsResolved(){
        String description = "Fire";

        Disturbance disturbance = new Disturbance(description);
        disturbance.resolve();

        Assertions.assertTrue(disturbance.isResolved());
    }
}
