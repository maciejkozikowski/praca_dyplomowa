package praca_dyplomowa.praca.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import praca_dyplomowa.praca.controller.dto.MeasurementCreateDto;
import praca_dyplomowa.praca.controller.dto.StatisticDto;
import praca_dyplomowa.praca.entity.Measurement;
import praca_dyplomowa.praca.service.MeasurementService;
import praca_dyplomowa.praca.service.ParameterService;

import javax.validation.Valid;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final ParameterService parameterService;

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody MeasurementCreateDto measurement){
        Integer createdMeasurementId = measurementService.create(measurement);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdMeasurementId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Measurement>> findAll(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String parameter){
        List<Measurement> measurements = new ArrayList<>();
        LocalDateTime start = LocalDateTime.now().minusYears(1000);
        LocalDateTime end = LocalDateTime.now().plusYears(1000);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(!startDate.equals("null"))
            start = LocalDate.parse(startDate, formatter).atStartOfDay();
        if(!endDate.equals("null"))
            end = LocalDate.parse(endDate, formatter).plusDays(1).atStartOfDay();

        if(parameterService.findByName(parameter).isPresent())
            measurements = measurementService.findAllByDateBetweenAndParameterId(start, end, parameterService.findByName(parameter).get().getId());
        else
            measurements = measurementService.findAllByDateBetween(start, end);
        return ResponseEntity.ok(measurements);
    }

    @GetMapping("/date")
    public ResponseEntity<List<Measurement>> findAllByDateBetween(
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate){
        List<Measurement> measurements = measurementService.findAllByDateBetween(startDate, endDate);
        return ResponseEntity.ok(measurements);
    }

    @DeleteMapping
    public void delete(@Valid @RequestParam Integer id){
        measurementService.delete(id);
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticDto> statistics(@RequestParam String startDate,
                                                   @RequestParam String endDate,
                                                   @RequestParam String parameter){
        LocalDateTime start = LocalDateTime.now().minusYears(1000);
        LocalDateTime end = LocalDateTime.now().plusYears(1000);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(!startDate.equals("null"))
            start = LocalDate.parse(startDate, formatter).atStartOfDay();
        if(!endDate.equals("null"))
            end = LocalDate.parse(endDate, formatter).plusDays(1).atStartOfDay();
        StatisticDto result = measurementService.statistics(start, end, parameter);
        return ResponseEntity.ok(result);
    }
}
