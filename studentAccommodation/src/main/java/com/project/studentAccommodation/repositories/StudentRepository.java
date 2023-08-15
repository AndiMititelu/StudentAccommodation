package com.project.studentAccommodation.repositories;

import com.project.studentAccommodation.models.Student;
import com.project.studentAccommodation.models.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Student findByEmail(String email);
    Student findByNrMatricol(String nrMatricol);
    Student findByResetToken(String reset_token);

}
