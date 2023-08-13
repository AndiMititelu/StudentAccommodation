package com.project.studentAccommodation.controllers;

import com.project.studentAccommodation.models.Admin;
import com.project.studentAccommodation.models.User;
import com.project.studentAccommodation.repositories.AdminRepository;
import com.project.studentAccommodation.repositories.StudentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class PasswordResetController {

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/reset-password")
    public String showPage() {
        return "pswd-recovery";
    }

    @PostMapping("/reset-password")
    public String processPasswordReset(@RequestParam("email") String email, Model model) {
        Admin admin = adminRepository.findByEmail(email);
        if(admin != null) {
            String resetToken = generateUniqueToken();
            sendResetTokenEmail(admin, resetToken);
            HttpSession session = request
        }

        return null;
    }


    private String generateUniqueToken() {
        // Generate a unique token, e.g., using UUID.randomUUID()
        return UUID.randomUUID().toString();
    }

    private void sendResetTokenEmail(User user, String resetToken) {
        // Implement your email sending logic here
        // Send an email to the user containing the reset token and a link
    }

}
