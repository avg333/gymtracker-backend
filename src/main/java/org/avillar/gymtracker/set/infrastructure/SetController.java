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
     * Obtiene el setDTO del set con el ID especificado
     * Se usa cuando en SetModal cuando se modifica una set existente
     */
    @GetMapping("sets/{setId}")
    public ResponseEntity<SetDto> getSet(@PathVariable final Long setId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.setService.getSet(setId));
    }

    /**
     * Obtiene una Set con los datos por defecto para una nueva set en ese SetGroup
     */
    @GetMapping("setGroups/{setGroupId}/sets/newSet")
    public ResponseEntity<SetDto> getSetDefaultDataForNewSet(@PathVariable final Long setGroupId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.setService.getSetDefaultDataForNewSet(setGroupId));
    }

    /**
     * Crea una nueva Set en el SetGroup especificado.
     * Se usa en SetModal al guardar una Set nueva, pero no usa el retorno.
     */
    @PostMapping("setGroups/{setGroupId}/sets")
    public ResponseEntity<SetDto> postSet(@PathVariable final Long setGroupId, @RequestBody final SetDto setDto)
            throws EntityNotFoundException, BadFormException {
        setDto.setSetGroup(new SetGroupDto(setGroupId));
        setDto.setId(null);

        return ResponseEntity.ok(this.setService.createSet(setDto));
    }

    /**
     * Sustituye la set con el ID especificado por otra con los datos del SetDto enviado.
     * No se sustituye el padre de la Set original ni el ID de la set.
     * Devuelve el SetDto resultado de la actualización.
     * Se usa en SetModal cuando se modifica una Set existente, pero no se usa el retorno.
     */
    @PutMapping("sets/{setId}")
    public ResponseEntity<SetDto> updateSet(@PathVariable final Long setId, @RequestBody final SetDto setDto)
            throws EntityNotFoundException, BadFormException {
        setDto.setId(setId);

        return ResponseEntity.ok(this.setService.updateSet(setDto));
    }

    /**
     * Elimina la set con el ID especificado.
     * Se usa en SetModal para eliminar la set en edición.
     */
    @DeleteMapping("sets/{setId}")
    public ResponseEntity<Void> deleteSet(@PathVariable final Long setId)
            throws EntityNotFoundException, IllegalAccessException {
        this.setService.deleteSet(setId);
        return ResponseEntity.ok().build();
    }

}
