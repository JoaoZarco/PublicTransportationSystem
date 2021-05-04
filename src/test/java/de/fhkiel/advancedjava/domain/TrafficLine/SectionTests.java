package de.fhkiel.advancedjava.domain.TrafficLine;

import de.fhkiel.advancedjava.domain.common.Type;
import de.fhkiel.advancedjava.domain.stop.SectionStop;
import de.fhkiel.advancedjava.domain.stop.Stop;
import de.fhkiel.advancedjava.domain.trafficline.Section;
import de.fhkiel.advancedjava.exception.section.InvalidSectionStops;
import de.fhkiel.advancedjava.exception.stop.InvalidTypeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;


public class SectionTests {


    private Stop stop1;
    private Stop stop2;

    @BeforeEach
    public void beforeEach(){
        Collection<String> stringTypes = List.of("BUS","SUBWAY");
        //act
        stop1 = new Stop(1,"name","city","OPENED",stringTypes);
        stop2 = new Stop(2,"name","city","OPENED",stringTypes);
    }

    @Test
    public void ensureSectionIsCreated(){
        int duration = 10;
        String stringType = "BUS";

        Section section = new Section(stop1,stop2,stringType,duration);

        Assertions.assertEquals(stop1,section.getStartStop());
        Assertions.assertEquals(stop2,section.getEndStop());
        Assertions.assertEquals(stringType,section.getType().name());
        Assertions.assertEquals(duration,section.getDurationInMinutes());
    }

    @Test
    public void ensureSectionTypeIsInvalid() {
        int duration = 10;
        String stringType = "UFF";

        Assertions.assertThrows(InvalidTypeException.class,
                ()-> new Section(stop1,stop2,stringType,duration));
    }

    @Test
    public void ensureSectionStopIdsCannotBeTheSame(){
        int duration = 10;
        String stringType = "BUS";

        Assertions.assertThrows(InvalidSectionStops.class,
                ()-> new Section(stop1,stop1,stringType,duration));
    }

    @Test
    public void ensureSectionDurationIsSetCorrectlyWhenNegative(){
        int duration = -10;
        String stringType = "BUS";

        Section section = new Section(stop1,stop2,stringType,duration);


        Assertions.assertEquals(0,section.getDurationInMinutes());
    }

    @Test
    public void ensureSectionDurationIsSetCorrectlyWhenPositive(){
        int duration = 15;
        String stringType = "BUS";

        Section section = new Section(stop1,stop2,stringType,duration);

        Assertions.assertEquals(duration,section.getDurationInMinutes());
    }

    @Test
    public void ensureSectionStopsAreNotFunctional(){
        String stringType = "BUS";
        Stop closedStop = new Stop(3,"name","city",List.of(stringType));

        Section section = new Section(stop1,closedStop,stringType,0);
        SectionStop sectionStop = section.getStartStop().getSectionStopByType(Type.valueOf(stringType)).get();

        Assertions.assertTrue(sectionStop.hasNoConnections());
    }

    @Test
    public void ensureSectionEndStopSectionStopsHaveNoCompatibleTypes(){
        String stringType = "BUS";
        Stop stop = new Stop(3,"name","city","OPENED",List.of("SUBURBAN_TRAIN"));

        Assertions.assertThrows(InvalidSectionStops.class, () -> new Section(stop1,stop,stringType,0));
    }

    @Test
    public void ensureSectionStartStopSectionStopsHaveNoCompatibleTypes(){
        String stringType = "BUS";
        Stop stop = new Stop(3,"name","city","OPENED",List.of("SUBURBAN_TRAIN"));

        Assertions.assertThrows(InvalidSectionStops.class, () -> new Section(stop,stop1,stringType,0));
    }

    @Test
    public void ensureSectionEqualsWorks(){
        Section section1 = new Section(stop1,stop2,"BUS",10);
        Section section2 = new Section(stop1,stop2,"BUS",10);
        Section section3 = new Section(stop2,stop1,"BUS",10);
        Section section4 = new Section(stop1,stop2,"SUBWAY",10);

        Assertions.assertEquals(section1,section2);
        Assertions.assertEquals(section1,section1);
        Assertions.assertNotEquals(null,section1);
        Assertions.assertNotEquals(section1,new Object());
        Assertions.assertNotEquals(section1,section3);
        Assertions.assertNotEquals(section1,section4);
    }

    @Test
    public void ensureSectionHashCodeWorks(){
        Section section1 = new Section(stop1,stop2,"BUS",10);
        Section section2 = new Section(stop1,stop2,"BUS",10);
        Section section3 = new Section(stop2,stop1,"BUS",10);
        Section section4 = new Section(stop1,stop2,"SUBWAY",10);

        Assertions.assertEquals(section1.hashCode(),section2.hashCode());
        Assertions.assertNotEquals(section1.hashCode(),section3.hashCode());
        Assertions.assertNotEquals(section1.hashCode(),section4.hashCode());
    }
}
