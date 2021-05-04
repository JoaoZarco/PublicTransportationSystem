package de.fhkiel.advancedjava.dto.path;

import de.fhkiel.advancedjava.dto.stop.ResponseStopDto;

public class PathStopDto {

    public ResponseStopDto stop;
    public String type;
    public Integer cumulativeCost;

    public PathStopDto(ResponseStopDto stop, String type, Integer cumulativeCost) {
        this.type = type;
        this.stop = stop;
        this.cumulativeCost = cumulativeCost;
    }
}
