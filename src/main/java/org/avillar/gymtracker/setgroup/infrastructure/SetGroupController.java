package org.avillar.gymtracker.setgroup.infrastructure;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.session.application.dto.SessionDto;
import org.avillar.gymtracker.setgroup.application.SetGroupService;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupDto;
import org.avillar.gymtracker.workout.application.dto.WorkoutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SetGroupController {

    private final SetGroupService setGroupService;

    @Autowired
    public SetGroupController(SetGroupService setGroupService) {
        this.setGroupService = setGroupService;
    }

    @GetMapping("/sessions/{sessionId}/setGroups")
    public ResponseEntity<List<SetGroupDto>> getAllSessionSetGroups(@PathVariable final Long sessionId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.setGroupService.getAllSessionSetGroups(sessionId));
    }

    @GetMapping("/workouts/{workoutId}/setGroups")
    public ResponseEntity<List<SetGroupDto>> getAllWorkoutSetGroups(@PathVariable final Long workoutId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.setGroupService.getAllWorkoutSetGroups(workoutId));
    }

    //TODO Completar esto
    @GetMapping("/users/{userId}/exercises/{exerciseId}/last")
    public ResponseEntity<SetGroupDto> getLastUserExerciseSetGroup(@PathVariable final Long userId, @PathVariable final Long exerciseId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.setGroupService.getLastTimeUserExerciseSetGroup(userId, exerciseId));
    }

    @GetMapping("/setGroups/{setGroupId}")
    public ResponseEntity<SetGroupDto> getSetGroup(@PathVariable final Long setGroupId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.setGroupService.getSetGroup(setGroupId));
    }

    @PostMapping("/sessions/{sessionId}/setGroups")
    public ResponseEntity<SetGroupDto> postSetGroupInSession(@PathVariable final Long sessionId, @RequestBody final SetGroupDto setGroupDto)
            throws EntityNotFoundException, IllegalAccessException {
        setGroupDto.setId(null);
        final SessionDto sessionDto = new SessionDto();
        sessionDto.setId(sessionId);
        setGroupDto.setSession(sessionDto);

        return ResponseEntity.ok(this.setGroupService.createSetGroupInSession(setGroupDto));
        //TODO Validate
    }

    @PostMapping("/workouts/{workoutId}/setGroups")
    public ResponseEntity<SetGroupDto> postSetGroupInWorkout(@PathVariable final Long workoutId, @RequestBody final SetGroupDto setGroupDto)
            throws EntityNotFoundException, IllegalAccessException {
        setGroupDto.setId(null);
        final WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setId(workoutId);
        setGroupDto.setWorkout(workoutDto);

        return ResponseEntity.ok(this.setGroupService.createSetGroupInWorkout(setGroupDto));
        //TODO Validate
    }

    @PostMapping("/setGroups/{setGroupDestinationId}/replaceWith/setGroups/{setGroupSourceId}")
    public ResponseEntity<SetGroupDto> replaceSetGroupSetsWithSetGroup(@PathVariable final Long setGroupDestinationId,
                                                                       @PathVariable final Long setGroupSourceId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.setGroupService.replaceSetGroupSetsFromSetGroup(setGroupDestinationId, setGroupSourceId));
    }

    @PutMapping("/setGroups/{setGroupId}")
    public ResponseEntity<SetGroupDto> putSetGroup(@PathVariable final Long setGroupId, @RequestBody final SetGroupDto setGroupDto)
            throws EntityNotFoundException, IllegalAccessException {
        setGroupDto.setId(setGroupId);
        return ResponseEntity.ok(this.setGroupService.updateSetGroup(setGroupDto));
        //TODO Validate
    }

    @DeleteMapping("/setGroups/{setGroupId}")
    public ResponseEntity<Void> deleteSetGroup(@PathVariable final Long setGroupId)
            throws EntityNotFoundException, IllegalAccessException {
        this.setGroupService.deleteSetGroup(setGroupId);
        return ResponseEntity.ok().build();
    }

}