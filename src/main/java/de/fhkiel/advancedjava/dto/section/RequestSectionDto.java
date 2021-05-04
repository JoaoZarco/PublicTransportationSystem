package de.fhkiel.advancedjava.dto.section;

public class RequestSectionDto {
    public int beginStopId;
    public int endStopId;
    public int durationInMinutes;

    public RequestSectionDto() {

    }

    public RequestSectionDto(int beginStopId, int endStopId, int durationInMinutes) {
        this.beginStopId = beginStopId;
        this.endStopId = endStopId;
        this.durationInMinutes = durationInMinutes;
    }
}
