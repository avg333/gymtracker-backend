package org.avillar.gymtracker.program.infrastructure;

import org.avillar.gymtracker.errors.application.EntityNotFoundException;
import org.avillar.gymtracker.program.application.ProgramDto;
import org.avillar.gymtracker.program.application.ProgramService;
import org.avillar.gymtracker.utils.infraestructure.PaginatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProgramController {

    private final ProgramService programService;

    @Autowired
    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping("/users/{userId}/programs/count")
    public ResponseEntity<Long> getNumberOfProgramsFromUser(@PathVariable final Long userId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.programService.getAllUserProgramsSize(userId));
    }

    @GetMapping("/users/{userId}/programs")
    public ResponseEntity<List<ProgramDto>> getAllUserPrograms(
            @PathVariable final Long userId,
            @RequestParam(required = false, defaultValue = "0") final Integer page,
            @RequestParam(required = false, defaultValue = "1") final Integer size,
            @RequestParam(required = false) final String sortParam,
            @RequestParam(required = false, defaultValue = "False") final Boolean descSort)
            throws EntityNotFoundException, IllegalAccessException {
        final Pageable pageable = PaginatorUtils.getPageable(page, size, sortParam, descSort);
        return ResponseEntity.ok(this.programService.getAllUserPrograms(userId, pageable));
    }

    @GetMapping("/programs/{programId}")
    public ResponseEntity<ProgramDto> getProgram(@PathVariable final Long programId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.programService.getProgram(programId));
    }

    @PostMapping("/users/{userId}/programs")
    public ResponseEntity<ProgramDto> postProgram(@PathVariable final Long userId, @RequestBody final ProgramDto programDto)
            throws EntityNotFoundException, IllegalAccessException {
        programDto.setId(null);
        programDto.setUserAppId(userId);

        return ResponseEntity.ok(this.programService.createProgram(programDto));
        //TODO Validate
    }

    @PutMapping("/programs/{programId}")
    public ResponseEntity<ProgramDto> putProgram(@PathVariable final Long programId, @RequestBody final ProgramDto programDto)
            throws EntityNotFoundException, IllegalAccessException {
        programDto.setId(programId);

        return ResponseEntity.ok(this.programService.updateProgram(programDto));
        //TODO Validate
    }

    @DeleteMapping("/programs/{programId}")
    public ResponseEntity<Void> deleteProgram(@PathVariable final Long programId)
            throws EntityNotFoundException, IllegalAccessException {
        this.programService.deleteProgram(programId);
        return ResponseEntity.ok().build();
    }
}