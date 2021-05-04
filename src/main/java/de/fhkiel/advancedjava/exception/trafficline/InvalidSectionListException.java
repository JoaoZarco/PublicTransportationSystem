package de.fhkiel.advancedjava.exception.trafficline;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidSectionListException extends IllegalArgumentException {

    public InvalidSectionListException(String s) {
        super(s);
    }
}
