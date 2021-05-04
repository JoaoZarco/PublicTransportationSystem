package de.fhkiel.advancedjava.dto.section;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.fhkiel.advancedjava.dto.disturbance.ResponseDisturbanceDto;

import java.util.Collection;

public class ResponseSectionDto {

    public int beginStopId;
    public int endStopId;
    public String type;
    public int durationInMinutes;
    public boolean outOfOrder;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Collection<ResponseDisturbanceDto> disturbances;


    public ResponseSectionDto(int beginStopId, int endStopId, int durationInMinutes, boolean outOfOrder, String type) {
        this.beginStopId = beginStopId;
        this.endStopId = endStopId;
        this.type = type;
        this.durationInMinutes = durationInMinutes;
        this.outOfOrder = outOfOrder;
    }

    public ResponseSectionDto(int beginStopId, int endStopId, int durationInMinutes, boolean outOfOrder, String type, Collection<ResponseDisturbanceDto> disturbances) {
        this.beginStopId = beginStopId;
        this.endStopId = endStopId;
        this.type = type;
        this.durationInMinutes = durationInMinutes;
        this.outOfOrder = outOfOrder;
        this.disturbances = disturbances;
    }


}
