package de.fhkiel.advancedjava.domain.common;

import de.fhkiel.advancedjava.domain.stop.Stop;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FunctionalTests {

    @Test
    public void ensureFunctionalHasChanged(){
        Stop stop = new Stop(1,"name","city","OPENED", List.of("BUS"));

        stop.saveFunctional();
        stop.closeStop();

        Assertions.assertTrue(stop.hasFunctionalChanged());
    }
}
