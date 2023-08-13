package com.project.studentAccommodation.controllers;

import com.project.studentAccommodation.models.Accommodation;
import com.project.studentAccommodation.models.Role;
import com.project.studentAccommodation.models.Student;
import com.project.studentAccommodation.repositories.AccommodationRepository;
import com.project.studentAccommodation.repositories.StudentRepository;
import com.project.studentAccommodation.service.AccommodationService;
import com.project.studentAccommodation.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
public class AccommodationController {
    @Autowired
    StudentService studentService;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AccommodationRepository accommodationRepository;

    @GetMapping("/form")
    public String showLoginPage(HttpSession session, Model model) {
        String userRole = (String) session.getAttribute("userRole");
//        String isLoggedIn = (String) session.getAttribute("loggedInUser");
        if(Objects.equals(userRole, "STUDENT"))
            return "form";
        else if (session.getAttribute("loggedInUser") == null)
            return "form";

//        return "redirect:/";
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "redirect:/admin/manage";
    }

    @PostMapping("/form")
    public String sendForm(HttpSession session, RedirectAttributes redirectAttributes, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("year") Integer year, @RequestParam("gender") String gender, @RequestParam("option1") String option1, @RequestParam("option2") String option2, @RequestParam("option3") String option3, @RequestParam("option4") String option4) {
        Student loggedStudent = (Student) session.getAttribute("loggedInUser");
        if(loggedStudent != null) {
            loggedStudent.setFirstName(firstName);
            loggedStudent.setLastName(lastName);
            loggedStudent.setYear(year);
            loggedStudent.setGender(gender);

            //Setting the accommodation preferences
            //Accommodation preference1 = accommodationRepository.findByName(option1);
            if(accommodationRepository.findByName(option1) != null)
                loggedStudent.setPreference1(option1);
            //Accommodation preference2 = accommodationRepository.findByName(option2);
            if(accommodationRepository.findByName(option2) != null)
                loggedStudent.setPreference2(option2);
            //Accommodation preference3 = accommodationRepository.findByName(option3);
            if(accommodationRepository.findByName(option3) != null)
                loggedStudent.setPreference3(option3);
            //Accommodation preference4 = accommodationRepository.findByName(option4);
            if(accommodationRepository.findByName(option4) != null)
                loggedStudent.setPreference4(option4);

            //Update loggedStudent in the DB
            studentService.updateStudent(loggedStudent);
            //accommodationService.findBestAccommodation();
            return "redirect:/";
        }
        else
            return "redirect:/login"; //TODO: schimba redirectu!

    }


}
