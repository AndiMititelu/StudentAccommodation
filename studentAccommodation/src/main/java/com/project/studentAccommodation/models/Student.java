package com.project.studentAccommodation.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(columnDefinition = "uuid", updatable = false)
    protected UUID id;
    @Column(name = "first_name")
    protected String firstName;
    @Column(name = "last_name")
    protected String lastName;
    @Column(name = "nr_matricol", unique = true)
    protected String nrMatricol;
    @Column(name = "email", unique = true, nullable = false)
    protected String email;
    @Column(name = "password", nullable = false)
    protected String password;
    @Column(name = "year")
    protected Integer year;
    @Column(name = "score")
    protected Integer score;
    @Column(name = "gender")
    protected String gender;
    @ManyToOne
    @JoinColumn(name = "preference1_accommodation_id")
    protected Accommodation preference1;
    @ManyToOne
    @JoinColumn(name = "preference2_accommodation_id")
    protected Accommodation preference2;
    @ManyToOne
    @JoinColumn(name = "preference3_accommodation_id")
    protected Accommodation preference3;
    @ManyToOne
    @JoinColumn(name = "preference4_accommodation_id")
    protected Accommodation preference4;
    @ManyToOne
    @JoinColumn(name = "assigned_accommodation_id")
    protected Accommodation assignedAccommodation;
    @ManyToOne
    @JoinColumn(name = "confirmed_accommodation_id")
    protected Accommodation confirmedAccommodation;

    public Student(String nrMatricol, String email, String password) {
//        this.firstName = firstName;
//        this.lastName = lastName;
        this.nrMatricol = nrMatricol;
        this.email = email;
        this.password = password;
    }
}
