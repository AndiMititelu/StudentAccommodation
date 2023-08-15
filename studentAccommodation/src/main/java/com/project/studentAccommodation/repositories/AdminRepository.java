package com.project.studentAccommodation.repositories;

import com.project.studentAccommodation.models.Admin;
import com.project.studentAccommodation.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Admin findByNrMatricol(String nrMatricol);
    Admin findByEmail(String email);
//    @Query("SELECT a FROM admins a")
//    List<Admin> findAllAdmins();
    Admin findByResetToken(String reset_token);

}
