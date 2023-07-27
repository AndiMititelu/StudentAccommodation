package com.project.studentAccommodation.service;

import com.project.studentAccommodation.models.Student;
import com.project.studentAccommodation.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student authenticateStudent(String email, String password) {
        Student student = studentRepository.findByEmail(email);
        if(student != null && student.getPassword().equals(password)){
            return student;
        }
        return null;
    }

}
