package com.project.studentAccommodation.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "accommodations")
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @Column(name = "name", unique = true)
    protected String name;
    @Column(name = "capacity_men") //Number of rooms
    protected Integer capacityMen;
    @Column(name = "capacity_women")
    protected Integer capacityWomen;
    @OneToMany(mappedBy = "assignedAccommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> acceptedStudents;
    @OneToMany(mappedBy = "confirmedAccommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<Student> confirmedStudentList;

}
