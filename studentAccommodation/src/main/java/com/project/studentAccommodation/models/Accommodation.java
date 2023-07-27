package com.project.studentAccommodation.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "accommodations")
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @Column(name = "name", unique = true)
    protected String name;
    @Column(name = "capacity") //Number of rooms
    protected Integer capacity;
    @Column(name = "beds") //Number of beds per room
    protected Integer beds;
    @OneToMany(mappedBy = "assignedAccommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> acceptedStudents;
    @OneToMany(mappedBy = "confirmedAccommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<Student> confirmedStudentList;

    public Accommodation() {
    }

    public Accommodation(String name, Integer capacity, Integer beds) {
        this.name = name;
        this.capacity = capacity;
        this.beds = beds;
    }
}
