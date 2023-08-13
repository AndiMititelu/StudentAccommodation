package com.project.studentAccommodation.repositories;

import com.project.studentAccommodation.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Admin findByNrMatricol(String nrMatricol);
    Admin findByEmail(String email);

}
