package de.fhkiel.advancedjava.domain.trafficline;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class TrafficLineSection {

    @Id
    @GeneratedValue
    private Long id;

    private final Integer position;

    @Relationship(type = "IS_IN")
    private Section section;

    public TrafficLineSection(Integer position, Section section) {
        this.position = position;
        this.section = section;
    }

    public Integer getPosition() {
        return position;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}
