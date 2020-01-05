package praca_dyplomowa.praca.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import praca_dyplomowa.praca.entity.Parameter;
import praca_dyplomowa.praca.repository.MeasurementRepository;
import praca_dyplomowa.praca.repository.ParameterRepository;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
@Lazy
public class ParameterService {

    @Autowired
    private final MeasurementRepository measurementRepository;
    @Autowired
    private final ParameterRepository parameterRepository;

    public Integer create(Parameter model){
        if (model.getDateAdded() == null)
            model.setDateAdded(Instant.now());
        return parameterRepository.save(model).getId();
    }

    public List<Parameter> findAll(){
        return parameterRepository.findAll();
    }

    public List<Parameter> findByName(String name){
        return parameterRepository.findByName(name);
    }
}
