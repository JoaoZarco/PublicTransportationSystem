package de.fhkiel.advancedjava.mapper;

import de.fhkiel.advancedjava.domain.trafficline.Section;
import de.fhkiel.advancedjava.domain.trafficline.TrafficLine;
import de.fhkiel.advancedjava.dto.trafficline.RequestTrafficLineDto;
import de.fhkiel.advancedjava.dto.section.RequestSectionDto;
import de.fhkiel.advancedjava.dto.section.ResponseSectionDto;
import de.fhkiel.advancedjava.dto.trafficline.ResponseTrafficLineDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class TrafficLineMapper {


    private final SectionMapper sectionMapper;

    @Autowired
    public TrafficLineMapper(SectionMapper sectionMapper) {
        this.sectionMapper = sectionMapper;
    }

    //Traffic Line should be completly build here
    public TrafficLine DTOToTrafficLine(RequestTrafficLineDto dto) {
        List<Section> sections = new ArrayList<>();
        for (RequestSectionDto requestSectionDto : dto.sections) {
            sections.add(sectionMapper.DTOtoSection(requestSectionDto, dto.type));
        }
        return new TrafficLine(
                dto.lineId,
                dto.name,
                dto.type,
                sections);
    }


    public ResponseTrafficLineDto trafficLineToDTO(TrafficLine trafficLine) {
        Collection<ResponseSectionDto> responseSectionDtos = new ArrayList<>();
        for (Section section : trafficLine.getSections()) {
            responseSectionDtos.add(sectionMapper.sectionToDTO(section));
        }
        return new ResponseTrafficLineDto(
                trafficLine.getId(),
                trafficLine.getName(),
                trafficLine.getType().name(),
                responseSectionDtos
        );
    }

    public RequestTrafficLineDto trafficLineToRequestDTO(TrafficLine trafficLine) {
        Collection<RequestSectionDto> sections = new ArrayList<>();
        trafficLine.getSections().forEach(section -> sections.add(sectionMapper.sectionToRequestDTO(section)));
        return new RequestTrafficLineDto(
                trafficLine.getId(),
                trafficLine.getName(),
                trafficLine.getType().name(),
                sections
        );
    }

    public Iterable<ResponseTrafficLineDto> trafficLinesToDTOs(Iterable<TrafficLine> trafficLines) {
        Collection<ResponseTrafficLineDto> dtos = new ArrayList<>();
        trafficLines.forEach(trafficLine -> dtos.add(trafficLineToDTO(trafficLine)));
        return dtos;
    }


}
