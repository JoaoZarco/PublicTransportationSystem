package de.fhkiel.advancedjava.domain.Stop;

import de.fhkiel.advancedjava.domain.stop.Stop;
import de.fhkiel.advancedjava.exception.stop.InvalidStateException;
import de.fhkiel.advancedjava.exception.stop.InvalidTypeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StopTests {

    @Test
    public void ensureStopIsCreated(){
        Integer id = 1;
        Integer connectionTime = 0;
        String name = "name";
        String city = "city";
        Collection<String> stringTypes = List.of("BUS");

        Stop stop = new Stop(id,name,city,stringTypes);

        Assertions.assertEquals(id,stop.getId());
        Assertions.assertEquals(name,stop.getName());
        Assertions.assertEquals(city,stop.getCity());
        Assertions.assertTrue(stop.isClosed());
        Assertions.assertEquals(connectionTime,stop.getConnectionTime());
    }

    @Test
    public void ensureStopIsCreatedWConnectionTime(){
        Integer id = 1;
        Integer connectionTime = 10;
        String name = "name";
        String city = "city";
        Collection<String> stringTypes = List.of("BUS");

        Stop stop = new Stop(id,name,city,stringTypes,connectionTime);

        Assertions.assertEquals(id,stop.getId());
        Assertions.assertEquals(name,stop.getName());
        Assertions.assertEquals(city,stop.getCity());
        Assertions.assertTrue(stop.isClosed());
        Assertions.assertEquals(connectionTime,stop.getConnectionTime());
    }

    @Test
    public void ensureStopIsCreatedWConnectionTimeAndState(){
        Integer id = 1;
        Integer connectionTime = 10;
        String name = "name";
        String city = "city";
        String state = "OPENED";
        Collection<String> stringTypes = List.of("BUS");

        Stop stop = new Stop(id,name,city,state,stringTypes,connectionTime);

        Assertions.assertEquals(id,stop.getId());
        Assertions.assertEquals(name,stop.getName());
        Assertions.assertEquals(city,stop.getCity());
        Assertions.assertFalse(stop.isClosed());
        Assertions.assertEquals(connectionTime,stop.getConnectionTime());
    }

    @Test
    public void ensureStopIsCreatedWState(){
        Integer id = 1;
        Integer connectionTime = 0;
        String name = "name";
        String city = "city";
        String state = "OPENED";
        Collection<String> stringTypes = List.of("BUS");

        Stop stop = new Stop(id,name,city,state,stringTypes);

        Assertions.assertEquals(id,stop.getId());
        Assertions.assertEquals(name,stop.getName());
        Assertions.assertEquals(city,stop.getCity());
        Assertions.assertFalse(stop.isClosed());
        Assertions.assertEquals(connectionTime,stop.getConnectionTime());
    }

    @Test
    public void ensureStringTypesAreValid(){
        //arrange
        Collection<String> stringTypes = List.of("BUS","SUBWAY");
        //act
        Stop stop = new Stop(1,"name","city",stringTypes);
        //assert
        Collection<String> actualStringTypes = stop.getStringTypes();
        Assertions.assertEquals(stringTypes.size(),actualStringTypes.size());
        stringTypes.forEach(stringType -> Assertions.assertTrue(actualStringTypes.contains(stringType)));
    }

    @Test
    public void ensureStringTypesDoNotExist(){
        //arrange
        Collection<String> stringTypes = List.of("BUA","SUBWAY");
        //act and assert
        Assertions.assertThrows(InvalidTypeException.class, () -> {
            new Stop(1,"name","city",stringTypes);
        });
    }

    @Test
    public void ensureStringTypesIsEmpty(){
        //arrange
        Collection<String> stringTypes = Collections.emptyList();
        //act and assert
        Assertions.assertThrows(InvalidTypeException.class, () -> {
            new Stop(1,"name","city",stringTypes);
        });
    }

    @Test
    public void ensureStringStateIsValid(){
        //arrange
        String stringState = "OPENED";
        //act
        Stop stop = new Stop(1,"name","city",stringState,List.of("BUS","SUBWAY"));
        //assert
        Assertions.assertEquals(stringState,stop.getState().name());
    }

    @Test
    public void ensureStringStateDoesNotExist(){
        //arrange
        String stringState = "SEMI-OPENED";
        //act and assert
        Assertions.assertThrows(InvalidStateException.class, () ->{
            new Stop(1,"name","city",stringState,List.of("BUS","SUBWAY"));
        });
    }

    @Test
    public void ensureUpdateConnectionTimeIsPositiveValue(){
        Integer connectionTime = 10;
        Integer id = 1;
        String name = "name";
        String city = "city";
        Collection<String> stringTypes = List.of("BUS");

        Stop stop = new Stop(id,name,city,stringTypes);
        stop.updateConnectionTime(connectionTime);

        Assertions.assertEquals(connectionTime,stop.getConnectionTime());
    }

    @Test
    public void ensureUpdateConnectionTimeIsNotNegativeValue(){
        Integer connectionTime = -10;
        Integer expectedConnectionTime = 0;
        Integer id = 1;
        String name = "name";
        String city = "city";
        Collection<String> stringTypes = List.of("BUS");

        Stop stop = new Stop(id,name,city,stringTypes);
        stop.updateConnectionTime(connectionTime);

        Assertions.assertEquals(expectedConnectionTime,stop.getConnectionTime());
    }

    @Test
    public void ensureStopEqualsWorks(){
        Integer id1 = 1;
        Integer id2 = 2;
        String name = "name";
        String city = "city";
        Collection<String> stringTypes = List.of("BUS");

        Stop stop1 = new Stop(id1,name,city,stringTypes);
        Stop stop2 = new Stop(id2,name,city,stringTypes);
        Stop stop3 = new Stop(id1,name,city,stringTypes);

        Assertions.assertEquals(stop1,stop3);
        Assertions.assertEquals(stop1,stop1);
        Assertions.assertNotEquals(stop1,stop2);
        Assertions.assertNotEquals(null,stop1);
        Assertions.assertNotEquals(stop1,new Object());
    }

    @Test
    public void ensureStopHashCodeWorks(){
        Integer id1 = 1;
        Integer id2 = 2;
        String name = "name";
        String city = "city";
        Collection<String> stringTypes = List.of("BUS");

        Stop stop1 = new Stop(id1,name,city,stringTypes);
        Stop stop2 = new Stop(id2,name,city,stringTypes);

        Assertions.assertEquals(stop1.hashCode(),stop1.hashCode());
        Assertions.assertNotEquals(stop1.hashCode(),stop2.hashCode());
    }
}
