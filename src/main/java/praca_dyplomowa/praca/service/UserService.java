package praca_dyplomowa.praca.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import praca_dyplomowa.praca.controller.dto.UserCreateDto;
import praca_dyplomowa.praca.entity.Measurement;
import praca_dyplomowa.praca.entity.User;
import praca_dyplomowa.praca.repository.MeasurementRepository;
import praca_dyplomowa.praca.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
@Lazy
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final MeasurementRepository measurementRepository;

    public Integer login(String name, String password){
        return userRepository.findUserByNameAndPassword(name, password).get().getId();
    }

    public User create(UserCreateDto model){
        User user = new User();
        user.setName(model.getName());
        user.setPassword(model.getPassword());
        user.setIsAdmin(model.getIsAdmin());
        return userRepository.save(user);
    }

    public void delete(Integer userId){
        List<Measurement> measurements = userRepository.findById(userId).get().getMeasurementList();
        measurementRepository.deleteAll(measurements);
        userRepository.deleteById(userId);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}
