package de.fhkiel.advancedjava.dto.stop;


import java.util.Collection;

public class RequestStopDto {


    public int stopId;
    public Collection<String> types;
    public String state;
    public String name;
    public String city;
    public Integer connectionTime;


    public RequestStopDto() {

    }

    public RequestStopDto(int stopId, Collection<String> types, String state, String name, String city, Integer connectionTime) {
        this.stopId = stopId;
        this.types = types;
        this.state = state;
        this.name = name;
        this.city = city;
        this.connectionTime = connectionTime;
    }
}
