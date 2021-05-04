package de.fhkiel.advancedjava.mapper;

import de.fhkiel.advancedjava.domain.disturbance.Disturbance;
import de.fhkiel.advancedjava.domain.disturbance.queryresults.DisturbancesSection;
import de.fhkiel.advancedjava.domain.disturbance.queryresults.DisturbancesStop;
import de.fhkiel.advancedjava.dto.disturbance.RequestDisturbanceDto;
import de.fhkiel.advancedjava.dto.disturbance.ResponseDisturbanceDto;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

@Component
public class DisturbanceMapper {

    public DisturbanceMapper() {

    }

    public ResponseDisturbanceDto disturbanceToDTO(Disturbance disturbance) {
        return new ResponseDisturbanceDto(
                disturbance.getId(),
                disturbance.getDescription(),
                disturbance.getStartTime().toString(),
                disturbance.isResolved() ? disturbance.getEndTime().toString() : null
        );
    }

    public Disturbance DTOToDisturbance(RequestDisturbanceDto dto) {
        return new Disturbance(dto.description);
    }

    public void writeStatistics(Long totalNumberOfDisturbances,
                                Iterable<DisturbancesSection> totalNumberOfDisturbancesPerSection,
                                Iterable<DisturbancesStop> totalNumberOfDisturbancesPerStop,
                                PrintWriter writer) {
        writer.println(String.format("Total number of disturbances : %d", totalNumberOfDisturbances));
        writer.println("Total Number Of disturbances per section :");
        totalNumberOfDisturbancesPerSection.forEach(disturbancesSection ->
                writer.println(String.format("%s -[%s]-> %s : %d",
                        disturbancesSection.startStop,
                        disturbancesSection.type,
                        disturbancesSection.endStop,
                        disturbancesSection.amount)
                )
        );
        writer.println("Total number of disturbances per stop :");
        totalNumberOfDisturbancesPerStop.forEach(disturbancesStop ->
                writer.println(String.format("%s : %d",
                        disturbancesStop.stop,
                        disturbancesStop.amount))
        );
    }
}
