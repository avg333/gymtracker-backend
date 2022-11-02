package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.model.dto.MeasureDto;
import org.avillar.gymtracker.services.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<MeasureDto>> getAllMeasures(@PathVariable final Long userId) throws IllegalAccessException {
        return ResponseEntity.ok(this.measureService.getAllUserMeasures(userId));
    }

    @GetMapping("/measures/{measureId}")
    public ResponseEntity<MeasureDto> getMeasureById(@PathVariable final Long measureId) throws IllegalAccessException {
        return ResponseEntity.ok(this.measureService.getMeasure(measureId));
    }

    @PostMapping("/measures")
    public ResponseEntity<MeasureDto> addMeasure(@RequestBody final MeasureDto measureDto) {
        if (null != measureDto.getId())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(this.measureService.createMeasure(measureDto));
    }

    @PutMapping("/measures/{measureId}")
    public ResponseEntity<MeasureDto> updateMeasure(@PathVariable final Long measureId, @RequestBody final MeasureDto measureDto) throws IllegalAccessException {
        if (!measureId.equals(measureDto.getId()))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(this.measureService.updateMeasure(measureDto));
    }

    @DeleteMapping("/measures/{measureId}")
    public ResponseEntity<Void> deleteMeasure(@PathVariable final Long measureId) throws IllegalAccessException {
        this.measureService.deleteMeasure(measureId);
        return ResponseEntity.ok().build();
    }
}
