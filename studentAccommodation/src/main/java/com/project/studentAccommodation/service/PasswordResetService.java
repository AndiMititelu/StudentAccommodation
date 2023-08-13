package com.project.studentAccommodation.service;

import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {    private final Map<String, PasswordResetToken> tokenMap = new HashMap<>();


}
