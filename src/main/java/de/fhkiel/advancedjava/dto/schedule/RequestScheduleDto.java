package de.fhkiel.advancedjava.dto.schedule;

import de.fhkiel.advancedjava.dto.stop.RequestStopDto;
import de.fhkiel.advancedjava.dto.trafficline.RequestTrafficLineDto;

public class RequestScheduleDto {

    public Iterable<RequestStopDto> stops;
    public Iterable<RequestTrafficLineDto> trafficLines;

    public RequestScheduleDto(Iterable<RequestStopDto> stops, Iterable<RequestTrafficLineDto> trafficLines) {
        this.stops = stops;
        this.trafficLines = trafficLines;
    }

    public RequestScheduleDto() {

    }
}
