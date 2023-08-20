package com.project.studentAccommodation.service;

import com.project.studentAccommodation.models.Accommodation;
import com.project.studentAccommodation.models.Student;
import com.project.studentAccommodation.repositories.AccommodationRepository;
import com.project.studentAccommodation.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccommodationService {
//    @Autowired
//    StudentRepository studentRepository;
    @Autowired
    AccommodationRepository accommodationRepository;
    @Autowired
    StudentService studentService;
    public void findBestAccommodation() {
        List<Student> students = studentService.getStudentsAfterScore();
        for(Student student : students) {
            if(studentService.isAssigned(student) == false) {
                for(String preference : student.getAllPreferences()) {
                    if(isPreferenceAvailable(preference, student) == true) {
                        updateAccommodationForStudent(student, preference);
                        break;
                    }
                }
            }
        }
    }

    boolean isPreferenceAvailable(String preferenceName, Student student) {
        for(Accommodation accommodation : accommodationRepository.findAll()) {
            if(Objects.equals(accommodation.getName(), preferenceName)) {
                //TODO: verificare gender inainte
                if(accommodation.getCapacityLeft() > 0) {
                    //lower capacity for that accommodation
                    accommodation.setCapacityLeft(accommodation.getCapacityLeft() - 1);
                    return true;
                }
                else
                    return false;
            }
        }

        return false;
    }

    void updateAccommodationForStudent(Student student, String accommodationName) {
        student.setAssignedAccommodation(accommodationRepository.findByName(accommodationName).getName());
        studentService.updateStudent(student);
    }

}
