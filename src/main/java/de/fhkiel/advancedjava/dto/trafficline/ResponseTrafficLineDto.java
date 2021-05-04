package de.fhkiel.advancedjava.dto.trafficline;

import de.fhkiel.advancedjava.dto.section.ResponseSectionDto;

import java.util.Collection;

public class ResponseTrafficLineDto {
    public int lineId;
    public String name;
    public String type;
    public Collection<ResponseSectionDto> sections;

    public ResponseTrafficLineDto(int lineId, String name, String type, Collection<ResponseSectionDto> sections) {
        this.lineId = lineId;
        this.name = name;
        this.type = type;
        this.sections = sections;
    }
}
