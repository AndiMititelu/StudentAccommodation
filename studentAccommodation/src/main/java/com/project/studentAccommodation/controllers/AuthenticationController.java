package com.project.studentAccommodation.controllers;

import com.project.studentAccommodation.models.Admin;
import com.project.studentAccommodation.models.Role;
import com.project.studentAccommodation.models.Student;
import com.project.studentAccommodation.repositories.AdminRepository;
import com.project.studentAccommodation.repositories.StudentRepository;
import com.project.studentAccommodation.service.AdminService;
import com.project.studentAccommodation.service.StudentService;
import jakarta.servlet.http.HttpSession;
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
    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/login")
    public String showLoginPage(Authentication authentication) {
        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalidate the session to log out the user
        return "redirect:/";  // Redirect to the home page or any other page after logout
    }

    @PostMapping("/login")
    public String doLogin(HttpSession session, RedirectAttributes redirectAttributes, @RequestParam("email") String email, @RequestParam("password") String password)
    {
        //Search the email in ADMIN repo and STUDENT repo
        //role == Role.STUDENT
        if(studentRepository.findByEmail(email) != null) {
            Student authenticatedStudent = studentService.authenticateStudent(email, password);
            if(authenticatedStudent != null) {
                session.setAttribute("loggedInUser", authenticatedStudent); // store the user's login session
                session.setAttribute("userRole", "STUDENT"); // Store the user's role in the session
                return "redirect:/";
            }
            else {
                // Authentication failed, redirect back to authentication page
                redirectAttributes.addFlashAttribute("error", "Invalid credentials");
                return "redirect:/login";
            }
        }
        //role == Role.ADMIN
        else if (adminRepository.findByEmail(email) != null) {
            Admin admin = adminService.authenticateAdmin(email, password);
            if(admin != null) {
                session.setAttribute("loggedInUser", admin);
                session.setAttribute("userRole", "ADMIN"); // Store the user's role in the session
                return "redirect:/";
            }
            else {
                redirectAttributes.addFlashAttribute("error", "Invalid credentials");
                return "redirect:/login";
            }
        }
        return null;
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute("student") Student student, HttpSession session, RedirectAttributes redirectAttributes) {

        Student registeredStudent = studentService.registerStudent(student.getNrMatricol(), student.getEmail(), student.getPassword());
        if(registeredStudent != null) {
            //Register successful, save user info in session
            session.setAttribute("loggedInUser", registeredStudent);
            session.setAttribute("userRole", "STUDENT");
            return "redirect:/";
        }
        else {
            //Registration failed, redirect back to authentication page
            redirectAttributes.addFlashAttribute("error", "Email already user");
            return "redirect:/login";
        }
    }

}
