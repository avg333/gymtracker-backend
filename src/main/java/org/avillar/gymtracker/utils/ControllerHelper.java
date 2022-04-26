package org.avillar.gymtracker.utils;

import org.avillar.gymtracker.config.security.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

public class ControllerHelper {

    public static void addLogedUserToModel(final Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) auth.getPrincipal();
        model.addAttribute("userName", myUserDetails.getUserApp().getName() + " " + myUserDetails.getUserApp().getLastNameFirst());
    }
}
