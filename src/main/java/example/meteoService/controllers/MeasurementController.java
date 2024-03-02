package example.meteoService.controllers;

import example.meteoService.dto.MeasurementDTO;
import example.meteoService.dto.SensorDTO;
import example.meteoService.models.Measurement;
import example.meteoService.models.Sensor;
import example.meteoService.services.MeasurementService;
import example.meteoService.util.MeasurementDTOValidator;
import example.meteoService.util.MeasurementErrorResponse;
import example.meteoService.util.MeasurementNotCreatedException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("measurements")
public class MeasurementController {
    private final ModelMapper modelMapper;
    private final MeasurementService measurementService;
    private final MeasurementDTOValidator measurementDTOValidator;

    @Autowired
    public MeasurementController(ModelMapper modelMapper, MeasurementService measurementService, MeasurementDTOValidator measurementDTOValidator) {
        this.modelMapper = modelMapper;
        this.measurementService = measurementService;
        this.measurementDTOValidator = measurementDTOValidator;
    }

    @GetMapping
    public List<MeasurementDTO> getAllMeasurements() {
        return measurementService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public int getRainyDaysCount() {
        return measurementService.getRainyDaysCount();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {

        measurementDTOValidator.validate(measurementDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage()).append("; ");
            }

            throw new MeasurementNotCreatedException(errorMsg.toString());
        }

        measurementService.add(convertToMeasurement(measurementDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
        measurement.setSensor(modelMapper.map(measurementDTO.getSensor(), Sensor.class));
        return measurement;
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        MeasurementDTO measurementDTO = modelMapper.map(measurement, MeasurementDTO.class);
        measurementDTO.setSensor(modelMapper.map(measurement.getSensor(), SensorDTO.class));
        return measurementDTO;
    }
}
