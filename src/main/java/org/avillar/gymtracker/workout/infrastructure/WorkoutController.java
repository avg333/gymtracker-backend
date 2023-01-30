package org.avillar.gymtracker.workout.infrastructure;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.base.infrastructure.BaseController;
import org.avillar.gymtracker.user.application.UserAppDto;
import org.avillar.gymtracker.workout.application.WorkoutService;
import org.avillar.gymtracker.workout.application.dto.WorkoutDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class WorkoutController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkoutController.class);

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping("/users/{userId}/workouts/dates")
    public ResponseEntity<Map<Date, Long>> getAllWorkoutDatesByUser(@PathVariable final Long userId) {
        try {
            return ResponseEntity.ok(this.workoutService.getAllUserWorkoutDates(userId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access user={} workout dates by user={}",
                    userId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error accessing user={} workout dates by user={}",
                    userId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{userId}/exercises/{exerciseId}/workouts/dates")
    public ResponseEntity<Map<Date, Long>> getAllUserWorkoutDatesWithExercise(@PathVariable final Long userId, @PathVariable final Long exerciseId) {
        // TODO Mejorar URL
        try {
            return ResponseEntity.ok(this.workoutService.getAllUserWorkoutsWithExercise(userId, exerciseId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access user={} workout dates with exercise={} by user={}",
                    userId, exerciseId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error accessing user={} workout dates with exercise={} by user={}",
                    userId, exerciseId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{userId}/workouts/date/{date}")
    public ResponseEntity<List<WorkoutDto>> getWorkoutsByUserAndDate(@PathVariable final Long userId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") final Date date) {
        try {
            return ResponseEntity.ok(this.workoutService.getAllUserWorkoutsByDate(userId, date));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access user={} workouts filtered by date={} by user={}",
                    userId, date, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error accessing user={} workouts filtered by date={} by user={}",
                    userId, date, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/workouts/{workoutId}")
    public ResponseEntity<WorkoutDto> getWorkoutById(@PathVariable final Long workoutId) {
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
    public ResponseEntity<WorkoutDto> postWorkoutInUser(@PathVariable final Long userId, @RequestBody final WorkoutDto workoutDto) {
        workoutDto.setId(null);
        final UserAppDto userAppDto = new UserAppDto();
        userAppDto.setId(userId);
        workoutDto.setUserApp(userAppDto);

        //TODO Contemplar validate
        try {
            return ResponseEntity.ok(this.workoutService.createWorkout(workoutDto));
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

    @PostMapping("/workouts/{workoutDestinationId}/addSetGroupsFrom/workouts/{workoutSourceId}")
    public ResponseEntity<WorkoutDto> copySetGroupsFromWorkoutToWorkout(@PathVariable final Long workoutDestinationId, @PathVariable final Long workoutSourceId) {
        try {
            return ResponseEntity.ok(this.workoutService.addSetGroupsToWorkoutFromWorkout(workoutDestinationId, workoutSourceId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access to copy SetGroups from workout={} to workout={} by user={}",
                    workoutSourceId, workoutDestinationId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error copying SetGroups from workout={} to workout={} by user={}",
                    workoutSourceId, workoutDestinationId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/workouts/{workoutDestinationId}/addSetGroupsFrom/sessions/{sessionSourceId}")
    public ResponseEntity<WorkoutDto> copySetGroupsFromSessionToWorkout(@PathVariable final Long workoutDestinationId, @PathVariable final Long sessionSourceId) {
        try {
            return ResponseEntity.ok(this.workoutService.addSetGroupsToWorkoutFromSession(workoutDestinationId, sessionSourceId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access to copy SetGroups from session={} to workout={} by user={}",
                    sessionSourceId, workoutDestinationId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error copying SetGroups from session={} to workout={} by user={}",
                    sessionSourceId, workoutDestinationId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/workouts/{workoutId}")
    public ResponseEntity<WorkoutDto> putWorkout(@PathVariable final Long workoutId, @RequestBody final WorkoutDto workoutDto) {
        workoutDto.setId(workoutId);

        //TODO Contemplar validate
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
