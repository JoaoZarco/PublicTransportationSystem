package de.fhkiel.advancedjava.domain.stop;

import de.fhkiel.advancedjava.domain.common.DisturbanceUser;
import de.fhkiel.advancedjava.domain.common.Type;
import de.fhkiel.advancedjava.exception.stop.InvalidStateException;
import de.fhkiel.advancedjava.exception.stop.InvalidTypeException;
import org.neo4j.ogm.annotation.*;
import org.springframework.cache.annotation.Cacheable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@NodeEntity
@CompositeIndex
public class Stop extends DisturbanceUser {

    public enum State {
        OPENED, CLOSED
    }

    @Id
    private Integer id;
    private String city;
    @Index(unique = true)
    private String name;
    private State state;
    private Integer connectionTime;


    @Relationship(type = "MANAGES")
    private Set<SectionStop> sectionStops;

    //required for mapping
    private Stop() {
        super();
    }

    public Stop(Integer id, String name, String city, String state, Collection<String> stringTypes) {
        super();
        this.id = id;
        this.sectionStops = new HashSet<>();
        setName(name);
        setCity(city);
        setState(state);
        setConnectionTime(0);
        createSectionStops(stringTypes);
    }

    public Stop(Integer id, String name, String city, String state, Collection<String> stringTypes, Integer connectionTime) {
        super();
        this.id = id;
        this.sectionStops = new HashSet<>();
        setName(name);
        setCity(city);
        setState(state);
        setConnectionTime(connectionTime);
        createSectionStops(stringTypes);
    }

    public Stop(Integer id, String name, String city, Collection<String> stringTypes) {
        super();
        this.id = id;
        this.sectionStops = new HashSet<>();
        setName(name);
        setCity(city);
        setState(State.CLOSED);
        setConnectionTime(0);
        createSectionStops(stringTypes);
    }

    public Stop(Integer id, String name, String city, Collection<String> stringTypes, Integer connectionTime) {
        super();
        this.id = id;
        this.sectionStops = new HashSet<>();
        setName(name);
        setCity(city);
        setState(State.CLOSED);
        setConnectionTime(connectionTime);
        createSectionStops(stringTypes);
    }

    public void closeStop() {
        this.state = State.CLOSED;
    }

    public void openStop() {
        this.state = State.OPENED;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public State getState() {
        return state;
    }

    public boolean isClosed() {
        return state.equals(State.CLOSED);
    }

    public Integer getConnectionTime() {
        return connectionTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private void setState(State state) {
        this.state = state;
    }

    private void setState(String stringState) {
        try {
            this.state = State.valueOf(stringState);
        } catch (IllegalArgumentException e) {
            throw new InvalidStateException("Invalid state, state does not exist");
        }
    }

    public void setConnectionTime(Integer connectionTime) {
        if (connectionTime < 0) this.connectionTime = 0;
        else this.connectionTime = connectionTime;
    }

    public void updateConnectionTime(Integer connectionTime) {
        setConnectionTime(connectionTime);
        if (sectionStops != null) {
            sectionStops.forEach(sectionStop -> sectionStop.updateConnectionTime(connectionTime));
        }
    }


    public void createSectionStops(Collection<String> stringTypes) {
        validateTypesNumber(stringTypes);
        Access startingAccess = new Access(id, true, false);
        Access endingAccess = new Access(id, false, true);
        for (String stringType : stringTypes) {
            SectionStop sectionStop = new SectionStop(this.id, stringType, sectionStops, connectionTime, startingAccess, endingAccess);
            sectionStops.add(sectionStop);
        }
    }

    public Collection<String> getStringTypes() {
        Set<String> set = new HashSet<>();
        sectionStops.forEach(sectionStop -> set.add(sectionStop.getType().name()));
        return set;
    }

    @Cacheable("sectionStopTypes")
    public Optional<SectionStop> getSectionStopByType(Type type) {
        for (SectionStop sectionStop : sectionStops) {
            if (sectionStop.getType().equals(type))
                return Optional.of(sectionStop);
        }
        return Optional.empty();
    }


    @Override
    public boolean isFunctional() {
        return !isOutOfOrder() && state.equals(State.OPENED);
    }

    private void validateTypesNumber(Collection<String> stringTypes) {
        int size = stringTypes.size();
        if (size == 0) throw new InvalidTypeException("Invalid number of types, a station must have at least one type");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stop stop = (Stop) o;

        return id.equals(stop.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}

