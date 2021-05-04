package de.fhkiel.advancedjava.domain.vehicle;

import de.fhkiel.advancedjava.domain.stop.DomainService;
import de.fhkiel.advancedjava.domain.stop.Stop;
import de.fhkiel.advancedjava.exception.stop.InvalidStopException;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Vehicle {

    @Id
    @GeneratedValue
    private Long id;

    private Boolean assigned;
    private String description;

    @Relationship(type = "IS_CURRENTLY_IN")
    private Stop currentStop;

    public Vehicle(String description) {
        this.description = description;
        this.assigned = false;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getAssigned() {
        return assigned;
    }

    public Stop getCurrentStop() {
        return currentStop;
    }

    public void setAssigned() {
        this.assigned = true;
    }

    public void setCurrentStop(Stop currentStop) {
        DomainService.validateFunctionality(currentStop);
        this.currentStop = currentStop;
    }

}
