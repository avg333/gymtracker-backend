package org.avillar.gymtracker.api.controllers.views;

import org.avillar.gymtracker.api.controllers.rest.ProgramRestController;
import org.avillar.gymtracker.api.dto.ProgramDto;
import org.avillar.gymtracker.exceptions.ResourceNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


}
