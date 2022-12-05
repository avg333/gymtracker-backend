package org.avillar.gymtracker.set.infrastructure;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.base.infrastructure.BaseController;
import org.avillar.gymtracker.set.application.SetService;
import org.avillar.gymtracker.set.application.dto.SetDto;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class SetController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SetController.class);

    private final SetService setService;

    @Autowired
    public SetController(SetService setService) {
        this.setService = setService;
    }

    @GetMapping("setGroups/{setGroupId}/sets")
    public ResponseEntity<List<SetDto>> getAllSetGroupSets(@PathVariable final Long setGroupId) {
        try {
            return ResponseEntity.ok(this.setService.getAllSetGroupSets(setGroupId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access setGroup={} sets by user={}",
                    setGroupId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error accessing setGroup={} sets by user={}",
                    setGroupId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("sets/{setId}")
    public ResponseEntity<SetDto> getSet(@PathVariable final Long setId) {
        try {
            return ResponseEntity.ok(this.setService.getSet(setId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access set={} by user={}",
                    setId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error accessing set={} by user={}",
                    setId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("setGroups/{setGroupId}/sets")
    public ResponseEntity<SetDto> postSet(@PathVariable final Long setGroupId, @RequestBody final SetDto setDto) {
        final SetGroupDto setGroupDto = new SetGroupDto();
        setGroupDto.setId(setGroupId);
        setDto.setSetGroup(setGroupDto);
        setDto.setId(null);

        try {
            return ResponseEntity.ok(this.setService.createSet(setDto));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access to create set for setGroup={} by user={}",
                    setGroupId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error creating set by user={}",
                    this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("sets/{setId}")
    public ResponseEntity<SetDto> updateSet(@PathVariable final Long setId, @RequestBody final SetDto setDto) {
        setDto.setId(setId);

        try {
            return ResponseEntity.ok(this.setService.updateSet(setDto));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access to update set={} by user={}",
                    setId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error updating set={} by user={}",
                    setId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("sets/{setId}")
    public ResponseEntity<Void> deleteSet(@PathVariable final Long setId) {
        try {
            this.setService.deleteSet(setId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access to remove set={} by user={}",
                    setId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error removing set={} by user={}",
                    setId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}