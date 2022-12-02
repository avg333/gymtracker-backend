package org.avillar.gymtracker.measure.infrastructure;

import org.avillar.gymtracker.measure.application.MeasureDto;
import org.avillar.gymtracker.measure.application.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/api")
public class MeasureController {

    private final MeasureService measureService;

    @Autowired
    public MeasureController(MeasureService measureService) {
        this.measureService = measureService;
    }

    @GetMapping("/users/{userId}/measures")
    public ResponseEntity<List<MeasureDto>> getAllMeasures(@PathVariable final Long userId) {
        try {
            return ResponseEntity.ok(this.measureService.getAllUserMeasures(userId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/measures/{measureId}")
    public ResponseEntity<MeasureDto> getMeasureById(@PathVariable final Long measureId) {
        try {
            return ResponseEntity.ok(this.measureService.getMeasure(measureId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/measures")
    public ResponseEntity<MeasureDto> addMeasure(@RequestBody final MeasureDto measureDto) {
        measureDto.setId(null);

        return ResponseEntity.ok(this.measureService.createMeasure(measureDto));
    }

    @PutMapping("/measures/{measureId}")
    public ResponseEntity<MeasureDto> updateMeasure(@PathVariable final Long measureId, @RequestBody final MeasureDto measureDto) {
        measureDto.setId(measureId);

        try {
            return ResponseEntity.ok(this.measureService.updateMeasure(measureDto));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/measures/{measureId}")
    public ResponseEntity<Void> deleteMeasure(@PathVariable final Long measureId) {
        try {
            this.measureService.deleteMeasure(measureId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}