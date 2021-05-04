package de.fhkiel.advancedjava.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fhkiel.advancedjava.dto.schedule.RequestScheduleDto;
import de.fhkiel.advancedjava.dto.stop.RequestStopDto;
import de.fhkiel.advancedjava.dto.trafficline.RequestTrafficLineDto;
import de.fhkiel.advancedjava.mapper.ScheduleMapper;
import de.fhkiel.advancedjava.persistence.GeneralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class ScheduleService {

    private final GeneralRepository generalRepository;
    private final StopService stopService;
    private final TrafficLineService trafficLineService;
    private final ScheduleMapper scheduleMapper;

    @Value("${backup.path}")
    private String backupPath;

    private static final String BACKUP_FILENAME = "ScheduleBackup";

    @Autowired
    public ScheduleService(GeneralRepository generalRepository,
                           StopService stopService,
                           TrafficLineService trafficLineService,
                           ScheduleMapper scheduleMapper) {
        this.generalRepository = generalRepository;
        this.stopService = stopService;
        this.trafficLineService = trafficLineService;
        this.scheduleMapper = scheduleMapper;
    }

    public void cleanDatabase() {
        generalRepository.cleanDatabase();
    }


    /**
     * Writes a backup of the current schedule into a JSON file
     * named "ScheduleBackup" followed by the LocalDateTime String value
     * into the path defined in the application.properties file
     * at 12:00pm everyday
     */
    @Scheduled(cron = "0 0 12 * * ?")
    @Transactional
    public void backupSchedule() {
        writeBackup(BACKUP_FILENAME.concat(LocalDateTime.now().toString()));
    }

    private void writeBackup(String fileName) {
        //Get Objects that make a Schedule and turn them to DTOs
        Iterable<RequestStopDto> requestStopDtos = scheduleMapper.stopsToRequestDTO(stopService.getAllStops());
        Iterable<RequestTrafficLineDto> trafficLineDtos = scheduleMapper.trafficLinesToRequestDTO(trafficLineService.getAllTrafficLinesOrdered());
        //Create the schedule Dto
        RequestScheduleDto scheduleDto = scheduleMapper.createScheduleDto(requestStopDtos, trafficLineDtos);
        try {
            // create object mapper instance
            ObjectMapper objectMapper = new ObjectMapper();
            //Create Path for backup
            objectMapper.writeValue(
                    Paths.get(backupPath, fileName.concat(".json")).toFile(),
                    scheduleDto);
        } catch (Exception e) {
            //do nothing ?
        }

    }


}