package com.project.studentAccommodation.controllers;

import com.project.studentAccommodation.models.Student;
import com.project.studentAccommodation.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.apache.naming.HandlerRef;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthenticationController {
    @Autowired
    private StudentService studentService;
    @GetMapping("/login")
    public String showLoginPage(Authentication authentication) {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(HttpSession session, RedirectAttributes redirectAttributes, @RequestParam("email") String email, @RequestParam("password") String password)
    {
        Student authenticatedStudent = studentService.authenticateStudent(email, password);
        if(authenticatedStudent != null) {
            // Authentication successful, save user info in session
            session.setAttribute("loggedInUser", authenticatedStudent);
            return "redirect:/";
        }
        else {
            // Authentication failed, redirect back to authentication page
            redirectAttributes.addFlashAttribute("error", "Invalid credentials");
            return "redirect:/login";
        }
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute("student") Student student, HttpSession session, RedirectAttributes redirectAttributes) {
        System.out.println(student.getPassword());
        Student registeredStudent = studentService.registerStudent(student.getFirstName(), student.getLastName(), student.getEmail(), student.getPassword());
        if(registeredStudent != null) {
            //Register successful, save user info in session
            session.setAttribute("loggedInUser", registeredStudent);
            return "redirect:/";
        }
        else {
            //Registration failed, redirect back to authentication page
            redirectAttributes.addFlashAttribute("error", "Email already user");
            return "redirect:/login";
        }
    }

}
