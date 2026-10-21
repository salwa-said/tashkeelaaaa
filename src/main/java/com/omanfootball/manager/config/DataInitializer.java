package com.omanfootball.manager.config;


import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;


import com.omanfootball.manager.entity.Role;
import com.omanfootball.manager.entity.User;
import com.omanfootball.manager.repository.UserRepository;


@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap-admin.email:}")
    private String adminEmail;

    @Value("${app.bootstrap-admin.password:}")
    private String adminPassword;


    @Override
    public void run(String... args) {


        if (adminEmail == null || adminEmail.isBlank()
                || adminPassword == null || adminPassword.isBlank()
                || userRepository.existsByEmail(adminEmail)) {
            return;
        }


        User admin = new User();


        admin.setFullName(
                "System Admin"
        );


        admin.setEmail(
                adminEmail
        );


        admin.setPassword(
                passwordEncoder.encode(
                        adminPassword
                )
        );


        admin.setRole(
                Role.ADMIN
        );


        userRepository.save(admin);


    }


}



