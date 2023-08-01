package com.project.studentAccommodation.controllers;

import com.project.studentAccommodation.models.Accommodation;
import com.project.studentAccommodation.models.Student;
import com.project.studentAccommodation.repositories.AccommodationRepository;
import com.project.studentAccommodation.repositories.StudentRepository;
import com.project.studentAccommodation.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
//        if(session.getAttribute("loggedInUser") == null) {
//            model.addAttribute("message", "Loghează-te pentru a te putea înscrie la repartizare.");
//            return "formMessage";
//        }
        return "form";
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
            Accommodation preference1 = accommodationRepository.findByName(option1);
            loggedStudent.setPreference1(preference1);
            Accommodation preference2 = accommodationRepository.findByName(option2);
            loggedStudent.setPreference2(preference2);
            Accommodation preference3 = accommodationRepository.findByName(option3);
            loggedStudent.setPreference1(preference3);
            Accommodation preference4 = accommodationRepository.findByName(option4);
            loggedStudent.setPreference1(preference4);

            //Update loggedStudent in the DB
            studentService.updateStudent(loggedStudent);
            return "redirect:/";
        }
        else
            return "redirect:/login"; //TODO: schimba redirectu!

    }

}
