package org.avillar.gymtracker.setgroup.infrastructure;

import org.avillar.gymtracker.errors.application.exceptions.BadFormException;
import org.avillar.gymtracker.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.errors.application.exceptions.IllegalAccessException;
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

    /**
     * No se usa DE MOMENTO
     */
    @GetMapping("/sessions/{sessionId}/setGroups")
    public ResponseEntity<List<SetGroupDto>> getAllSessionSetGroups(@PathVariable final Long sessionId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.setGroupService.getAllSessionSetGroups(sessionId));
    }

    /**
     * No se usa
     */
    @GetMapping("/workouts/{workoutId}/setGroups")
    public ResponseEntity<List<SetGroupDto>> getAllWorkoutSetGroups(@PathVariable final Long workoutId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.setGroupService.getAllWorkoutSetGroups(workoutId));
    }

    /**
     * Obtiene todos los setGroups del usuario especificado y el ejercicio especificado.
     * Estos SetGroups deben tener el Exercise (y sus subcategorías) y las Sets.
     * Se usa en ExercisePage para ver el histórico.
     */
    @GetMapping("/users/{userId}/exercises/{exerciseId}/setGroups")
    public ResponseEntity<List<SetGroupDto>> getAllUserAndExerciseSetGroups(@PathVariable final Long userId, @PathVariable final Long exerciseId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.setGroupService.getAllUserAndExerciseSetGroups(userId, exerciseId));
    }

    /**
     * Obtiene el SetGroupDto del SetGroup con el ID especificado.
     * Se usa en ChangeFromWorkoutModal y solo necesita el ID del Exercise
     */
    @GetMapping("/setGroups/{setGroupId}")
    public ResponseEntity<SetGroupDto> getSetGroup(@PathVariable final Long setGroupId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.setGroupService.getSetGroup(setGroupId));
    }

    /**
     * Crea un nuevo SetGroup en la Session especificada. No usa el retorno.
     */
    @PostMapping("/sessions/{sessionId}/setGroups")
    public ResponseEntity<SetGroupDto> postSetGroupInSession(@PathVariable final Long sessionId, @RequestBody final SetGroupDto setGroupDto)
            throws EntityNotFoundException, BadFormException {
        setGroupDto.setId(null);
        setGroupDto.setSession(new SessionDto(sessionId));

        return ResponseEntity.ok(this.setGroupService.createSetGroupInSession(setGroupDto));
    }

    /**
     * Crea un nuevo SetGroup en el Workout especificada. No usa el retorno.
     */
    @PostMapping("/workouts/{workoutId}/setGroups")
    public ResponseEntity<SetGroupDto> postSetGroupInWorkout(@PathVariable final Long workoutId, @RequestBody final SetGroupDto setGroupDto)
            throws EntityNotFoundException, BadFormException {
        setGroupDto.setId(null);
        setGroupDto.setWorkout(new WorkoutDto(workoutId));

        return ResponseEntity.ok(this.setGroupService.createSetGroupInWorkout(setGroupDto));
    }

    /**
     * Sustituye las Sets del SetGroup especificado por las Sets del otro SetGroup especificado.
     * Retorna el SetGroup modificado.
     * Se usa en ChangeFromWorkoutModal y no usa el retorno.
     */
    @PostMapping("/setGroups/{setGroupDestinationId}/replaceWith/setGroups/{setGroupSourceId}")
    public ResponseEntity<SetGroupDto> replaceSetGroupSetsWithSetGroup(@PathVariable final Long setGroupDestinationId, @PathVariable final Long setGroupSourceId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.setGroupService.replaceSetGroupSetsFromSetGroup(setGroupDestinationId, setGroupSourceId));
    }

    /**
     * Sustituye el SetGroup con el ID especicado por otro con los datos del SetGroupID.
     * No se sustituye el padre original (Workout / Session) ni el ID del SetGroup.
     * Solo se usa para la sustitución del Exercise (exerciseId) del SetGroup especificado desde ExercisesPage y no usa el retorno.
     */
    @PutMapping("/setGroups/{setGroupId}")
    public ResponseEntity<SetGroupDto> putSetGroup(@PathVariable final Long setGroupId, @RequestBody final SetGroupDto setGroupDto)
            throws EntityNotFoundException, BadFormException {
        setGroupDto.setId(setGroupId);

        return ResponseEntity.ok(this.setGroupService.updateSetGroup(setGroupDto));
    }

    /**
     * Elimina el SetGroup con el ID especificado.
     * Se usa en SetGroupCard para eliminar el SetGroup especificado.
     */
    @DeleteMapping("/setGroups/{setGroupId}")
    public ResponseEntity<Void> deleteSetGroup(@PathVariable final Long setGroupId)
            throws EntityNotFoundException, IllegalAccessException {
        this.setGroupService.deleteSetGroup(setGroupId);
        return ResponseEntity.ok().build();
    }

}
