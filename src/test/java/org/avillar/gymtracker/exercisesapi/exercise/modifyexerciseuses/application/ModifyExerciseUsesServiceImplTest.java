// package org.avillar.gymtracker.exercisesapi.exercise.modifyexerciseuses.application;
//
// import static org.assertj.core.api.Assertions.assertThat;
// import static org.assertj.core.api.Assertions.assertThatThrownBy;
// import static org.mockito.Mockito.doNothing;
// import static org.mockito.Mockito.doThrow;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;
//
// import java.util.List;
// import java.util.Optional;
// import java.util.UUID;
// import org.avillar.gymtracker.common.errors.application.AuthOperations;
// import org.avillar.gymtracker.exercisesapi.common.auth.application.AuthExercisesService;
// import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
// import org.avillar.gymtracker.exercisesapi.common.domain.ExerciseUses;
// import
// org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
// import
// org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseNotFoundException;
// import org.avillar.gymtracker.exercisesapi.common.facade.exercise.ExerciseFacade;
// import org.avillar.gymtracker.exercisesapi.common.facade.exerciseuses.ExerciseUsesFacade;
// import org.instancio.Instancio;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.junit.jupiter.api.parallel.Execution;
// import org.junit.jupiter.api.parallel.ExecutionMode;
// import org.mockito.ArgumentCaptor;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
//
// @Execution(ExecutionMode.CONCURRENT)
// @ExtendWith(MockitoExtension.class)
// class ModifyExerciseUsesServiceImplTest {
//  private static final AuthOperations AUTH_OPERATIONS = AuthOperations.READ;
//
//  @InjectMocks private ModifyExerciseUsesServiceImpl modifyExerciseUsesService;
//
//  @Mock private ExerciseFacade exerciseFacade;
//  @Mock private ExerciseUsesFacade exerciseUsesFacade;
//  @Mock private AuthExercisesService authExercisesService;
//
//  private static void checkSavedExerciseUses(
//      final ExerciseUses euRequest,
//      final List<ExerciseUses> exerciseUsesInDb,
//      final List<ExerciseUses> exerciseUsesSaved,
//      final UUID userId) {
//    final Optional<ExerciseUses> exerciseUsesInDbOptional =
//        exerciseUsesInDb.stream()
//            .filter(eu -> eu.getExercise().getId().equals(euRequest.getExercise().getId()))
//            .findAny();
//
//    final Optional<ExerciseUses> exerciseUsesSavedOptional =
//        exerciseUsesSaved.stream()
//            .filter(
//                euSaved -> euSaved.getExercise().getId().equals(euRequest.getExercise().getId()))
//            .findAny();
//    assertThat(exerciseUsesSavedOptional).isPresent();
//    assertThat(exerciseUsesSavedOptional.get().getUserId()).isEqualTo(userId);
//    assertThat(exerciseUsesSavedOptional.get().getExercise().getId())
//        .isEqualTo(euRequest.getExercise().getId());
//    if (exerciseUsesInDbOptional.isPresent()) {
//      final ExerciseUses exerciseUsesExistent = exerciseUsesInDbOptional.get();
//      assertThat(exerciseUsesSavedOptional.get().getId()).isEqualTo(exerciseUsesExistent.getId());
//      assertThat(exerciseUsesSavedOptional.get().getUses())
//          .isEqualTo(euRequest.getUses() + exerciseUsesExistent.getUses());
//    } else {
//      assertThat(exerciseUsesSavedOptional.get().getId()).isNull();
//      assertThat(exerciseUsesSavedOptional.get().getUses()).isEqualTo(euRequest.getUses());
//    }
//  }
//
//  private static List<Exercise> getExercises(final List<ExerciseUses> request) {
//    final List<Exercise> exercises =
// Instancio.ofList(Exercise.class).size(request.size()).create();
//    for (int i = 0; i < request.size(); i++) {
//      exercises.get(i).setId(request.get(i).getExercise().getId());
//    }
//    return exercises;
//  }
//
//  private static List<ExerciseUses> getExerciseUsesInDb(final List<ExerciseUses> request) {
//    final List<ExerciseUses> exerciseUses =
//        Instancio.ofList(ExerciseUses.class).size(request.size()).create();
//    for (int i = 0; i < request.size(); i++) {
//      exerciseUses.get(i).getExercise().setId(request.get(i).getExercise().getId());
//    }
//    return exerciseUses;
//  }
//
//  @Test
//  void shouldModifyExerciseUsesAndReturnTheySuccessfully()
//      throws ExerciseIllegalAccessException, ExerciseNotFoundException {
//    final UUID userId = UUID.randomUUID();
//    final List<ExerciseUses> request = Instancio.createList(ExerciseUses.class);
//    final List<UUID> exerciseIdsInRequest =
//        request.stream().map(ExerciseUses::getExercise).map(Exercise::getId).toList();
//    final List<Exercise> exercises = getExercises(request);
//    final List<ExerciseUses> exerciseUsesInDb = getExerciseUsesInDb(request);
//    exerciseUsesInDb.remove(0);
//    final List<ExerciseUses> exerciseUsesAfterSave = Instancio.createList(ExerciseUses.class);
//
//    final ArgumentCaptor<List<ExerciseUses>> exerciseUsesCaptor =
//        ArgumentCaptor.forClass(List.class);
//
//    when(exerciseFacade.getExercisesByIds(exerciseIdsInRequest)).thenReturn(exercises);
//    doNothing().when(authExercisesService).checkAccess(exercises, AUTH_OPERATIONS);
//    when(exerciseUsesFacade.getExerciseUsesByExerciseIdAndUserId(exerciseIdsInRequest, userId))
//        .thenReturn(exerciseUsesInDb);
//    // SAVE exerciseUsesRequest
//    when(exerciseUsesFacade.saveAllExerciseUses(exerciseUsesCaptor.capture()))
//        .thenReturn(exerciseUsesAfterSave);
//
//    assertThat(modifyExerciseUsesService.execute(userId,
// request)).isEqualTo(exerciseUsesAfterSave);
//
//    final List<ExerciseUses> exerciseUsesSaved = exerciseUsesCaptor.getValue();
//    assertThat(exerciseUsesSaved).isNotNull().hasSize(request.size());
//    request.forEach(eu -> checkSavedExerciseUses(eu, exerciseUsesInDb, exerciseUsesSaved,
// userId));
//
//    verify(exerciseUsesFacade).saveAllExerciseUses(exerciseUsesSaved);
//  }
//
//  @Test
//  void shouldThrowExerciseIllegalAccessExceptionWhenUserIsNotAuthorized()
//      throws ExerciseIllegalAccessException {
//    final UUID userId = UUID.randomUUID();
//    final List<ExerciseUses> request = Instancio.createList(ExerciseUses.class);
//    final List<Exercise> exercises = getExercises(request);
//    final ExerciseIllegalAccessException exception =
//        new ExerciseIllegalAccessException(
//            exercises.get(0).getId(), AUTH_OPERATIONS, UUID.randomUUID());
//
//    when(exerciseFacade.getExercisesByIds(
//            request.stream().map(ExerciseUses::getExercise).map(Exercise::getId).toList()))
//        .thenReturn(exercises);
//    doThrow(exception).when(authExercisesService).checkAccess(exercises, AUTH_OPERATIONS);
//
//    assertThatThrownBy(() -> modifyExerciseUsesService.execute(userId, request))
//        .isEqualTo(exception);
//  }
//
//  @Test
//  void shouldThrowExerciseNotFoundExceptionWhenExerciseIsNotFound() {
//    final UUID userId = UUID.randomUUID();
//    final List<ExerciseUses> request = Instancio.createList(ExerciseUses.class);
//    final List<Exercise> exercises = getExercises(request);
//    final ExerciseNotFoundException exception =
//        new ExerciseNotFoundException(exercises.get(0).getId());
//    exercises.remove(0);
//
//    when(exerciseFacade.getExercisesByIds(
//            request.stream().map(ExerciseUses::getExercise).map(Exercise::getId).toList()))
//        .thenReturn(exercises);
//
//    assertThatThrownBy(() -> modifyExerciseUsesService.execute(userId, request))
//        .isEqualTo(exception);
//  }
// }
