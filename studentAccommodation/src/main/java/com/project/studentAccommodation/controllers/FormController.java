package com.project.studentAccommodation.controllers;

import com.project.studentAccommodation.repositories.AccommodationRepository;
import com.project.studentAccommodation.repositories.StudentRepository;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FormController {
    @Autowired
    StudentRepository studentService;
    //AccommodationRepository accommodationRepository;
    @GetMapping("/form")
    public String showLoginPage(HttpSession session, Model model) {
//        if(session.getAttribute("loggedInUser") == null) {
//            model.addAttribute("message", "Loghează-te pentru a te putea înscrie la repartizare.");
//            return "formMessage";
//        }
        return "form";
    }

//    @PostMapping("/form")
//    public String sendForm()

}
