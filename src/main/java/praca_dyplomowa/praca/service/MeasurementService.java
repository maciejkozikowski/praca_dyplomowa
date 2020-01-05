package praca_dyplomowa.praca.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import praca_dyplomowa.praca.entity.Measurement;
import praca_dyplomowa.praca.repository.MeasurementRepository;
import praca_dyplomowa.praca.repository.ParameterRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RequiredArgsConstructor
@Service
@Lazy
public class MeasurementService {


    @Autowired
    private final MeasurementRepository measurementRepository;
    @Autowired
    private final ParameterRepository parameterRepository;

    public Integer create(Measurement model){
        if (model.getDate() == null)
            model.setDate(Instant.now());
        Measurement createdMeasurement = measurementRepository.save(model);
        return createdMeasurement.getId();
    }
    public List<Measurement> findAll(){
        return measurementRepository.findAll();
    }

    public List<Measurement> findAllByParameterId(Integer parameterId){
        return measurementRepository.findAllByParameterId(parameterId);
    }

    public List<Measurement> findAllByDateBetween(LocalDateTime startDate, LocalDateTime endDate){
        Instant startPeriod = startDate.atZone(ZoneId.of("Europe/Warsaw")).toInstant();
        Instant endPeriod = endDate.atZone(ZoneId.of("Europe/Warsaw")).toInstant();
        return measurementRepository.findAllByDateBetween(startPeriod, endPeriod);
    }

    public List<Measurement> findAllByDateBetweenAndParameterId(Instant startDate, Instant endDate, Integer parameterId){
        return measurementRepository.findAllByDateBetweenAndParameterId(startDate, endDate, parameterId);
    }

}
