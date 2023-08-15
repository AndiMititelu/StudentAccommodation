package com.project.studentAccommodation.controllers;

import com.project.studentAccommodation.models.Admin;
import com.project.studentAccommodation.models.Student;
import com.project.studentAccommodation.repositories.AdminRepository;
import com.project.studentAccommodation.repositories.StudentRepository;
import com.project.studentAccommodation.service.AdminService;
import com.project.studentAccommodation.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class PasswordResetController {
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AdminService adminService;
    @Autowired
    StudentService studentService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {
        if(studentRepository.findByResetToken(token) != null || adminRepository.findByResetToken(token) != null) {
            model.addAttribute("token", token);
            return "password-reset";
        }
        else {
            return "redirect:/";
        }

    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("password") String newPassword,
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model)  {

        //User user = userRepository.findByResetToken(token);
        Student student = studentRepository.findByResetToken(token);
        if(student == null) {

            Admin admin = adminRepository.findByResetToken(token);
            if (Objects.equals(confirmPassword, newPassword)) {
                admin.setPassword(passwordEncoder.encode(confirmPassword));
                adminService.updateAdmin(admin);
                return "redirect:/login";
            } else {
                return null; //TODO: returneaza eroare cu passwords not matching
            }
        }
        else  {
            if(Objects.equals(confirmPassword, newPassword)) {
                student.setPassword(passwordEncoder.encode(confirmPassword));
                studentService.updateStudent(student);
                return "redirect:/login";
            }
            else {
                return null; //TODO: returneaza eroare cu passwords not matching
            }
        }

    }

}
