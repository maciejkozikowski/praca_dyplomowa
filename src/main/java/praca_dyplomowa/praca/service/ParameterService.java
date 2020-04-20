package praca_dyplomowa.praca.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import praca_dyplomowa.praca.controller.dto.ParameterListDto;
import praca_dyplomowa.praca.entity.Parameter;
import praca_dyplomowa.praca.entity.User;
import praca_dyplomowa.praca.repository.MeasurementRepository;
import praca_dyplomowa.praca.repository.ParameterRepository;
import praca_dyplomowa.praca.repository.UserRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Lazy
public class ParameterService {

    @Autowired
    private final MeasurementRepository measurementRepository;
    @Autowired
    private final ParameterRepository parameterRepository;
    @Autowired
    private final UserRepository userRepository;

    public Integer create(Parameter model, Integer userId) throws Exception {
        if (!userRepository.findById(userId).get().getIsAdmin())
            throw new Exception("You're not an admin!");
        if (model.getDateAdded() == null)
            model.setDateAdded(Instant.now());
        if (parameterRepository.findByName(model.getName()).isPresent())
            throw new Exception("Parameter with this name exists");
        return parameterRepository.save(model).getId();
    }

    public List<Parameter> findAll(){
        return parameterRepository.findAll();
    }

    public Optional<Parameter> findByName(String name){
        return parameterRepository.findByName(name);
    }

    public void delete(Integer id, Integer userId) throws Exception {
        if (!userRepository.findById(userId).get().getIsAdmin())
            throw new Exception("You're not an admin!");
        measurementRepository.deleteAll(measurementRepository.findAllByParameterId(id));
        parameterRepository.deleteById(id);
    }

    public List<ParameterListDto> getNames(){
        List<ParameterListDto> result = new ArrayList<>();
        List<Parameter> parametersList = parameterRepository.findAll();
        for (Parameter param : parametersList) {
            ParameterListDto temp = new ParameterListDto();
            temp.setId(param.getId());
            temp.setName(param.getName());
            result.add(temp);
        }
        return result;
    }
}
