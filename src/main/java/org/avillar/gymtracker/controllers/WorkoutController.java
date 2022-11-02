package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.model.dto.WorkoutDto;
import org.avillar.gymtracker.services.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }


    @GetMapping("/users/{userId}/workouts")
    public ResponseEntity<List<WorkoutDto>> getAllUserWorkouts(@PathVariable final Long userId) throws IllegalAccessException {
        return ResponseEntity.ok(this.workoutService.getAllUserWorkouts(userId));
    }

    @GetMapping("/workouts/{workoutId}")
    public ResponseEntity<WorkoutDto> getWorkout(@PathVariable final Long workoutId) throws IllegalAccessException {
        return ResponseEntity.ok(this.workoutService.getWorkout(workoutId));
    }

    @PostMapping("/workouts")
    public ResponseEntity<WorkoutDto> postSession(@RequestBody final WorkoutDto workoutDto) {
        return ResponseEntity.ok(this.workoutService.createWorkout(workoutDto));
    }

    @PutMapping("/workouts/{workoutId}")
    public ResponseEntity<WorkoutDto> putWorkout(@PathVariable final Long workoutId, final WorkoutDto workoutDto) throws IllegalAccessException {
        if (!workoutId.equals(workoutDto.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.workoutService.updateWorkout(workoutDto));
    }

    @DeleteMapping("/workouts/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable final Long workoutId) throws IllegalAccessException {
        this.workoutService.deleteWorkout(workoutId);
        return ResponseEntity.ok().build();
    }
}
