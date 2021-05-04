package de.fhkiel.advancedjava.persistence;

import de.fhkiel.advancedjava.domain.vehicle.Vehicle;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface VehicleRepository extends Neo4jRepository<Vehicle, Long> {

    @Query("MATCH(v:Vehicle {id:$0})-[r:IS_CURRENTLY_IN]->(:Stop)" +
            "DELETE r")
    Boolean deleteVehicleRelationship(Long id);

}
