package org.avillar.gymtracker.controllers.views;

import org.avillar.gymtracker.config.Url;
import org.avillar.gymtracker.model.dto.ProgramDto;
import org.avillar.gymtracker.services.ProgramService;
import org.avillar.gymtracker.utils.ControllerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProgramController {

    private static final String REDIRECT_PROGRAMS = "redirect:" + Url.PROGRAMS;
    private final ProgramService programService;

    @Autowired
    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping(Url.PROGRAMS)
    public String programsPage(final Model model) {
        ControllerHelper.addLogedUserToModel(model);
        return "programs";
    }

    @PostMapping("createProgram")
    public String addProgram(@ModelAttribute final ProgramDto programDto) {
        this.programService.createProgram(programDto);
        return REDIRECT_PROGRAMS;
    }

    @PostMapping("updateProgram")
    public String updateProgram(@ModelAttribute final ProgramDto programDto) throws IllegalAccessException {
        this.programService.updateProgram(programDto);
        return REDIRECT_PROGRAMS;
    }

    @PostMapping("deleteProgram")
    public String deleteProgram(@ModelAttribute final ProgramDto programDto) throws IllegalAccessException {
        this.programService.deleteProgram(programDto.getId());
        return REDIRECT_PROGRAMS;
    }
}
