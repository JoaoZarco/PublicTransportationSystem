package de.fhkiel.advancedjava.dto.schedule;

import de.fhkiel.advancedjava.dto.stop.ResponseStopDto;
import de.fhkiel.advancedjava.dto.trafficline.ResponseTrafficLineDto;

import java.util.Collection;

public class ResponseScheduleDto {

    public Collection<ResponseStopDto> stops;
    public Collection<ResponseTrafficLineDto> trafficLines;

    public ResponseScheduleDto(Collection<ResponseStopDto> stops, Collection<ResponseTrafficLineDto> trafficLines) {
        this.stops = stops;
        this.trafficLines = trafficLines;
    }
}
