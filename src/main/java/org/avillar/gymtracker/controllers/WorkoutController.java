package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.model.dto.WorkoutDto;
import org.avillar.gymtracker.services.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping("/{workoutId}")
    public ResponseEntity<WorkoutDto> getWorkout(@PathVariable final Long workoutId) throws IllegalAccessException {
        return ResponseEntity.ok(this.workoutService.getWorkout(workoutId));
    }

    @PostMapping("")
    public ResponseEntity<WorkoutDto> postSession(@RequestBody final WorkoutDto workoutDto) {
        return ResponseEntity.ok(this.workoutService.createWorkout(workoutDto));
    }

    @PutMapping("/{workoutId}")
    public ResponseEntity<WorkoutDto> putWorkout(@PathVariable final Long workoutId, final WorkoutDto workoutDto) throws IllegalAccessException {
        if (!workoutId.equals(workoutDto.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.workoutService.updateWorkout(workoutDto));
    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable final Long workoutId) throws IllegalAccessException {
        this.workoutService.deleteWorkout(workoutId);
        return ResponseEntity.ok().build();
    }
}
