package com.project.studentAccommodation.controllers;

import com.project.studentAccommodation.models.Admin;
import com.project.studentAccommodation.models.Student;
import com.project.studentAccommodation.repositories.AdminRepository;
import com.project.studentAccommodation.repositories.StudentRepository;
import com.project.studentAccommodation.service.AdminService;
import com.project.studentAccommodation.service.StudentService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class PasswordRecoveryController {

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AdminService adminService;
    @Autowired
    StudentService studentService;

    private final JavaMailSender emailSender;

    public PasswordRecoveryController(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }


    @GetMapping("/password-recovery")
    public String showPage() {
        return "pswd-recovery";
    }

//    @PostMapping("/reset-password")
//    public String processPasswordReset(@RequestParam("email") String email, Model model) {
//        Admin admin = adminRepository.findByEmail(email);
//        if(admin != null) {
//            String resetToken = generateUniqueToken();
//            sendResetTokenEmail(admin, resetToken);
//            HttpSession session = request
//        }
//
//        return null;
//    }

//    private String generateUniqueToken() {
//        // Generate a unique token, e.g., using UUID.randomUUID()
//        return UUID.randomUUID().toString();
//    }

    @PostMapping("/password-recovery")
    public String processPasswordRecovery(@RequestParam("email") String email) throws MessagingException {
        Admin admin = adminRepository.findByEmail(email);
        if(admin != null) {
            String resetToken = UUID.randomUUID().toString();
            admin.setResetToken(resetToken);
            //TODO: maybe add expiration for resetToken
            adminService.updateAdmin(admin);

            //Send email with reset link
            String resetLink = "localhost:8082/reset-password?token=" + resetToken;

            String emailContent = "Click the link below to reset your password:<br>"
                    + "<a href='" + resetLink + "'>" + resetLink + "</a>";
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Password Reset");
            helper.setText(emailContent, true);
            emailSender.send(message);

            return "redirect:/login";

        }
        else {
            Student student = studentRepository.findByEmail(email);
            if(student != null) {
                String resetToken = UUID.randomUUID().toString();
                student.setResetToken(resetToken);
                //TODO: maybe add expiration for resetToken
                studentService.updateStudent(student);

                //Send email with reset link
                String resetLink = "localhost:8082/reset-password?token=" + resetToken;
                String emailContent = "Click the link below to reset your password:<br>"
                        + "<a href='" + resetLink + "'>" + resetLink + "</a>";
                MimeMessage message = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setTo(email);
                helper.setSubject("Password Reset");
                helper.setText(emailContent, true);
                emailSender.send(message);

                return "redirect:/login";

            }
        }
        return null;
    }

}
