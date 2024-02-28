package example.meteoService.services;

import example.meteoService.models.Measurement;
import example.meteoService.repositories.MeasurementRepository;
import example.meteoService.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    @Transactional
    public void add(Measurement measurement) {
        measurement.setMeasurementDate(new Date());
        measurement.setSensor(sensorRepository.findByName(measurement.getSensor().getName()).get());
        measurementRepository.save(measurement);
    }
}
