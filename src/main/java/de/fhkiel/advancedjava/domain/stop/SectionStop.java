package de.fhkiel.advancedjava.domain.stop;

import de.fhkiel.advancedjava.domain.common.Type;
import de.fhkiel.advancedjava.domain.common.TypeDomainService;
import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Set;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@NodeEntity
public class SectionStop extends Access {

    private Type type;

    @Relationship(type = "CONNECTED_TO")
    private Set<Connection> connections;

    @Relationship(type = "CONNECTED_TO", direction = INCOMING)
    private Set<Connection> sameStopsIncoming;
    @Relationship(type = "CONNECTED_TO")
    private Set<Connection> sameStopsOutgoing;

    @Relationship(type = "CONNECTED_TO")
    private Set<Connection> endingAccess;
    @Relationship(type = "CONNECTED_TO", direction = INCOMING)
    private Set<Connection> startingAccess;


    //mapping purposes
    private SectionStop() {
        super();
    }

    SectionStop(Integer stopId, String stringType, Iterable<SectionStop> sameStops, Integer connectionTime, Access startingAccess, Access endingAccess) {
        super(stopId, false, false);
        connections = new HashSet<>();
        setType(stringType);
        setAccess(startingAccess, endingAccess);
        setSameStops(sameStops, connectionTime);
    }

    public void setType(String stringType) {
        this.type = TypeDomainService.getType(stringType);
    }

    void setSameStops(Iterable<SectionStop> sameStops, Integer connectionTime) {
        sameStopsIncoming = new HashSet<>();
        sameStopsOutgoing = new HashSet<>();
        sameStops.forEach(sectionStop -> {
                    sameStopsOutgoing.add(new Connection(this, sectionStop, connectionTime));
                    sameStopsIncoming.add(new Connection(sectionStop, this, connectionTime));
                }
        );
    }

    void setAccess(Access startingAccess, Access endingAccess) {
        this.startingAccess = new HashSet<>();
        this.endingAccess = new HashSet<>();
        this.startingAccess.add(new Connection(startingAccess, this, 0));
        this.endingAccess.add(new Connection(this, endingAccess, 0));
    }

    public void updateConnectionTime(Integer connectionTime) {
        if (!connections.isEmpty()) {
            sameStopsIncoming.forEach(connection -> connection.setCost(connectionTime));
            sameStopsOutgoing.forEach(connection -> connection.setCost(connectionTime));
        }
    }

    public void addConnection(SectionStop sectionStop, Integer cost) {
        connections.add(new Connection(this, sectionStop, cost));
    }

    public Type getType() {
        return type;
    }

    public boolean hasNoConnections() {
        return connections.isEmpty();
    }

    @PostLoad
    public void postLoad() {
        connections = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SectionStop)) return false;
        if (!super.equals(o)) return false;

        SectionStop that = (SectionStop) o;

        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
