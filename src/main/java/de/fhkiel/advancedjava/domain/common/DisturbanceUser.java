package de.fhkiel.advancedjava.domain.common;

import de.fhkiel.advancedjava.domain.disturbance.Disturbance;
import org.neo4j.ogm.annotation.PostLoad;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

public abstract class DisturbanceUser extends Functional {

    @Relationship(type = "AS_A")
    private Set<Disturbance> disturbances;
    private boolean outOfOrder;

    public DisturbanceUser() {
        super();
        disturbances = new HashSet<>();
        this.outOfOrder = false;
    }

    public boolean isOutOfOrder() {
        return outOfOrder;
    }

    public Set<Disturbance> getDisturbances() {
        return new HashSet<>(disturbances);
    }

    public void addDisturbance(Disturbance disturbance) {
        disturbances.add(disturbance);
        setOutOfOrder(true);
    }

    public void resolveDisturbance(String description) {
        int counter = 0;
        if (disturbances != null) {
            for (Disturbance d : disturbances) {
                if (!d.isResolved() && d.getDescription().equals(description)) {
                    d.resolve();
                }
                if (d.isResolved()) {
                    counter++;
                }
            }
            if (disturbances.size() - counter == 0) {
                setOutOfOrder(false);
            }
        }
    }


    @Override
    public boolean isFunctional() {
        return !outOfOrder;
    }

    @PostLoad
    public void postLoad() {
        saveFunctional();
        if (disturbances == null) disturbances = new HashSet<>();
    }

    public void setOutOfOrder(boolean outOfOrder) {
        this.outOfOrder = outOfOrder;
    }

}
