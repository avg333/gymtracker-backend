package org.avillar.gymtracker.api.controllers.views;

import org.avillar.gymtracker.api.controllers.rest.ProgramController;
import org.avillar.gymtracker.api.dto.ProgramDto;
import org.avillar.gymtracker.exceptions.ResourceNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/programs")
    public String programsPage(final Model model) {

        model.addAttribute("userName", "Adrián Villar Gesto");
        return "programs";
    }

    @PostMapping("createProgram")
    public String  addProgram(@ModelAttribute("programDto") ProgramDto programDto ){
        programController.createProgram(programDto);
        return "redirect:/programs";
    }

    @PostMapping("updateProgram")
    public String updateProgram(@ModelAttribute("programDto") ProgramDto programDto ) throws ResourceNotExistsException {
        programController.updateProgram(programDto.getId() ,programDto);
        return "redirect:/programs";
    }

    @PostMapping("deleteProgram")
    public String deleteProgram(@ModelAttribute("programDto") ProgramDto programDto ) throws ResourceNotExistsException {
        programController.deleteProgram(programDto.getId());
        return "redirect:/programs";
    }

}
