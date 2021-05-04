package de.fhkiel.advancedjava.exception.section;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidSectionStops extends IllegalArgumentException {

    public InvalidSectionStops(String s) {
        super(s);
    }
}
