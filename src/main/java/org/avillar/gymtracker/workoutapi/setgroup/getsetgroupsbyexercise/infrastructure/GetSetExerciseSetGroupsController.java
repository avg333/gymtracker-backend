package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.GetExerciseSetGroupsService;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.mapper.GetExerciseSetGroupsControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.model.GetExerciseSetGroupsResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO Aclarar respuesta
@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class GetSetExerciseSetGroupsController {

  private final GetExerciseSetGroupsService getExerciseSetGroupsService;
  private final GetExerciseSetGroupsControllerMapper getExerciseSetGroupsControllerMapper;

  /**
   * Obtiene todos los setGroups del usuario especificado y el ejercicio especificado. Estos
   * SetGroups deben tener el Exercise (y sus subcategorías) y las Sets. Se usa en ExercisePage para
   * ver el histórico.
   */
  @GetMapping("/users/{userId}/exercises/{exerciseId}/setGroups")
  public ResponseEntity<GetExerciseSetGroupsResponseInfrastructure> get(
      @PathVariable final UUID userId, @PathVariable final UUID exerciseId) {
    return ResponseEntity.ok(
        getExerciseSetGroupsControllerMapper.map(
            getExerciseSetGroupsService.execute(userId, exerciseId)));
  }
}
