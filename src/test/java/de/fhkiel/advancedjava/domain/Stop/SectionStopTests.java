package de.fhkiel.advancedjava.domain.Stop;

import de.fhkiel.advancedjava.domain.common.Type;
import de.fhkiel.advancedjava.domain.stop.SectionStop;
import de.fhkiel.advancedjava.domain.stop.Stop;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

public class SectionStopTests {

    @Test
    public void ensureSectionStopEqualsWorks(){
        Integer id1 = 1;
        Integer id2 = 2;
        String name = "name";
        String city = "city";
        Collection<String> stringTypes = List.of("BUS","SUBWAY");

        Stop stop1 = new Stop(id1,name,city,stringTypes);
        Stop stop2 = new Stop(id1,name,city,stringTypes);
        Stop stop3 = new Stop(id2,name,city,stringTypes);
        SectionStop busSectionStop1 = stop1.getSectionStopByType(Type.valueOf("BUS")).get();
        SectionStop subwaySectionStop1 = stop1.getSectionStopByType(Type.valueOf("SUBWAY")).get();
        SectionStop busSectionStop2 = stop2.getSectionStopByType(Type.valueOf("BUS")).get();
        SectionStop busSectionStop3 = stop3.getSectionStopByType(Type.valueOf("BUS")).get();

        Assertions.assertNotEquals(null, busSectionStop1);
        Assertions.assertNotEquals(busSectionStop1,busSectionStop3);
        Assertions.assertNotEquals(busSectionStop1, stop1);
        Assertions.assertNotEquals(busSectionStop1, subwaySectionStop1);
        Assertions.assertEquals(busSectionStop1, busSectionStop1);
        Assertions.assertEquals(busSectionStop1,busSectionStop2);

    }

    @Test
    public void ensureSectionStopHashcodeWorks(){
        Integer id1 = 1;
        Integer id2 = 2;
        String name = "name";
        String city = "city";
        Collection<String> stringTypes = List.of("BUS","SUBWAY");

        Stop stop1 = new Stop(id1,name,city,stringTypes);
        Stop stop2 = new Stop(id2,name,city,stringTypes);
        SectionStop busSectionStop = stop1.getSectionStopByType(Type.valueOf("BUS")).get();
        SectionStop subwaySectionStop = stop1.getSectionStopByType(Type.valueOf("SUBWAY")).get();
        SectionStop busSectionStop2 = stop2.getSectionStopByType(Type.valueOf("BUS")).get();

        Assertions.assertEquals(busSectionStop.hashCode(),busSectionStop.hashCode());
        Assertions.assertNotEquals(busSectionStop.hashCode(),busSectionStop2.getType());
        Assertions.assertNotEquals(busSectionStop.hashCode(),subwaySectionStop.hashCode());
    }

    @Test
    public void ensureSectionStopHasNoConnections(){
        Integer id1 = 1;
        String name = "name";
        String city = "city";
        Collection<String> stringTypes = List.of("BUS","SUBWAY");

        Stop stop = new Stop(id1,name,city,stringTypes);

        SectionStop busSectionStop = stop.getSectionStopByType(Type.valueOf("BUS")).get();

        Assertions.assertTrue(busSectionStop.hasNoConnections());
    }

    @Test
    public void ensureSectionStopHasConnections(){
        Integer id1 = 1;
        Integer id2 = 2;
        String name = "name";
        String city = "city";
        Collection<String> stringTypes = List.of("BUS","SUBWAY");

        Stop stop1 = new Stop(id1,name,city,stringTypes);
        Stop stop2 = new Stop(id2,name,city,stringTypes);

        SectionStop busSectionStop1 = stop1.getSectionStopByType(Type.valueOf("BUS")).get();
        SectionStop busSectionStop2 = stop2.getSectionStopByType(Type.valueOf("BUS")).get();

        busSectionStop1.addConnection(busSectionStop2,10);

        Assertions.assertFalse(busSectionStop1.hasNoConnections());
    }
}
