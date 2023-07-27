package com.project.studentAccommodation.models;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "students")
//implements UserDetails??
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
    @Column(name = "email", unique = true)
    protected String email;
    @Column(name = "password")
    protected String password;
    @Column(name = "year")
    protected Integer year;
    @Column(name = "score")
    protected Integer score;
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
    @JoinColumn(name = "preference5_accommodation_id")
    protected Accommodation preference5;
    @ManyToOne
    @JoinColumn(name = "assigned_accommodation_id")
    protected Accommodation assignedAccommodation;
    @ManyToOne
    @JoinColumn(name = "confirmed_accommodation_id")
    protected Accommodation confirmedAccommodation;

//    @Enumerated(EnumType.STRING)
//    private Role role;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(role.name()));
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }

    public String getPassword() {
        return password;
    }

    public Student() {
    }

    public Student(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
