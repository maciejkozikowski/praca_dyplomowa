package praca_dyplomowa.praca.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import praca_dyplomowa.praca.controller.dto.MeasurementCreateDto;
import praca_dyplomowa.praca.controller.dto.StatisticDto;
import praca_dyplomowa.praca.entity.Measurement;
import praca_dyplomowa.praca.repository.MeasurementRepository;
import praca_dyplomowa.praca.repository.ParameterRepository;

import java.lang.reflect.Array;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
@Lazy
public class MeasurementService {


    @Autowired
    private final MeasurementRepository measurementRepository;
    @Autowired
    private final ParameterRepository parameterRepository;

    public Integer create(MeasurementCreateDto model){
        Measurement measurement = new Measurement();
        if (model.getDate() == null)
            measurement.setDate(Instant.now());
        measurement.setDate(model.getDate().atZone(ZoneId.of("Europe/Warsaw")).toInstant());
        measurement.setParameter(parameterRepository.findByName(model.getParameter()).get());
        measurement.setValue(model.getValue());
        return measurementRepository.save(measurement).getId();
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

    public List<Measurement> findAllByDateBetweenAndParameterId(LocalDateTime startDate, LocalDateTime endDate, Integer parameterId){
        Instant startPeriod = startDate.atZone(ZoneId.of("Europe/Warsaw")).toInstant();
        Instant endPeriod = endDate.atZone(ZoneId.of("Europe/Warsaw")).toInstant();
        return measurementRepository.findAllByDateBetweenAndParameterId(startPeriod, endPeriod, parameterId);
    }

    public void delete(Integer id){
        measurementRepository.deleteById(id);
    }

    public StatisticDto statistics(LocalDateTime startDate, LocalDateTime endDate, String parameter){
        Instant startPeriod = startDate.atZone(ZoneId.of("Europe/Warsaw")).toInstant();
        Instant endPeriod = endDate.atZone(ZoneId.of("Europe/Warsaw")).toInstant();
        Integer parameterId = parameterRepository.findByName(parameter).get().getId();
        List<Measurement> measurements = measurementRepository.findAllByDateBetweenAndParameterId(startPeriod, endPeriod, parameterId);
        StatisticDto result = new StatisticDto();
        Float sum = 0F;
        Float min = measurements.get(0).getValue();
        Float max = measurements.get(0).getValue();
        for (Measurement m : measurements) {
            sum+=m.getValue();
            if(m.getValue() < min)
                min = m.getValue();
            if(m.getValue() > max)
                max = m.getValue();
        }
        Float[] values = new Float[measurements.size()];
        Integer i = 0;
        for (Measurement m : measurements) {
            values[i] = m.getValue();
        }
        Arrays.sort(values);
        Float median;
        if (values.length % 2 == 0)
            median = ((Float)values[values.length/2] + (Float)values[values.length/2 - 1])/2;
        else
            median = (Float) values[values.length/2];
        result.setCount(measurements.size());
        if(result.getCount() != 0) {
            result.setSum(sum);
            result.setAverage(result.getSum() / result.getCount());
            result.setMaximum(max);
            result.setMinimum(min);
            result.setMedian(median);
        }
        return result;

    }
}
