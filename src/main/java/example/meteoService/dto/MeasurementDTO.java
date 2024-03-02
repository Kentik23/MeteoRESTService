package example.meteoService.dto;

import jakarta.validation.constraints.Digits;

public class MeasurementDTO {
    @Digits(integer = 3, fraction = 1, message = "Значение должно состоять максимум из 3 цифр в целой части и 1 цифры в дробной")
    private float value;
    
    private boolean raining;

    private SensorDTO sensor;

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

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensorDTO) {
        this.sensor = sensorDTO;
    }
}
