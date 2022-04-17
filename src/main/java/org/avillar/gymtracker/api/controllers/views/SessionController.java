package org.avillar.gymtracker.api.controllers.views;

import org.avillar.gymtracker.api.dto.SessionDto;
import org.avillar.gymtracker.exceptions.ResourceNotExistsException;
import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.model.entities.Session;
import org.avillar.gymtracker.services.ProgramService;
import org.avillar.gymtracker.services.SessionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SessionController {

    private final ProgramService programService;
    private final SessionService sessionService;
    private final ModelMapper modelMapper;

    @Autowired
    public SessionController(ProgramService programService, SessionService sessionService, ModelMapper modelMapper) {
        this.programService = programService;
        this.sessionService = sessionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/sessions")
    public String sessionPage(final Model model, @RequestParam final Long programId) throws ResourceNotExistsException {
        final Program program = programService.getProgram(programId);
        model.addAttribute("userName", "Adrián Villar Gesto");
        model.addAttribute("programName", program.getName());
        return "sessions";
    }

    @PostMapping("createSession")
    public String addSession(@ModelAttribute("programDto") SessionDto sessionDto ){
        sessionService.createSession(sessionDto.getId(), modelMapper.map(sessionDto, Session.class));
        return "redirect:/sessions";
    }

    @PostMapping("updateSession")
    public String updateSession(@ModelAttribute("programDto") SessionDto sessionDto ) {
        sessionService.updateSession(sessionDto.getId(), sessionDto.getId(), modelMapper.map(sessionDto, Session.class));
        return "redirect:/sessions";
    }

    @PostMapping("deleteSession")
    public String deleteSession(@ModelAttribute("programDto") SessionDto sessionDto ) {
        sessionService.deleteSession(sessionDto.getId(), sessionDto.getId());
        return "redirect:/sessions";
    }
}
