package praca_dyplomowa.praca.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import praca_dyplomowa.praca.controller.dto.ParameterListDto;
import praca_dyplomowa.praca.entity.Parameter;
import praca_dyplomowa.praca.service.ParameterService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parameters")
public class ParameterController {
    private final ParameterService parameterService;

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Parameter parameter, @RequestParam Integer userId) throws Exception {
        Integer createdParameterId = parameterService.create(parameter, userId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdParameterId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<Parameter>> findAll(){
        List<Parameter> parameters = parameterService.findAll();
        return ResponseEntity.ok(parameters);
    }

    @DeleteMapping
    public void delete(@Valid @RequestParam Integer id, @RequestParam Integer userId) throws Exception {
        parameterService.delete(id, userId);
    }

    @GetMapping("/names")
    public ResponseEntity<List<ParameterListDto>> getNames(){
        return ResponseEntity.ok(parameterService.getNames());
    }
}
