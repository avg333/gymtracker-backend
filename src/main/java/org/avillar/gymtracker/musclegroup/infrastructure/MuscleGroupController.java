package org.avillar.gymtracker.musclegroup.infrastructure;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.base.infrastructure.BaseController;
import org.avillar.gymtracker.musclegroup.application.MuscleGroupService;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleGroupDto;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleSubGroupDto;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleSupGroupDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class MuscleGroupController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MuscleGroupController.class);

    private final MuscleGroupService muscleGroupService;

    @Autowired
    public MuscleGroupController(MuscleGroupService muscleGroupService) {
        this.muscleGroupService = muscleGroupService;
    }

    @GetMapping("muscleSupGroups")
    public ResponseEntity<List<MuscleSupGroupDto>> getAllMuscleSupGroups() {
        try {
            return ResponseEntity.ok(this.muscleGroupService.getAllMuscleSupGroups());
        } catch (Exception exception) {
            LOGGER.error("Error accessing all MuscleSupGroups by user={}",
                    this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("muscleSupGroups/{muscleSupGroupId}")
    public ResponseEntity<MuscleSupGroupDto> getMuscleSupGroup(@PathVariable final Long muscleSupGroupId) {
        try {
            return ResponseEntity.ok(this.muscleGroupService.getMuscleSupGroup(muscleSupGroupId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            LOGGER.error("Error accessing MuscleSupGroup={} by user={}",
                    muscleSupGroupId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("muscleSupGroups/{muscleSupGroupId}/muscleGroups")
    public ResponseEntity<List<MuscleGroupDto>> getAllMuscleSupGroupMuscleGroups(@PathVariable final Long muscleSupGroupId) {
        try {
            return ResponseEntity.ok(this.muscleGroupService.getAllMuscleSupGroupMuscleGroups(muscleSupGroupId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            LOGGER.error("Error accessing MuscleSupGroup={} MuscleSupGroups by user={}",
                    muscleSupGroupId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("muscleGroups")
    public ResponseEntity<List<MuscleGroupDto>> getAllMuscleGroups() {
        try {
            return ResponseEntity.ok(this.muscleGroupService.getAllMuscleGroups());
        } catch (Exception exception) {
            LOGGER.error("Error accessing all MuscleGroups by user={}",
                    this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("muscleGroups/{muscleGroupId}")
    public ResponseEntity<MuscleGroupDto> getMuscleGroup(@PathVariable final Long muscleGroupId) {
        try {
            return ResponseEntity.ok(this.muscleGroupService.getMuscleGroup(muscleGroupId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            LOGGER.error("Error accessing MuscleGroup={} by user={}",
                    muscleGroupId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("muscleGroups/{muscleGroupId}/muscleSubGroups")
    public ResponseEntity<List<MuscleSubGroupDto>> getMuscleSubgroups(@PathVariable final Long muscleGroupId) {
        try {
            return ResponseEntity.ok(this.muscleGroupService.getAllMuscleGroupMuscleSubGroups(muscleGroupId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            LOGGER.error("Error accessing MuscleGroup={} MuscleSubGroups by user={}",
                    muscleGroupId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
