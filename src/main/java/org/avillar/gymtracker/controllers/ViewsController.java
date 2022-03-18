package org.avillar.gymtracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewsController {

    @GetMapping("/exercises")
    public String exercisesPage(){
        return "exercises";
    }

    @GetMapping("/addExercise")
    public String addExercisePage(){
        return "addExercise";
    }

    @GetMapping("/programs")
    public String programsPage(){
        return null;
    }

    @GetMapping("/statistics")
    public String statisticsPage(){
        return null;
    }
}
