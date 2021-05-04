package de.fhkiel.advancedjava.domain.Stop;

import de.fhkiel.advancedjava.domain.stop.DomainService;
import de.fhkiel.advancedjava.domain.stop.Stop;
import de.fhkiel.advancedjava.exception.stop.StopIsNotFunctionalException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DomainServiceTests {

    @Test
    public void ensureStopIsFunctional(){
        Stop stop = new Stop(1,"name","city","OPENED", List.of("BUS"));

        DomainService.validateFunctionality(stop);

        Assertions.assertTrue(stop.isFunctional());
    }

    @Test
    public void ensureStopIsNotFunctional(){
        Stop stop = new Stop(1,"name","city",List.of("BUS"));

        Assertions.assertThrows(StopIsNotFunctionalException.class,
                () -> DomainService.validateFunctionality(stop));
    }
}
