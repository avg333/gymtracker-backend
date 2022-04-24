package org.avillar.gymtracker.controllers.views;

import org.avillar.gymtracker.exceptions.ResourceNotExistsException;
import org.avillar.gymtracker.model.dto.ProgramDto;
import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.services.ProgramService;
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

    @Autowired
    public ProgramController(ProgramService programService, ModelMapper modelMapper) {
        this.programService = programService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/programs")
    public String programsPage(final Model model) {
        model.addAttribute("userName", "Adrián Villar Gesto");
        return "programs";
    }

    @PostMapping("createProgram")
    public String addProgram(@ModelAttribute("programDto") ProgramDto programDto) {
        programService.createProgram(modelMapper.map(programDto, Program.class));
        return "redirect:/programs";
    }

    @PostMapping("updateProgram")
    public String updateProgram(@ModelAttribute("programDto") ProgramDto programDto) throws ResourceNotExistsException {
        programService.updateProgram(programDto.getId(), modelMapper.map(programDto, Program.class));
        return "redirect:/programs";
    }

    @PostMapping("deleteProgram")
    public String deleteProgram(@ModelAttribute("programDto") ProgramDto programDto) throws ResourceNotExistsException {
        programService.deleteProgram(programDto.getId());
        return "redirect:/programs";
    }
}
