package de.fhkiel.advancedjava.mapper;

import de.fhkiel.advancedjava.domain.stop.Stop;
import de.fhkiel.advancedjava.domain.trafficline.TrafficLine;
import de.fhkiel.advancedjava.dto.schedule.RequestScheduleDto;
import de.fhkiel.advancedjava.dto.schedule.ResponseScheduleDto;
import de.fhkiel.advancedjava.dto.stop.RequestStopDto;
import de.fhkiel.advancedjava.dto.stop.ResponseStopDto;
import de.fhkiel.advancedjava.dto.trafficline.RequestTrafficLineDto;
import de.fhkiel.advancedjava.dto.trafficline.ResponseTrafficLineDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;


@Component
public class ScheduleMapper {

    private StopMapper stopMapper;
    private TrafficLineMapper trafficLineMapper;

    @Autowired
    public ScheduleMapper(StopMapper stopMapper, TrafficLineMapper trafficLineMapper) {
        this.stopMapper = stopMapper;
        this.trafficLineMapper = trafficLineMapper;
    }

    public ResponseScheduleDto scheduleToDto(Iterable<Stop> stops, Iterable<TrafficLine> trafficLines) {
        Collection<ResponseStopDto> stopDtos = new ArrayList<>();
        Collection<ResponseTrafficLineDto> requestTrafficLineDtos = new ArrayList<>();

        stops.forEach(stop -> stopDtos.add(stopMapper.stopToDTO(stop)));
        trafficLines.forEach(trafficLine -> requestTrafficLineDtos.add(trafficLineMapper.trafficLineToDTO(trafficLine)));
        return new ResponseScheduleDto(
                stopDtos, requestTrafficLineDtos
        );
    }

    public RequestScheduleDto createScheduleDto(Iterable<RequestStopDto> stops, Iterable<RequestTrafficLineDto> trafficLines) {
        return new RequestScheduleDto(stops, trafficLines);
    }

    public Iterable<Stop> stopDTOsToStops(Iterable<RequestStopDto> dtos) {
        Collection<Stop> stops = new ArrayList<>();
        dtos.forEach(dto -> stops.add(stopMapper.DTOToStopWithState(dto)));
        return stops;
    }

    public Iterable<TrafficLine> trafficLineDTOsToTrafficLines(Iterable<RequestTrafficLineDto> dtos) {
        Collection<TrafficLine> trafficLines = new ArrayList<>();
        dtos.forEach(dto -> trafficLines.add(trafficLineMapper.DTOToTrafficLine(dto)));
        return trafficLines;
    }

    public Iterable<RequestStopDto> stopsToRequestDTO(Iterable<Stop> stops) {
        Collection<RequestStopDto> requestStopDtos = new ArrayList<>();
        stops.forEach(stop -> requestStopDtos.add(stopMapper.stopToRequestDto(stop)));
        return requestStopDtos;
    }

    public Iterable<RequestTrafficLineDto> trafficLinesToRequestDTO(Iterable<TrafficLine> trafficLines) {
        Collection<RequestTrafficLineDto> requestTrafficLineDtos = new ArrayList<>();
        trafficLines.forEach(trafficLine -> requestTrafficLineDtos.add(trafficLineMapper.trafficLineToRequestDTO(trafficLine)));
        return requestTrafficLineDtos;
    }


}
