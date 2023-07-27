package com.project.studentAccommodation.repositories;

import com.project.studentAccommodation.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Student findByEmail(String email);
    //Optional<Student> findByNrMatricol(String nrMatricol);

}
