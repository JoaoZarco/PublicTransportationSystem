package de.fhkiel.advancedjava.exception.stop;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidStopIdException extends IllegalArgumentException {

    public InvalidStopIdException() {

    }

    public InvalidStopIdException(int id) {
        super(String.format("stop with id %d not found", id));
    }

    public InvalidStopIdException(String msg) {
        super(msg);
    }
}
