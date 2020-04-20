package praca_dyplomowa.praca.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import praca_dyplomowa.praca.controller.dto.UserCreateDto;
import praca_dyplomowa.praca.entity.User;
import praca_dyplomowa.praca.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Integer> login(
            @RequestParam String name,
            @RequestParam String password){
        return ResponseEntity.ok(userService.login(name, password));
    }

    @PostMapping
    public ResponseEntity<User> create(
            @Valid @RequestBody UserCreateDto model){
        return ResponseEntity.ok(userService.create(model));
    }

    @DeleteMapping
    public void delete(
            @Valid @RequestParam Integer id){
        userService.delete(id);
    }

    @PostMapping("/createadmin")
    public ResponseEntity<User> createAdmin(){
        UserCreateDto user = new UserCreateDto();
        user.setName("AdminKozikom");
        user.setPassword("kaszanka");
        user.setIsAdmin(true);
        return ResponseEntity.ok(userService.create(user));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
