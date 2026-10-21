package com.omanfootball.manager.service;


import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.omanfootball.manager.entity.Role;
import com.omanfootball.manager.entity.User;
import com.omanfootball.manager.repository.UserRepository;



@Service
@RequiredArgsConstructor
public class AdminService {



    private final UserRepository userRepository;



    private final PasswordEncoder passwordEncoder;





    // =========================
    // CREATE USER
    // =========================

    public User createUser(User user) {



        if (userRepository.existsByEmail(user.getEmail())) {


            throw new RuntimeException(
                    "Email already exists"
            );

        }





        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );





        if (user.getRole() == null) {


            user.setRole(Role.USER);


        }





        return userRepository.save(user);


    }






    // =========================
    // DELETE USER
    // =========================

    public void deleteUser(Long id) {



        if (!userRepository.existsById(id)) {


            throw new RuntimeException(
                    "User not found"
            );

        }





        userRepository.deleteById(id);


    }



}