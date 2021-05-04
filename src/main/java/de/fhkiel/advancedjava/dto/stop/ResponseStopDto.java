package de.fhkiel.advancedjava.dto.stop;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.opencsv.bean.CsvBindByPosition;
import de.fhkiel.advancedjava.dto.disturbance.ResponseDisturbanceDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseStopDto {

    @CsvBindByPosition(position = 3)
    public Integer id;
    @CsvBindByPosition(position = 4)
    public String name;
    @CsvBindByPosition(position = 5)
    public String city;
    public String state;
    public Iterable<String> types;
    public Boolean outOfOrder;
    public Integer connectionTime;
    public Iterable<ResponseDisturbanceDto> disturbances;


    public ResponseStopDto(int id, String name, String city, String state, Iterable<String> types, boolean outOfOrder, Integer connectionTime) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.state = state;
        this.types = types;
        this.outOfOrder = outOfOrder;
        this.connectionTime = connectionTime;
    }

    public ResponseStopDto(int id, String name, String city, String state, Iterable<String> types, boolean outOfOrder, Integer connectionTime, Iterable<ResponseDisturbanceDto> disturbances) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.state = state;
        this.types = types;
        this.outOfOrder = outOfOrder;
        this.connectionTime = connectionTime;
        this.disturbances = disturbances;
    }

    public ResponseStopDto(int id, String name, String city, Iterable<String> types) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.types = types;
    }
}
