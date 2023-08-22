package com.project.studentAccommodation.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
public class Student extends User {
    @Column(name = "nr_matricol", unique = true)
    protected String nrMatricol;
//    @Column(name = "email", unique = true, nullable = false)
//    protected String email;
//    @Column(name = "password", nullable = false)
//    protected String password;
    @Column(name = "year")
    protected Integer year;
    @Column(name = "score")
    protected Integer score;
    @Column(name = "gender")
    protected String gender;
    //    @ManyToOne
//    @JoinColumn(name = "preference1_accommodation_id")
    protected String preference1;
    //    @ManyToOne
//    @JoinColumn(name = "preference2_accommodation_id")
    protected String preference2;
    //    @ManyToOne
//    @JoinColumn(name = "preference3_accommodation_id")
    protected String preference3;
    //    @ManyToOne
//    @JoinColumn(name = "preference4_accommodation_id")
    protected String preference4;
    //    @ManyToOne
//    @JoinColumn(name = "assigned_accommodation_id")
    protected String assignedAccommodation;
    //    @ManyToOne
//    @JoinColumn(name = "confirmed_accommodation_id")
    protected Boolean confirmedAccommodation;

    public Student(String nrMatricol, String email, String password) {
        this.nrMatricol = nrMatricol;
        this.email = email;
        this.password = password;
    }

    public List<String> getAllPreferences() {
        if (confirmedAccommodation == null && assignedAccommodation == null) {
            List<String> preferences = new ArrayList<>();
            preferences.add(preference1);
            preferences.add(preference2);
            preferences.add(preference3);
            preferences.add(preference4);
            return preferences;
        } else
            return null;
    }
}
