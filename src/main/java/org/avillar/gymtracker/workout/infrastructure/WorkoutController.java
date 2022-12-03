package org.avillar.gymtracker.workout.infrastructure;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.base.infrastructure.BaseController;
import org.avillar.gymtracker.workout.application.WorkoutDto;
import org.avillar.gymtracker.workout.application.WorkoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WorkoutController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkoutController.class);

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
            LOGGER.info("Unauthorized access user={} workouts dates by user={}",
                    userId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error accessing user={} workouts dates by user={}",
                    userId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{userId}/workouts")
    public ResponseEntity<List<WorkoutDto>> getAllUserWorkouts(@PathVariable final Long userId) {
        try {
            return ResponseEntity.ok(this.workoutService.getAllUserWorkouts(userId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access user={} workouts by user={}",
                    userId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error accessing user={} workouts by user={}",
                    userId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/workouts/{workoutId}")
    public ResponseEntity<WorkoutDto> getWorkout(@PathVariable final Long workoutId) {
        try {
            return ResponseEntity.ok(this.workoutService.getWorkout(workoutId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access workout={} by user={}",
                    workoutId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error accessing workout={} by user={}",
                    workoutId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users/{userId}/workouts")
    public ResponseEntity<WorkoutDto> postWorkout(@PathVariable final Long userId, @RequestBody final WorkoutDto workoutDto) {
        workoutDto.setId(null);
        workoutDto.setUserId(userId);

        try {
            return ResponseEntity.ok(this.workoutService.createWorkout(workoutDto, userId));
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access creating workout for user={} by user={}",
                    userId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error creating workout for user={} by user={}",
                    userId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/workouts/{workoutId}")
    public ResponseEntity<WorkoutDto> putWorkout(@PathVariable final Long workoutId, @RequestBody final WorkoutDto workoutDto) {
        workoutDto.setId(workoutId);

        try {
            return ResponseEntity.ok(this.workoutService.updateWorkout(workoutDto));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access to update workout={} by user={}",
                    workoutId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error updating workout={} by user={}",
                    workoutId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
            LOGGER.info("Unauthorized access to remove workout={} by user={}",
                    workoutId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error removing workout={} by user={}",
                    workoutId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
