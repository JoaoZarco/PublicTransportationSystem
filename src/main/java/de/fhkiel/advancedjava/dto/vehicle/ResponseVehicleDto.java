package de.fhkiel.advancedjava.dto.vehicle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvRecurse;
import de.fhkiel.advancedjava.dto.stop.ResponseStopDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseVehicleDto {

    @CsvBindByPosition(position = 0)
    public Long id;
    @CsvBindByPosition(position = 1)
    public String description;
    @CsvBindByPosition(position = 2)
    public Boolean assigned;
    @CsvRecurse
    public ResponseStopDto stop;

    public ResponseVehicleDto(long id, String description, boolean assigned, ResponseStopDto stop) {
        this.id = id;
        this.description = description;
        this.assigned = assigned;
        this.stop = stop;
    }
}
