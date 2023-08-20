package com.project.studentAccommodation.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "accommodations")
@Getter
@Setter
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @Column(name = "name", unique = true)
    protected String name;
//    @Column(name = "capacity_men")
//    protected Integer capacityMen;
    @Column(name = "capacity")
    protected Integer capacity;
    @Column(name = "capacity_left")
    protected Integer capacityLeft;
    @Column(name = "preference1_students") //Studenti - nr matricol
//    @OneToMany(mappedBy = "preference1", cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<String> preference1Students;
//    @OneToMany(mappedBy = "assignedAccommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "acceptedStudents") //Studenti - nr matricol
    private List<String> acceptedStudents;
//    @OneToMany(mappedBy = "confirmedAccommodation", cascade = CascadeType.ALL, orphanRemoval = true)
//    protected List<String> confirmedStudentList;

//    public List<Student> getSortedStudentsByScore() {
//        List<Student> sortedStudents = new ArrayList<>();
//        sortedStudents.addAll(preference1Students);
//        sortedStudents.sort(Comparator.comparingInt(Student::getScore).reversed());
//        return sortedStudents;
//    }

}
