package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.model.Program;
import org.avillar.gymtracker.services.RoutineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class RoutineController {

    private final RoutineService routineService;

    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }

    @GetMapping("/programs")
    public ResponseEntity<List<Program>> getAllPrograms() {
        final List<Program> programs = this.routineService.getAllPrograms();

        return programs.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(programs, HttpStatus.OK);
    }

}
