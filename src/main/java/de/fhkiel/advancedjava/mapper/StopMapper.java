package de.fhkiel.advancedjava.mapper;

import de.fhkiel.advancedjava.domain.stop.Stop;
import de.fhkiel.advancedjava.dto.disturbance.ResponseDisturbanceDto;
import de.fhkiel.advancedjava.dto.stop.RequestStopDto;
import de.fhkiel.advancedjava.dto.stop.ResponseStopDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class StopMapper {

    private DisturbanceMapper disturbanceMapper;

    @Autowired
    public StopMapper(DisturbanceMapper disturbanceMapper) {
        this.disturbanceMapper = disturbanceMapper;
    }

    public ResponseStopDto stopToDTO(Stop stop) {
        return new ResponseStopDto(
                stop.getId(),
                stop.getName(),
                stop.getCity(),
                stop.getState().toString(),
                stop.getStringTypes(),
                stop.isOutOfOrder(),
                stop.getConnectionTime()
        );
    }

    public RequestStopDto stopToRequestDto(Stop stop) {
        return new RequestStopDto(
                stop.getId(),
                stop.getStringTypes(),
                stop.getState().name(),
                stop.getName(),
                stop.getCity(),
                stop.getConnectionTime()
        );
    }

    public Stop DTOToStop(RequestStopDto dto) {
        if (dto.connectionTime == null) {
            return new Stop(
                    dto.stopId,
                    dto.name,
                    dto.city,
                    dto.types
            );
        } else {
            return new Stop(
                    dto.stopId,
                    dto.name,
                    dto.city,
                    dto.types,
                    dto.connectionTime
            );
        }
    }

    public Stop DTOToStopWithState(RequestStopDto dto) {
        if (dto.connectionTime == null) {
            return new Stop(
                    dto.stopId,
                    dto.name,
                    dto.city,
                    dto.state,
                    dto.types
            );
        } else {
            return new Stop(
                    dto.stopId,
                    dto.name,
                    dto.city,
                    dto.state,
                    dto.types,
                    dto.connectionTime
            );
        }
    }

    public ResponseStopDto stopToDTOWithDisturbance(Stop stop) {
        Collection<ResponseDisturbanceDto> disturbanceDtos = new ArrayList<>();
        stop.getDisturbances().forEach(disturbance -> disturbanceDtos.add(disturbanceMapper.disturbanceToDTO(disturbance)));
        return new ResponseStopDto(
                stop.getId(),
                stop.getName(),
                stop.getCity(),
                stop.getState().name(),
                stop.getStringTypes(),
                stop.isOutOfOrder(),
                stop.getConnectionTime(),
                disturbanceDtos
        );
    }

    public ResponseStopDto stopToDTOWithNoDetail(Stop stop) {
        if (stop == null) return null;
        return new ResponseStopDto(
                stop.getId(),
                stop.getName(),
                stop.getCity(),
                stop.getStringTypes()
        );
    }

    public Iterable<ResponseStopDto> stopsToDTOs(Iterable<Stop> stops) {
        Collection<ResponseStopDto> dtos = new ArrayList<>();
        stops.forEach(stop -> dtos.add(stopToDTO(stop)));
        return dtos;
    }

    public void writeStatistics(Long totalNumberOfStops,
                                Long totalNumberOfBusStops,
                                Long totalNumberOfSubwayStops,
                                Long totalNumberOfSuburbanTrainStops,
                                Long totalNumberOfConnections,
                                PrintWriter writer) {
        writer.println(String.format("Total number of stops : %d", totalNumberOfStops));
        writer.println(String.format("Total number of bus stops : %d", totalNumberOfBusStops));
        writer.println(String.format("Total number of subway stops : %d", totalNumberOfSubwayStops));
        writer.println(String.format("Total number of suburban train stops : %d", totalNumberOfSuburbanTrainStops));
        writer.println(String.format("Total number of connections : %d", totalNumberOfConnections));

    }

}
