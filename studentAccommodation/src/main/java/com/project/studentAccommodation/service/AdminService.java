package com.project.studentAccommodation.service;

import com.project.studentAccommodation.models.Admin;
import com.project.studentAccommodation.models.Student;
import com.project.studentAccommodation.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    protected AdminRepository adminRepository;

    protected BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //TODO: foloseste criptarea dupa ce termini exemplul + maybe adauga nr matricol
    public Admin authenticateAdmin(String email, String password) {
        Admin admin = adminRepository.findByEmail(email);
        if(admin != null && password.equals(admin.getPassword())) {
            return admin;
        }
        else
            return null;

    }

}
