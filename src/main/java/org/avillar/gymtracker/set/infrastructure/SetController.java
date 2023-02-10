package org.avillar.gymtracker.set.infrastructure;

import org.avillar.gymtracker.errors.application.exceptions.BadFormException;
import org.avillar.gymtracker.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.set.application.SetService;
import org.avillar.gymtracker.set.application.dto.SetDto;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class SetController {

    private final SetService setService;

    @Autowired
    public SetController(SetService setService) {
        this.setService = setService;
    }

    /**
     * No se usa
     */
    @GetMapping("setGroups/{setGroupId}/sets")
    public ResponseEntity<List<SetDto>> getAllSetGroupSets(@PathVariable final Long setGroupId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.setService.getAllSetGroupSets(setGroupId));
    }

    /**
     * ModalSet para obtener la set en edicion
     */
    @GetMapping("sets/{setId}")
    public ResponseEntity<SetDto> getSet(@PathVariable final Long setId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.setService.getSet(setId));
    }

    /**
     * ModalSet para obtener los datos de las nueva set
     */
    @GetMapping("setGroups/{setGroupId}/sets/newSet")
    public ResponseEntity<SetDto> getSetDefaultDataForNewSet(@PathVariable final Long setGroupId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.setService.getSetDefaultDataForNewSet(setGroupId));
    }

    /**
     * ModalSet para crear la nueva set. No se usa el retorno
     */
    @PostMapping("setGroups/{setGroupId}/sets")
    public ResponseEntity<SetDto> postSet(@PathVariable final Long setGroupId, @RequestBody final SetDto setDto)
            throws EntityNotFoundException, BadFormException {
        setDto.setSetGroup(new SetGroupDto(setGroupId));
        setDto.setId(null);

        return ResponseEntity.ok(this.setService.createSet(setDto));
    }

    /**
     * ModalSet para editar una set. No se usa el retorno
     */
    @PutMapping("sets/{setId}")
    public ResponseEntity<SetDto> updateSet(@PathVariable final Long setId, @RequestBody final SetDto setDto)
            throws EntityNotFoundException, BadFormException {
        setDto.setId(setId);

        return ResponseEntity.ok(this.setService.updateSet(setDto));
    }

    /**
     * ModalSet para eliminar una set. No se usa el retorno
     */
    @DeleteMapping("sets/{setId}")
    public ResponseEntity<Void> deleteSet(@PathVariable final Long setId)
            throws EntityNotFoundException, IllegalAccessException {
        this.setService.deleteSet(setId);
        return ResponseEntity.ok().build();
    }

}
