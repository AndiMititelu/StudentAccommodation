package com.project.studentAccommodation.service;

import com.project.studentAccommodation.models.Accommodation;
import com.project.studentAccommodation.models.Student;
import com.project.studentAccommodation.repositories.AccommodationRepository;
import com.project.studentAccommodation.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
public class AccommodationService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AccommodationRepository accommodationRepository;
    void findBestMatch() {
        List<Student> students = studentRepository.findAll();
        List<Accommodation> accommodations = accommodationRepository.findAll();
        List<Accommodation> preferredAccommodations = new ArrayList<>();
        for(Student student : students) {
            preferredAccommodations.add(student.getPreference1());
        }

        // Irving's Algorithm
        for (Accommodation accommodation : accommodations) {
            List<Student> sortedStudents = accommodation.getSortedStudentsByScore();
            for (Student student : sortedStudents) {
                if (student.getAssignedAccommodation() == null && accommodation.getCapacity() > 0) {
                    // Tentatively accept the proposal
                    Accommodation currentAccommodation = student.getAssignedAccommodation();
                    if (currentAccommodation != null) {
                        currentAccommodation.setCapacity(currentAccommodation.getCapacity() - 1);
                    }

                    student.setAssignedAccommodation(accommodation);
                    //accommodation.setCapacity(accommodation.getCapacity() - 1);
                }
            }
        }

        // At this point, the matching is stable and complete.
        // You can save the final results in the database if needed.
    }

}
