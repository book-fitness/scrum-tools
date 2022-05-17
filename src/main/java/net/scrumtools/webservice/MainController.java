package net.scrumtools.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping({"/", "/main"})
public class MainController {

    @Autowired
    private HttpSession httpSession;

    @GetMapping
    public String mainPage() {
        System.out.println("sessionId " + httpSession.getId() + " started");
        return "index";
    }
}