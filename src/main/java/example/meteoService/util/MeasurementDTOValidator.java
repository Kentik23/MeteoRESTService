package example.meteoService.util;

import example.meteoService.dto.MeasurementDTO;
import example.meteoService.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Transactional(readOnly = true)
public class MeasurementDTOValidator implements Validator {
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementDTOValidator(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(MeasurementDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;
        if (sensorRepository.findByName(measurementDTO.getSensor().getName()).isEmpty()) {
            errors.rejectValue("sensor", "", "Сенсора с таким названием не существует");
        }
    }
}
