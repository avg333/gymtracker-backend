package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.model.dto.WorkoutDto;
import org.avillar.gymtracker.model.dto.WorkoutSummaryDto;
import org.avillar.gymtracker.services.WorkoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping("/users/{userId}/workouts/dates")
    public ResponseEntity<List<Date>> getAllUserWorkoutsDates(@PathVariable final Long userId) {
        try {
            return ResponseEntity.ok(this.workoutService.getAllUserWorkoutsDates(userId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/users/{userId}/workouts")
    public ResponseEntity<List<WorkoutDto>> getAllUserWorkouts(@PathVariable final Long userId) {
        try {
            return ResponseEntity.ok(this.workoutService.getAllUserWorkouts(userId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/workouts/{workoutId}/summary")
    public ResponseEntity<WorkoutSummaryDto> getWorkoutSummary(@PathVariable final Long workoutId) {
        try {
            return ResponseEntity.ok(this.workoutService.getWorkoutSummary(workoutId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/workouts/{workoutId}")
    public ResponseEntity<WorkoutDto> getWorkout(@PathVariable final Long workoutId) {
        try {
            return ResponseEntity.ok(this.workoutService.getWorkout(workoutId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/users/{userId}/workouts")
    public ResponseEntity<WorkoutDto> postWorkout(@PathVariable final Long userId, @RequestBody final WorkoutDto workoutDto) {
        workoutDto.setId(null);
        workoutDto.setUserId(userId);

        return ResponseEntity.ok(this.workoutService.createWorkout(workoutDto));
    }

    @PutMapping("/workouts/{workoutId}")
    public ResponseEntity<WorkoutDto> putWorkout(@PathVariable final Long workoutId, @RequestBody final WorkoutDto workoutDto) {
        workoutDto.setId(workoutId);

        try {
            return ResponseEntity.ok(this.workoutService.updateWorkout(workoutDto));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/workouts/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable final Long workoutId) {
        try {
            this.workoutService.deleteWorkout(workoutId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
