package de.fhkiel.advancedjava.domain.stop;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Access {

    @Id
    @GeneratedValue
    private Long graphId;
    private Integer stopId;
    private boolean isStartingAccess;
    private boolean isEndingAccess;

    //mapping
    Access() {

    }

    Access(Integer stopId, boolean isStartingAccess, boolean isEndingAccess) {
        this.stopId = stopId;
        this.isStartingAccess = isStartingAccess;
        this.isEndingAccess = isEndingAccess;
    }


    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;
        if (!(o instanceof Access)) {
            return false;
        }
        Access access = (Access) o;
        return stopId .equals(access.stopId);
    }

    @Override
    public int hashCode() {
        return stopId.hashCode();
    }

  
}
