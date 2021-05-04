package de.fhkiel.advancedjava.dto.disturbance;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ResponseDisturbanceDto {

    public long id;
    public String description;
    public String startTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String endTime;

    public ResponseDisturbanceDto(long id, String description, String startTime, String endTime) {
        this.id = id;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }


}
