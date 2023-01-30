package org.avillar.gymtracker.setgroup.infrastructure;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.base.infrastructure.BaseController;
import org.avillar.gymtracker.session.application.dto.SessionDto;
import org.avillar.gymtracker.setgroup.application.SetGroupService;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupDto;
import org.avillar.gymtracker.workout.application.dto.WorkoutDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SetGroupController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SetGroupController.class);

    private final SetGroupService setGroupService;

    @Autowired
    public SetGroupController(SetGroupService setGroupService) {
        this.setGroupService = setGroupService;
    }

    @GetMapping("/sessions/{sessionId}/setGroups")
    public ResponseEntity<List<SetGroupDto>> getAllSessionSetGroups(@PathVariable final Long sessionId) {
        try {
            return ResponseEntity.ok(this.setGroupService.getAllSessionSetGroups(sessionId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access session={} setGroups by user={}",
                    sessionId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error accessing session={} setGroups by user={}",
                    sessionId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/workouts/{workoutId}/setGroups")
    public ResponseEntity<List<SetGroupDto>> getAllWorkoutSetGroups(@PathVariable final Long workoutId) {
        try {
            return ResponseEntity.ok(this.setGroupService.getAllWorkoutSetGroups(workoutId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access workout={} setGroups by user={}",
                    workoutId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error accessing workout={} setGroups by user={}",
                    workoutId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //TODO Completar esto
    @GetMapping("/users/{userId}/exercises/{exerciseId}/last")
    public ResponseEntity<SetGroupDto> getLastUserExerciseSetGroup(@PathVariable final Long userId, @PathVariable final Long exerciseId) {
        try {
            return ResponseEntity.ok(this.setGroupService.getLastTimeUserExerciseSetGroup(userId, exerciseId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access to get last user={} setGroup with exercise={} by user={}",
                    userId, exerciseId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error getting last user={} setGroup with exercise={} by user={}",
                    userId, exerciseId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/setGroups/{setGroupId}")
    public ResponseEntity<SetGroupDto> getSetGroup(@PathVariable final Long setGroupId) {
        try {
            return ResponseEntity.ok(this.setGroupService.getSetGroup(setGroupId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access setGroup={} by user={}",
                    setGroupId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error accessing setGroup={} by user={}",
                    setGroupId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/sessions/{sessionId}/setGroups")
    public ResponseEntity<SetGroupDto> postSetGroupInSession(@PathVariable final Long sessionId, @RequestBody final SetGroupDto setGroupDto) {
        setGroupDto.setId(null);
        final SessionDto sessionDto = new SessionDto();
        sessionDto.setId(sessionId);
        setGroupDto.setSession(sessionDto);

        //TODO Validate
        try {
            return ResponseEntity.ok(this.setGroupService.createSetGroupInSession(setGroupDto));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access to create setGroup for session={} by user={}",
                    sessionId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error creating setGroup for session={} by user={}",
                    sessionId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/workouts/{workoutId}/setGroups")
    public ResponseEntity<SetGroupDto> postSetGroupInWorkout(@PathVariable final Long workoutId, @RequestBody final SetGroupDto setGroupDto) {
        setGroupDto.setId(null);
        final WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setId(workoutId);
        setGroupDto.setWorkout(workoutDto);

        //TODO Validate
        try {
            return ResponseEntity.ok(this.setGroupService.createSetGroupInWorkout(setGroupDto));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access to create setGroup for workout={} by user={}",
                    workoutId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error creating setGroup for workout={} by user={}",
                    workoutId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/setGroups/{setGroupDestinationId}/replaceWith/setGroups/{setGroupSourceId}")
    public ResponseEntity<SetGroupDto> replaceSetGroupSetsWithSetGroup(@PathVariable final Long setGroupDestinationId,
                                                                       @PathVariable final Long setGroupSourceId) {
        try {
            return ResponseEntity.ok(this.setGroupService.replaceSetGroupSetsFromSetGroup(setGroupDestinationId, setGroupSourceId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access to replace setGroup={} sets with setGroup={} sets by user={}",
                    setGroupDestinationId, setGroupSourceId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error replacing setGroup={} sets with setGroup={} sets by user={}",
                    setGroupDestinationId, setGroupSourceId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/setGroups/{setGroupId}")
    public ResponseEntity<SetGroupDto> putSetGroup(@PathVariable final Long setGroupId, @RequestBody final SetGroupDto setGroupDto) {
        setGroupDto.setId(setGroupId);

        //TODO Validate
        try {
            return ResponseEntity.ok(this.setGroupService.updateSetGroup(setGroupDto));
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access to update setGroup={} by user={}",
                    setGroupId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error updating setGroup={} by user={}",
                    setGroupId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/setGroups/{setGroupId}")
    public ResponseEntity<Void> deleteSetGroup(@PathVariable final Long setGroupId) {
        try {
            this.setGroupService.deleteSetGroup(setGroupId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access to remove setGroup={} by user={}",
                    setGroupId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error removing setGroup={} by user={}",
                    setGroupId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}