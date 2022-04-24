package org.avillar.gymtracker.controllers.views;

import org.avillar.gymtracker.config.Url;
import org.avillar.gymtracker.exceptions.ResourceNotExistsException;
import org.avillar.gymtracker.model.dto.SessionDto;
import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.model.entities.Session;
import org.avillar.gymtracker.services.ProgramService;
import org.avillar.gymtracker.services.SessionService;
import org.avillar.gymtracker.utils.ControllerHelper;
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
    private static final String REDIRECT_SESSIONS = "redirect:" + Url.SESSIONS;

    @Autowired
    public SessionController(ProgramService programService, SessionService sessionService, ModelMapper modelMapper) {
        this.programService = programService;
        this.sessionService = sessionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(Url.SESSIONS)
    public String sessionPage(final Model model, @RequestParam final Long programId) throws ResourceNotExistsException {
        ControllerHelper.addLogedUserToModel(model);
        final Program program = this.programService.getProgram(programId);
        model.addAttribute("programName", program.getName());
        return "sessions";
    }

    @PostMapping("createSession")
    public String addSession(@ModelAttribute final SessionDto sessionDto) {
        this.sessionService.createSession(sessionDto.getId(), modelMapper.map(sessionDto, Session.class));
        return REDIRECT_SESSIONS;
    }

    @PostMapping("updateSession")
    public String updateSession(@ModelAttribute final SessionDto sessionDto) {
        this.sessionService.updateSession(sessionDto.getId(), sessionDto.getId(), modelMapper.map(sessionDto, Session.class));
        return REDIRECT_SESSIONS;
    }

    @PostMapping("deleteSession")
    public String deleteSession(@ModelAttribute final SessionDto sessionDto) {
        this.sessionService.deleteSession(sessionDto.getId(), sessionDto.getId());
        return REDIRECT_SESSIONS;
    }
}
