package example.meteoService.dto;

import example.meteoService.models.Sensor;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;

public class MeasurementDTO {
    @Digits(integer = 3, fraction = 1, message = "Значение должно состоять максимум из 3 цифр в целой части и 1 цифры в дробной")
    private float value;

//    @Pattern(regexp = "^(true|false)$", message = "Должно принимать значение true или false")
    private boolean raining;

    private Sensor sensor;

    public MeasurementDTO() {
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
