package de.fhkiel.advancedjava.dto.trafficline;

import de.fhkiel.advancedjava.dto.section.RequestSectionDto;

import java.util.Collection;

public class RequestTrafficLineDto {
    public int lineId;
    public String name;
    public String type;
    public Collection<RequestSectionDto> sections;

    public RequestTrafficLineDto() {

    }

    public RequestTrafficLineDto(int lineId, String name, String type, Collection<RequestSectionDto> sections) {
        this.lineId = lineId;
        this.name = name;
        this.type = type;
        this.sections = sections;
    }


}
