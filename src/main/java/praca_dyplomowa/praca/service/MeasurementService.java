package praca_dyplomowa.praca.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import praca_dyplomowa.praca.controller.dto.MeasurementCreateDto;
import praca_dyplomowa.praca.controller.dto.StatisticDto;
import praca_dyplomowa.praca.entity.Measurement;
import praca_dyplomowa.praca.entity.User;
import praca_dyplomowa.praca.repository.MeasurementRepository;
import praca_dyplomowa.praca.repository.ParameterRepository;
import praca_dyplomowa.praca.repository.UserRepository;

import java.lang.reflect.Array;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
    @Autowired
    private final UserRepository userRepository;

    public Integer create(MeasurementCreateDto model, Integer userId){
        Measurement measurement = new Measurement();
        if (model.getDate() == null)
            model.setDate(LocalDateTime.now());
        measurement.setDate(model.getDate().toInstant(ZoneOffset.ofHours(0)));
        measurement.setParameter(parameterRepository.findByName(model.getParameter()).get());
        measurement.setValue(model.getValue());
        measurement.setUser(userRepository.findById(userId).get());
        Integer measurementId = measurementRepository.save(measurement).getId();
        User user = userRepository.findById(userId).get();
        user.getMeasurementList().add(measurement);
        return measurementId;
    }
    public List<Measurement> findAll(){
        return measurementRepository.findAll();
    }


    public List<Measurement> findAllByDateBetween(
            LocalDateTime startDate, LocalDateTime endDate, Integer userId){
        Instant startPeriod = startDate.atZone(ZoneId.of("Europe/Warsaw")).toInstant();
        Instant endPeriod = endDate.atZone(ZoneId.of("Europe/Warsaw")).toInstant();
        return measurementRepository.findAllByDateBetweenAndUser(
                startPeriod, endPeriod, userRepository.findById(userId).get());
    }

    public List<Measurement> findAllByDateBetweenAndParameterId(
            LocalDateTime startDate, LocalDateTime endDate, Integer parameterId, Integer userId){
        Instant startPeriod = startDate.atZone(ZoneId.of("Europe/Warsaw")).toInstant();
        Instant endPeriod = endDate.atZone(ZoneId.of("Europe/Warsaw")).toInstant();
        return measurementRepository.findAllByDateBetweenAndParameterIdAndUser(
                startPeriod, endPeriod, parameterId, userRepository.findById(userId).get());
    }

    public void delete(Integer id){
        measurementRepository.deleteById(id);
    }

    public StatisticDto statistics(
            LocalDateTime startDate, LocalDateTime endDate, String parameter, Integer userId){
        Instant startPeriod = startDate.atZone(ZoneId.of("Europe/Warsaw")).toInstant();
        Instant endPeriod = endDate.atZone(ZoneId.of("Europe/Warsaw")).toInstant();
        Integer parameterId = parameterRepository.findByName(parameter).get().getId();
        List<Measurement> measurements = measurementRepository.findAllByDateBetweenAndParameterIdAndUser(
                startPeriod, endPeriod, parameterId, userRepository.findById(userId).get());
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
            i++;
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
