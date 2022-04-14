package org.avillar.gymtracker.api.controllers;

import org.avillar.gymtracker.api.dto.ProgramDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ViewsController {

    @Autowired
    private ProgramController programController;

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

    @PostMapping("/programs/createProgram")
    public RedirectView addProgram(@ModelAttribute("programDto") ProgramDto programDto ){
        programController.createProgram(programDto);
        return new RedirectView("");
    }

}
