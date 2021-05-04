package de.fhkiel.advancedjava.domain.stop;

import de.fhkiel.advancedjava.exception.stop.StopIsNotFunctionalException;

public class DomainService {

    private DomainService() {

    }

    public static void validateFunctionality(Stop stop) {
        if (!stop.isFunctional())
            throw new StopIsNotFunctionalException(String.format("Stop with id %d is not functional", stop.getId()));
    }
}
