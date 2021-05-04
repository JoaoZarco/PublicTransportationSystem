package de.fhkiel.advancedjava.dto.path;

public class ResponsePathDto extends PathDto {
    public Iterable<PathStopDto> stops;

    public ResponsePathDto(Iterable<PathStopDto> stops) {
        this.stops = stops;
    }
}
