package com.project.studentAccommodation.controllers;

import com.project.studentAccommodation.models.Accommodation;
import com.project.studentAccommodation.models.Student;
import com.project.studentAccommodation.repositories.AccommodationRepository;
import com.project.studentAccommodation.repositories.StudentRepository;
import com.project.studentAccommodation.service.AccommodationService;
import com.project.studentAccommodation.service.StudentService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
public class AdminController {
    @Autowired
    StudentService studentService;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AccommodationService accommodationService;
    @Autowired
    AccommodationRepository accommodationRepository;

    @GetMapping("admin/manage")
    public String showStudentList(Model model, HttpSession session) {

        String userRole = (String) session.getAttribute("userRole");
        if(!Objects.equals(userRole, "ADMIN"))
            return "redirect:/";

        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "form";

    }
    @PostMapping("admin/manage")
    public String findAccommodation() {
        accommodationService.findBestAccommodation();
        return "redirect:/form";
    }

    @PostMapping("/delete-student/{id}")
    public String deleteStudent(@PathVariable UUID id) {
        studentService.deleteStudentById(id);
        return "redirect:/form";
    }

    @GetMapping("/export-csv")
    public void exportCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=students.csv");

        List<Student> students = studentRepository.findAll();

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_PREFERENCE);
        //The column for the CSV file
        String[] csvHeader = {"Nr Matricol","Nume", "Prenume", "Email", "Anul Studiilor", "Score",  "Pref 1", "Pref 2", "Pref 3", "Pref 4"};
        String[] nameMapping = {"nrMatricol","lastName", "firstName", "email", "year", "score", "preference1", "preference2", "preference3", "preference4"};
        csvBeanWriter.writeHeader(csvHeader);

        for(Student student : students) {
            csvBeanWriter.write(student, nameMapping);
        }

        csvBeanWriter.close();

    }
    @PostMapping("/admin/accept-student")
    public String acceptStudent(@RequestParam("studentId") UUID studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        Accommodation accommodation = accommodationRepository.findByName(student.getAssignedAccommodation());
        accommodation.setCapacityLeft(accommodation.getCapacityLeft() - 1);
        studentService.acceptStudent(studentId);
        return "redirect:/admin/manage"; // Redirect back to the student list
    }

    @PostMapping("/admin/reject-student")
    public String rejectStudent(@RequestParam("studentId") UUID studentId) {
        studentService.deleteStudentById(studentId);
        return "redirect:/admin/manage"; // Redirect back to the student list
    }

}
