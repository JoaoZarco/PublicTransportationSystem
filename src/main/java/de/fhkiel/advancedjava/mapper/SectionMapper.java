package de.fhkiel.advancedjava.mapper;

import de.fhkiel.advancedjava.domain.trafficline.Section;
import de.fhkiel.advancedjava.dto.disturbance.ResponseDisturbanceDto;
import de.fhkiel.advancedjava.dto.section.RequestSectionDto;
import de.fhkiel.advancedjava.dto.section.ResponseSectionDto;
import de.fhkiel.advancedjava.service.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class SectionMapper {


    private final DisturbanceMapper disturbanceMapper;
    private final StopService stopService;

    @Autowired
    public SectionMapper(DisturbanceMapper disturbanceMapper, StopService stopService) {
        this.disturbanceMapper = disturbanceMapper;
        this.stopService = stopService;
    }

    public ResponseSectionDto sectionToDTO(Section section) {
        return new ResponseSectionDto(
                section.getStartStop().getId(),
                section.getEndStop().getId(),
                section.getDurationInMinutes(),
                section.isOutOfOrder(),
                section.getType().name()
        );
    }

    public RequestSectionDto sectionToRequestDTO(Section section) {
        return new RequestSectionDto(
                section.getStartStop().getId(),
                section.getEndStop().getId(),
                section.getDurationInMinutes()
        );
    }

    public ResponseSectionDto sectionDtoWithDisturbances(Section section) {
        Collection<ResponseDisturbanceDto> disturbanceDtos = new ArrayList<>();
        section.getDisturbances().forEach(disturbance -> disturbanceDtos.add(disturbanceMapper.disturbanceToDTO(disturbance)));
        return new ResponseSectionDto(
                section.getStartStop().getId(),
                section.getEndStop().getId(),
                section.getDurationInMinutes(),
                section.isOutOfOrder(),
                section.getType().name(),
                disturbanceDtos
        );
    }

    public Section DTOtoSection(RequestSectionDto dto, String stringType) {
        return stopService.getSection(dto.beginStopId, dto.endStopId, stringType, dto.durationInMinutes);
    }


    public Iterable<ResponseSectionDto> sectionsToSectionDTOs(Iterable<Section> sections) {
        Collection<ResponseSectionDto> dtos = new ArrayList<>();
        sections.forEach(section -> dtos.add(this.sectionToDTO(section)));
        return dtos;
    }
}
