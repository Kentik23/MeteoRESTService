package example.meteoService.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

public class SensorDTO {
    @Size(min = 3, max = 30, message = "Название сенсора должно быть в пределах от 3 до 30 символов")
    private String name;

    public SensorDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
