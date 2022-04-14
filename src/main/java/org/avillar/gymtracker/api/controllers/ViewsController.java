package org.avillar.gymtracker.api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ViewsController {

    @GetMapping("/")
    @ResponseBody
    public String getServerStatus() {
        return "Server is running";
    }

    @GetMapping("/exercises")
    public String exercisesPage() {
        return "exercises";
    }

    @GetMapping("/addExercise")
    public String addExercisePage() {
        return "newexercise";
    }

    @GetMapping("/programs")
    public String programsPage() {
        return "programs";
    }

}
