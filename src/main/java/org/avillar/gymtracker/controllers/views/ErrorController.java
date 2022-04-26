package org.avillar.gymtracker.controllers.views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    @GetMapping(value = "errors")
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        ModelAndView errorPage = new ModelAndView("404");
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400 -> {
                errorMsg = "Http Error Code: 400. Bad Request";
            }
            case 401 -> {
                errorMsg = "Http Error Code: 401. Unauthorized";
            }
            case 404 -> {
                errorMsg = "Http Error Code: 404. Resource not found";
            }
            case 500 -> {
                errorMsg = "Http Error Code: 500. Internal Server Error";
            }
        }
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }
}
