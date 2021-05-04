package de.fhkiel.advancedjava.domain.common;

import de.fhkiel.advancedjava.exception.stop.InvalidTypeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TypeDomainServiceTests {

    @Test
    public void ensureTypeExists(){
        String stringType = "BUS";

        Type type = TypeDomainService.getType(stringType);
;
        Assertions.assertEquals(Type.valueOf(stringType),type);
    }

    @Test
    public void ensureTypeDoesNotExist(){
        String stringType = "BUZ";

        Assertions.assertThrows(InvalidTypeException.class,() -> TypeDomainService.getType(stringType));
    }
}
