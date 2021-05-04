package de.fhkiel.advancedjava.domain.stop;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity
public class Connection {

    @Id
    @GeneratedValue
    private Long id;
    @StartNode
    private Access startAccess;
    @EndNode
    private Access endAccess;
    @Property
    private Integer cost;

    //mapping purposes
    private Connection() {

    }

    Connection(Access startAccess, Access endAccess, Integer cost) {
        this.startAccess = startAccess;
        this.endAccess = endAccess;
        this.cost = cost;
    }

    void setCost(Integer cost) {
        this.cost = cost;
    }
}
