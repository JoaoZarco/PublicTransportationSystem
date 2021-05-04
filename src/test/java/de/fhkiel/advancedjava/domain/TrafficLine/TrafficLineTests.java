package de.fhkiel.advancedjava.domain.TrafficLine;

import de.fhkiel.advancedjava.domain.stop.Stop;
import de.fhkiel.advancedjava.domain.trafficline.Section;
import de.fhkiel.advancedjava.domain.trafficline.TrafficLine;
import de.fhkiel.advancedjava.exception.stop.InvalidTypeException;
import de.fhkiel.advancedjava.exception.trafficline.InvalidSectionListException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TrafficLineTests {


    Stop stop1;
    Stop stop2;
    Stop stop3;

    @BeforeEach
    public void beforeEach(){
        Collection<String> stringTypes = List.of("BUS","SUBWAY");
        //act
        stop1 = new Stop(1,"name","city",stringTypes);
        stop2 = new Stop(2,"name","city",stringTypes);
        stop3 = new Stop(3,"name","city",stringTypes);
    }


    @Test
    public void ensureTrafficLineIsCreated() {
        Section section1 = new Section(stop1, stop2, "BUS", 5);
        Section section2 = new Section(stop2, stop3, "BUS", 10);
        List<Section> sections = List.of(section1, section2);
        int id = 1;
        String name = "LINE1";
        String stringType = "BUS";


        TrafficLine trafficLine = new TrafficLine(id, name, stringType, sections);

        Assertions.assertEquals(id, trafficLine.getId());
        Assertions.assertEquals(name, trafficLine.getName());
        Assertions.assertEquals(stringType, trafficLine.getType().name());
        Assertions.assertEquals(sections, trafficLine.getSections());
    }

    @Test
    public void ensureTrafficLineCreationDoesNotAllowEmptyListOfSections() {
        List<Section> sections = Collections.emptyList();
        int id = 1;
        String name = "LINE1";
        String stringType = "BUS";

        Assertions.assertThrows(InvalidSectionListException.class, () -> {
            new TrafficLine(id, name, stringType, sections);
        });
    }

    @Test
    public void ensureTrafficLineCreationDoesNotAllowNonConsecutiveSections() {
        Section section1 = new Section(stop1, stop2, "BUS", 5);
        Section section2 = new Section(stop3, stop1, "BUS", 10);
        List<Section> sections = List.of(section1, section2);
        int id = 1;
        String name = "LINE1";
        String stringType = "BUS";

        Assertions.assertThrows(InvalidSectionListException.class, () -> {
            new TrafficLine(id, name, stringType, sections);
        });
    }

    @Test
    public void ensureTrafficLineCreationDoesNotAllowWrongTypes() {
        Section section1 = new Section(stop1, stop2, "BUS", 5);
        Section section2 = new Section(stop2, stop3, "BUS", 10);
        List<Section> sections = List.of(section1, section2);
        int id = 1;
        String name = "LINE1";
        String stringType = "UFF";

        Assertions.assertThrows(InvalidTypeException.class, () -> {
            new TrafficLine(id, name, stringType, sections);
        });
    }
}
