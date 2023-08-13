package com.project.studentAccommodation.service;

import com.project.studentAccommodation.models.Student;
import com.project.studentAccommodation.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {
    @Autowired
    protected StudentRepository studentRepository;
    protected BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Student authenticateStudent(String email, String password) {
        Student student = studentRepository.findByEmail(email);
        if(student != null && passwordEncoder.matches(password, student.getPassword())){
            return student;
        }
        return null;
    }

    public Student registerStudent(String nrMatricol, String email, String password) {
        if (studentRepository.findByEmail(email) != null) {
            return null; // Registration failed, student with email already exists
        }
        Student student = new Student(nrMatricol, email, passwordEncoder.encode(password));

        return studentRepository.save(student);
    }

    public void updateStudent(Student student) {
        Student foundStudent = studentRepository.findByNrMatricol(student.getNrMatricol());
        if(foundStudent != null){
            foundStudent.setPreference1(student.getPreference1());
            foundStudent.setPreference2(student.getPreference2());
            foundStudent.setPreference3(student.getPreference3());
            foundStudent.setPreference4(student.getPreference4());
            foundStudent.setYear(student.getYear());
            foundStudent.setFirstName(student.getFirstName());
            foundStudent.setLastName(student.getLastName());
            foundStudent.setGender(student.getGender());
            studentRepository.save(foundStudent);
        }
    }

    public void deleteStudentById(UUID id) {
        studentRepository.deleteById(id);
    }

    public List<Student> getStudentsAfterScore() {
        List<Student> students = studentRepository.findAll();
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                // Sorting: higher score first
                return Integer.compare(s2.getScore(), s1.getScore());
            }
        });
        return students;
    }

    public boolean isAssigned(Student student) {
        return student.getAssignedAccommodation() != null;
    }

}
