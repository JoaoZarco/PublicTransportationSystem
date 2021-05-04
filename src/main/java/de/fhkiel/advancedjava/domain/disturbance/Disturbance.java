package de.fhkiel.advancedjava.domain.disturbance;

import org.neo4j.ogm.annotation.*;

import java.time.LocalDateTime;

@NodeEntity
@CompositeIndex
public class Disturbance {

    @Id
    @GeneratedValue
    private Long id;

    @Index(unique = true)
    private String description;
    @Index(unique = true)
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean resolved;


    public Disturbance(String description) {
        this.description = description;
        this.startTime = LocalDateTime.now();
        resolved = false;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }


    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void resolve() {
        endTime = LocalDateTime.now();
        this.resolved = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Disturbance)) return false;

        Disturbance that = (Disturbance) o;

        if (!description.equals(that.description)) return false;
        return startTime.equals(that.startTime);
    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + startTime.hashCode();
        return result;
    }
}
