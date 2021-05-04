package de.fhkiel.advancedjava.mapper;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import de.fhkiel.advancedjava.domain.vehicle.Vehicle;
import de.fhkiel.advancedjava.dto.stop.ResponseStopDto;
import de.fhkiel.advancedjava.dto.vehicle.RequestVehicleDto;
import de.fhkiel.advancedjava.dto.vehicle.ResponseVehicleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Component
public class VehicleMapper {

    private StopMapper stopMapper;

    @Autowired
    public VehicleMapper(StopMapper stopMapper) {
        this.stopMapper = stopMapper;
    }

    public Vehicle DTOToVehicle(RequestVehicleDto dto) {
        return new Vehicle(dto.description);
    }

    public ResponseVehicleDto vehicleToDTO(Vehicle vehicle) {
        ResponseStopDto responseStopDto = stopMapper.stopToDTOWithNoDetail(vehicle.getCurrentStop());
        return new ResponseVehicleDto(
                vehicle.getId(),
                vehicle.getDescription(),
                vehicle.getAssigned(),
                responseStopDto
        );
    }

    public List<ResponseVehicleDto> vehiclesToDTOs(Iterable<Vehicle> vehicles) {
        List<ResponseVehicleDto> responseVehicleDtos = new ArrayList<>();
        vehicles.forEach(vehicle -> responseVehicleDtos.add(this.vehicleToDTO(vehicle)));
        return responseVehicleDtos;
    }

    public void writeCsvToResponse(PrintWriter writer, List<ResponseVehicleDto> vehicles) {
        try {
            ColumnPositionMappingStrategy<ResponseVehicleDto> mapStrategy
                    = new ColumnPositionMappingStrategy<>();

            mapStrategy.setType(ResponseVehicleDto.class);

            String[] columns = new String[]{"Vehicle Id", "description", "assigned", "Stop Id", "name", "city"};
            mapStrategy.setColumnMapping(columns);

            StatefulBeanToCsv<ResponseVehicleDto> btcsv = new StatefulBeanToCsvBuilder<ResponseVehicleDto>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(mapStrategy)
                    .withSeparator(',')
                    .build();

            btcsv.write(vehicles);
        } catch (CsvException e) {

        }
    }

}
