package de.fhkiel.advancedjava.domain.common;

import de.fhkiel.advancedjava.exception.stop.InvalidTypeException;

public class TypeDomainService {

    private TypeDomainService() {

    }

    public static Type getType(String stringType) {
        try {
            return Type.valueOf(stringType);
        } catch (IllegalArgumentException e) {
            throw new InvalidTypeException(String.format("Invalid station type, station type %s does not exist", stringType));
        }
    }

}
