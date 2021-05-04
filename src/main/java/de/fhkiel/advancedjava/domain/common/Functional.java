package de.fhkiel.advancedjava.domain.common;

import org.neo4j.ogm.annotation.Transient;

public abstract class Functional {

    @Transient
    private boolean isFunctional;

    public abstract boolean isFunctional();

    public void saveFunctional() {
        this.isFunctional = isFunctional();
    }

    public boolean hasFunctionalChanged() {
        return isFunctional != isFunctional();
    }
}
