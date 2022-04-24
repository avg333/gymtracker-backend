package org.avillar.gymtracker.controllers.views;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String programsPage(final Model model) {
        return "login";
    }
}
