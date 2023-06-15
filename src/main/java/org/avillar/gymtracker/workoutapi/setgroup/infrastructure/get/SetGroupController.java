package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.get;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.GetSetGroupService;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.get.mapper.GetSetGroupControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.get.model.GetSetGroupResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class SetGroupController {

  private final GetSetGroupService getSetGroupService;
  private final GetSetGroupControllerMapper getSetGroupControllerMapper;

  /**
   * Obtiene el SetGroupDto del SetGroup con el ID especificado. Se usa en ChangeFromWorkoutModal y
   * solo necesita el ID del Exercise
   */
  @GetMapping("/setGroups/{setGroupId}")
  public ResponseEntity<GetSetGroupResponse> getSetGroup(
      @PathVariable final UUID setGroupId, @RequestParam(required = false) final boolean full) {
    return ResponseEntity.ok(
        getSetGroupControllerMapper.getResponse(getSetGroupService.getSetGroup(setGroupId, full)));
  }

  /**
   * Obtiene todos los setGroups del usuario especificado y el ejercicio especificado. Estos
   * SetGroups deben tener el Exercise (y sus subcategorías) y las Sets. Se usa en ExercisePage para
   * ver el histórico.
   */
  @GetMapping("/users/{userId}/exercises/{exerciseId}/setGroups")
  public ResponseEntity<List<GetSetGroupResponse>> getAllUserAndExerciseSetGroups(
      @PathVariable final UUID userId, @PathVariable final UUID exerciseId) {
    return ResponseEntity.ok(
        getSetGroupControllerMapper.getResponse( // FIXME Cambiar respuesta
            getSetGroupService.getAllUserAndExerciseSetGroups(userId, exerciseId)));
  }
}