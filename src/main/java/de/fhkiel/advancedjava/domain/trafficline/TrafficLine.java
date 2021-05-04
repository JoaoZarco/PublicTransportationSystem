package de.fhkiel.advancedjava.domain.trafficline;

import de.fhkiel.advancedjava.domain.common.Type;
import de.fhkiel.advancedjava.exception.stop.InvalidTypeException;
import de.fhkiel.advancedjava.exception.trafficline.InvalidSectionListException;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.LinkedList;
import java.util.List;

@NodeEntity
public class TrafficLine {

    @Id
    private Integer id;
    private String name;
    private Type type;
    private int position;

    @Relationship(type = "CONTAINS")
    private List<TrafficLineSection> sections;

    //mapping purposes
    private TrafficLine() {

    }

    public TrafficLine(int id, String name, String type, List<Section> sections) {
        this.id = id;
        this.name = name;
        this.sections = new LinkedList<>();
        validateSectionsOrder(sections);
        validateSectionList(sections);
        sections.forEach(this::addSection);
        setType(type);
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public List<Section> getSections() {
        List<Section> sectionList = new LinkedList<>();
        sections.forEach(section -> sectionList.add(section.getSection()));
        return sectionList;
    }


    private void setType(String stringType) {
        try {
            this.type = Type.valueOf(stringType);
        } catch (IllegalArgumentException e) {
            throw new InvalidTypeException("Invalid type, type does not exist");
        }
    }

    private void addSection(Section section) {
        sections.add(new TrafficLineSection(position, section));
        position++;
    }

    private void validateSectionList(List<Section> sections) {
        if (sections.isEmpty())
            throw new InvalidSectionListException("Invalid section List, there must be at least one section in the traffic line");
    }


    private void validateSectionsOrder(List<Section> sections) {
        for (int i = 0; i < sections.size() - 1; i++) {
            if (!sections.get(i).getEndStop().getId().
                    equals(sections.get(i + 1).getStartStop().getId())
            ) throw new InvalidSectionListException("Invalid Section List, sections must be consecutive");
        }
    }

}

