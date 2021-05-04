package de.fhkiel.advancedjava.domain.trafficline;

import de.fhkiel.advancedjava.domain.common.DisturbanceUser;
import de.fhkiel.advancedjava.domain.common.Type;
import de.fhkiel.advancedjava.domain.common.TypeDomainService;
import de.fhkiel.advancedjava.domain.stop.SectionStop;
import de.fhkiel.advancedjava.domain.stop.Stop;
import de.fhkiel.advancedjava.exception.section.InvalidSectionStops;
import de.fhkiel.advancedjava.exception.stop.InvalidTypeException;
import org.neo4j.ogm.annotation.*;

import java.util.Optional;

@NodeEntity
public class Section extends DisturbanceUser {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "duration")
    private Integer durationInMinutes;

    private Type type;

    @Relationship(type = "BEGINS_IN")
    private Stop startStop;
    @Relationship(type = "ENDS_IN")
    private Stop endStop;

    //mapping purposes
    private Section() {
        super();
    }


    public Section(Stop startStop, Stop endStop, String type, int durationInMinutes) {
        super();
        setType(type);
        setDuration(durationInMinutes);
        setStops(startStop, endStop);
    }

    public Long getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public Stop getStartStop() {
        return startStop;
    }

    public Stop getEndStop() {
        return endStop;
    }

    public void setType(String stringType) {
        this.type = TypeDomainService.getType(stringType);
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setStops(Stop startStop, Stop endStop) {
        validateStops(startStop, endStop);
        //set stops
        setStartStop(startStop);
        setEndStop(endStop);

        if (!startStop.isFunctional() || !endStop.isFunctional()) return;
        //make connection
        Optional<SectionStop> startSectionStopOpt = startStop.getSectionStopByType(this.type);
        Optional<SectionStop> endSectionStopOpt = endStop.getSectionStopByType(this.type);
        if (startSectionStopOpt.isEmpty())
            throw new InvalidSectionStops(String.format("Stop with id %d does not have %s stop", startStop.getId(), this.type));
        if (endSectionStopOpt.isEmpty())
            throw new InvalidSectionStops(String.format("Stop with id %d does not have %s stop", endStop.getId(), this.type));
        startSectionStopOpt.get().addConnection(endSectionStopOpt.get(), this.durationInMinutes);
    }

    private void setStartStop(Stop startStop) {
        this.startStop = startStop;
    }

    private void setEndStop(Stop endStop) {
        this.endStop = endStop;
    }


    public void setDuration(int durationInMinutes) {
        if (durationInMinutes < 0) this.durationInMinutes = 0;
        else this.durationInMinutes = durationInMinutes;
    }

    private void validateStops(Stop startStop, Stop endStop) {
        if (startStop.equals(endStop))
            throw new InvalidSectionStops("Invalid section Stops, begin stop id cannot be the same as the end stop id");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Section)) return false;

        Section section = (Section) o;

        if (type != section.type) return false;
        if (!startStop.equals(section.startStop)) return false;
        return endStop.equals(section.endStop);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + startStop.hashCode();
        result = 31 * result + endStop.hashCode();
        return result;
    }
}
