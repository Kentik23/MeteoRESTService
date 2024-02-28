package example.meteoService.util;

import example.meteoService.dto.SensorDTO;
import example.meteoService.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Transactional(readOnly = true)
public class SensorDTOValidator implements Validator {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorDTOValidator(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SensorDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) target;

        if (sensorRepository.findByName(sensorDTO.getName()).isPresent()) {
            errors.rejectValue("name", "",
                    "Сенсор с таким именем уже существует");
        }
    }
}
