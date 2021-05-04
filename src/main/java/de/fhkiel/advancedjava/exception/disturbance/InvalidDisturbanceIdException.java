package de.fhkiel.advancedjava.exception.disturbance;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDisturbanceIdException extends IllegalArgumentException {

    public InvalidDisturbanceIdException(String s) {
        super(s);
    }
}
