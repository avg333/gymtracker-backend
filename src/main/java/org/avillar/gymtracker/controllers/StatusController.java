package org.avillar.gymtracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StatusController {

    @GetMapping("/")
    @ResponseBody
    public String getServerStatus() {
        return "Server is running";
    }
}
