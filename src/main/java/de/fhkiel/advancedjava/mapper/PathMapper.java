package de.fhkiel.advancedjava.mapper;

import de.fhkiel.advancedjava.domain.stop.queryresults.NodePath;
import de.fhkiel.advancedjava.dto.path.InvalidStopNameDto;
import de.fhkiel.advancedjava.dto.path.PathDto;
import de.fhkiel.advancedjava.dto.path.PathStopDto;
import de.fhkiel.advancedjava.dto.path.ResponsePathDto;
import de.fhkiel.advancedjava.dto.stop.ResponseStopDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class PathMapper {

    private final StopMapper stopMapper;

    @Autowired
    public PathMapper(StopMapper stopMapper) {
        this.stopMapper = stopMapper;
    }

    public PathDto pathToDTO(Iterable<NodePath> path) {
        Collection<PathStopDto> pathStopDtos = new ArrayList<>();
        for (NodePath node : path) {
            ResponseStopDto responseStopDto = stopMapper.stopToDTOWithNoDetail(node.stop);
            pathStopDtos.add(new PathStopDto(responseStopDto, node.type, node.cost.intValue()));
        }
        return new ResponsePathDto(pathStopDtos);
    }

    public PathDto PathToDTO(String wrongParameter, String value, String hint) {
        return new InvalidStopNameDto(
                wrongParameter,
                value,
                hint
        );
    }
}
