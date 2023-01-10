package org.avillar.gymtracker.exercise.infrastructure;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.base.application.IncorrectFormException;
import org.avillar.gymtracker.base.infrastructure.BaseController;
import org.avillar.gymtracker.enums.domain.LoadTypeEnum;
import org.avillar.gymtracker.exercise.application.ExerciseService;
import org.avillar.gymtracker.exercise.application.dto.ExerciseDto;
import org.avillar.gymtracker.exercise.application.dto.ExerciseFilterDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExerciseController.class);

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("")
    public ResponseEntity<List<ExerciseDto>> getAllExercises(
            @RequestParam(required = false) final String name,
            @RequestParam(required = false) final String description,
            @RequestParam(required = false) final Boolean unilateral,
            @RequestParam(required = false) final LoadTypeEnum loadType,
            @RequestParam(required = false) final List<Long> muscleSupGroupIds,
            @RequestParam(required = false) final Long muscleGroupIds,
            @RequestParam(required = false) final List<Long> muscleSubGroupIds
    ) {
        try {
            final ExerciseFilterDto exerciseFilterDto = new ExerciseFilterDto(name, description, unilateral, loadType,
                    muscleSupGroupIds, muscleGroupIds != null ? List.of(muscleGroupIds) : null, muscleSubGroupIds);
            return ResponseEntity.ok(this.exerciseService.getAllExercises(exerciseFilterDto));
        } catch (Exception exception) {
            LOGGER.error("Error getting exercises by user={}",
                    this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{exerciseId}")
    public ResponseEntity<ExerciseDto> getExerciseById(@PathVariable final Long exerciseId) {
        try {
            return ResponseEntity.ok(this.exerciseService.getExercise(exerciseId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access exercise={} by user={}",
                    exerciseId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error accessing exercise={} by user={}",
                    exerciseId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> postExercise(@RequestBody final ExerciseDto exerciseDto) {
        final Map<String, String> errorMap = new HashMap<>();
        exerciseDto.setId(null);
        try {
            return ResponseEntity.ok(this.exerciseService.createExercise(exerciseDto, errorMap));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IncorrectFormException exception) {
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            LOGGER.error("Error creating exercise by user={}",
                    this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{exerciseId}")
    public ResponseEntity<?> putExercise(@PathVariable final Long exerciseId, @RequestBody final ExerciseDto exerciseDto) {
        final Map<String, String> errorMap = new HashMap<>();
        exerciseDto.setId(exerciseId);
        try {
            return ResponseEntity.ok(this.exerciseService.updateExercise(exerciseDto, errorMap));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IncorrectFormException exception) {
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized update exercise={} by user={}",
                    exerciseId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error updating exercise={} by user={}",
                    exerciseId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<Void> deleteExercise(@PathVariable final Long exerciseId) {
        try {
            this.exerciseService.deleteExercise(exerciseId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized deleting exercise={} by user={}",
                    exerciseId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error deleting exercise={} by user={}",
                    exerciseId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}