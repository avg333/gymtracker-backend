package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.model.Program;
import org.avillar.gymtracker.model.Session;
import org.avillar.gymtracker.services.RoutineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoutineController {


    @GetMapping("programs")
    public ResponseEntity<Program> getAllPrograms() {
        return new ResponseEntity<>(new Program(), HttpStatus.OK);
    }

    @PostMapping("program")
    public ResponseEntity<Program> addProgram(){
        return new ResponseEntity<>(new Program(), HttpStatus.OK);
    }

    @PutMapping("program")
    public ResponseEntity<Program> updateProgram(){
        return new ResponseEntity<>(new Program(), HttpStatus.OK);
    }

    @DeleteMapping("program/{id}")
    public ResponseEntity<Void> deleteProgram(){
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("sessions")
    public ResponseEntity<Session> getAllSessions() {
        return new ResponseEntity<>(new Session(), HttpStatus.OK);
    }

    @PostMapping("sessions")
    public ResponseEntity<Session> addSession(){
        return new ResponseEntity<>(new Session(), HttpStatus.OK);
    }

    @PutMapping("sessions")
    public ResponseEntity<Session> updateSession(){
        return new ResponseEntity<>(new Session(), HttpStatus.OK);
    }

    @DeleteMapping("sessions/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable String id){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
