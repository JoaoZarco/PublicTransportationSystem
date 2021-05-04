package de.fhkiel.advancedjava.exception.vehicle;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidVehicleIdException extends IllegalArgumentException {

    public InvalidVehicleIdException(long id) {
        super(String.format("Invalid Vehicle id %d", id));
    }
}
