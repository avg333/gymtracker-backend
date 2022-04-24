package org.avillar.gymtracker.controllers.views;

import org.avillar.gymtracker.config.Url;
import org.avillar.gymtracker.exceptions.ResourceNotExistsException;
import org.avillar.gymtracker.model.dto.ProgramDto;
import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.services.ProgramService;
import org.avillar.gymtracker.utils.ControllerHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProgramController {

    private final ProgramService programService;
    private final ModelMapper modelMapper;

    private static final String REDIRECT_PROGRAMS = "redirect:" + Url.PROGRAMS;

    @Autowired
    public ProgramController(ProgramService programService, ModelMapper modelMapper) {
        this.programService = programService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(Url.PROGRAMS)
    public String programsPage(final Model model) {
        ControllerHelper.addLogedUserToModel(model);
        return "programs";
    }

    @PostMapping("createProgram")
    public String addProgram(@ModelAttribute final ProgramDto programDto) {
        this.programService.createProgram(modelMapper.map(programDto, Program.class));
        return REDIRECT_PROGRAMS;
    }

    @PostMapping("updateProgram")
    public String updateProgram(@ModelAttribute final ProgramDto programDto) throws ResourceNotExistsException {
        this.programService.updateProgram(programDto.getId(), modelMapper.map(programDto, Program.class));
        return REDIRECT_PROGRAMS;
    }

    @PostMapping("deleteProgram")
    public String deleteProgram(@ModelAttribute final ProgramDto programDto) throws ResourceNotExistsException {
        this.programService.deleteProgram(programDto.getId());
        return REDIRECT_PROGRAMS;
    }
}
