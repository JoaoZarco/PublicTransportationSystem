package de.fhkiel.advancedjava.exception.stop;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidStateException extends IllegalArgumentException {

    public InvalidStateException(String s) {
        super(s);
    }
}
