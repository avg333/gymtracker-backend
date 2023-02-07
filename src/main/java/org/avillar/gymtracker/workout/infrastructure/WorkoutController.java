package org.avillar.gymtracker.workout.infrastructure;

import org.avillar.gymtracker.errors.application.exceptions.BadFormException;
import org.avillar.gymtracker.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.user.application.UserAppDto;
import org.avillar.gymtracker.workout.application.WorkoutService;
import org.avillar.gymtracker.workout.application.dto.WorkoutDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping("/users/{userId}/workouts/dates")
    public ResponseEntity<Map<Date, Long>> getAllWorkoutDatesByUser(@PathVariable final Long userId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.workoutService.getAllUserWorkoutDates(userId));
    }

    @GetMapping("/users/{userId}/exercises/{exerciseId}/workouts/dates")
    public ResponseEntity<Map<Date, Long>> getAllUserWorkoutDatesWithExercise(@PathVariable final Long userId, @PathVariable final Long exerciseId)
            throws EntityNotFoundException, IllegalAccessException {
        //TODO Mejorar URL
        return ResponseEntity.ok(this.workoutService.getAllUserWorkoutsWithExercise(userId, exerciseId));
    }

    @GetMapping("/users/{userId}/workouts/date/{date}")
    public ResponseEntity<List<WorkoutDto>> getWorkoutsByUserAndDate(@PathVariable final Long userId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") final Date date)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.workoutService.getAllUserWorkoutsByDate(userId, date));
    }

    @GetMapping("/workouts/{workoutId}")
    public ResponseEntity<WorkoutDto> getWorkoutById(@PathVariable final Long workoutId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.workoutService.getWorkout(workoutId));
    }

    @PostMapping("/users/{userId}/workouts")
    public ResponseEntity<WorkoutDto> postWorkoutInUser(@PathVariable final Long userId, @RequestBody final WorkoutDto workoutDto)
            throws EntityNotFoundException, IllegalAccessException, BadFormException {
        workoutDto.setId(null);
        workoutDto.setUserApp(new UserAppDto(userId));

        return ResponseEntity.ok(this.workoutService.createWorkout(workoutDto));
    }

    @PostMapping("/workouts/{workoutDestinationId}/addSetGroupsFrom/workouts/{workoutSourceId}")
    public ResponseEntity<WorkoutDto> copySetGroupsFromWorkoutToWorkout(@PathVariable final Long workoutDestinationId, @PathVariable final Long workoutSourceId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.workoutService.addSetGroupsToWorkoutFromWorkout(workoutDestinationId, workoutSourceId));
    }

    @PostMapping("/workouts/{workoutDestinationId}/addSetGroupsFrom/sessions/{sessionSourceId}")
    public ResponseEntity<WorkoutDto> copySetGroupsFromSessionToWorkout(@PathVariable final Long workoutDestinationId, @PathVariable final Long sessionSourceId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.workoutService.addSetGroupsToWorkoutFromSession(workoutDestinationId, sessionSourceId));
    }

    @PutMapping("/workouts/{workoutId}")
    public ResponseEntity<WorkoutDto> putWorkout(@PathVariable final Long workoutId, @RequestBody final WorkoutDto workoutDto)
            throws EntityNotFoundException, IllegalAccessException, BadFormException {
        workoutDto.setId(workoutId);

        return ResponseEntity.ok(this.workoutService.updateWorkout(workoutDto));
    }

    @DeleteMapping("/workouts/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable final Long workoutId)
            throws EntityNotFoundException, IllegalAccessException {
        this.workoutService.deleteWorkout(workoutId);
        return ResponseEntity.ok().build();
    }

}
