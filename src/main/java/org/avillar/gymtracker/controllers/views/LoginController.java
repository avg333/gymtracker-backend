package org.avillar.gymtracker.controllers.views;

import org.avillar.gymtracker.config.Url;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(Url.LOGIN)
    public String programsPage() {
        return "login";
    }


}
