package praca_dyplomowa.praca.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import praca_dyplomowa.praca.entity.Measurement;
import praca_dyplomowa.praca.service.MeasurementService;

import javax.validation.Valid;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Measurement measurement){
        Integer createdMeasurementId = measurementService.create(measurement);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdMeasurementId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<Measurement>> findAll(){
        List<Measurement> measurements = measurementService.findAll();
        return ResponseEntity.ok(measurements);
    }

    @GetMapping("/byparameterid")
    public ResponseEntity<List<Measurement>> findAllByParameterId(@RequestParam Integer parameterId){
        List<Measurement> measurements = measurementService.findAllByParameterId(parameterId);
        return ResponseEntity.ok(measurements);
    }

    @GetMapping("/date")
    public ResponseEntity<List<Measurement>> findAllByDateBetween(
            @RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime endDate){
        List<Measurement> measurements = measurementService.findAllByDateBetween(startDate, endDate);
        return ResponseEntity.ok(measurements);
    }

    @GetMapping("/dateparam")
    public ResponseEntity<List<Measurement>> findAllByDateBetweenAndParameterId(@RequestParam Instant startDate,
                                                                                @RequestParam Instant endDate,
                                                                                @RequestParam Integer parameterId){
        List<Measurement> measurements = measurementService.findAllByDateBetweenAndParameterId(startDate, endDate, parameterId);
        return ResponseEntity.ok(measurements);
    }
}
