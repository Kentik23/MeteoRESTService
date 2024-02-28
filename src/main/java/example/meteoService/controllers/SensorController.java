package example.meteoService.controllers;

import example.meteoService.dto.SensorDTO;
import example.meteoService.models.Sensor;
import example.meteoService.services.SensorService;
import example.meteoService.util.SensorErrorResponse;
import example.meteoService.util.SensorNotCreatedException;
import example.meteoService.util.SensorDTOValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sensors")
public class SensorController {
    private final ModelMapper modelMapper;
    private final SensorService sensorService;
    private final SensorDTOValidator sensorDTOValidator;

    @Autowired
    public SensorController(ModelMapper modelMapper, SensorService sensorService, SensorDTOValidator sensorDTOValidator) {
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
        this.sensorDTOValidator = sensorDTOValidator;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {

        sensorDTOValidator.validate(sensorDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage()).append("; ");
            }

            throw new SensorNotCreatedException(errorMsg.toString());
        }

        sensorService.register(convertToSensor(sensorDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
