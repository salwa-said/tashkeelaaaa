package com.omanfootball.manager.service;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


import com.omanfootball.manager.entity.User;
import com.omanfootball.manager.repository.UserRepository;



@Service
@RequiredArgsConstructor
public class UserService {



    private final UserRepository userRepository;





    public List<User> getAllUsers() {

        return userRepository.findAll();

    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }





    public User getUserById(Long id) {


        return userRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "User not found with id: " + id
                        )
                );

    }





    @Transactional
    public User updateUser(
            Long id,
            User newUser
    ) {


        User user = getUserById(id);



        user.setFullName(
                newUser.getFullName()
        );



        if (
                newUser.getEmail() != null
                        &&
                        !newUser.getEmail()
                                .equalsIgnoreCase(user.getEmail())
        ) {



            if (userRepository.existsByEmail(newUser.getEmail())) {

                throw new RuntimeException(
                        "Email already exists"
                );

            }



            user.setEmail(
                    newUser.getEmail()
            );

        }



        if (newUser.getRole() != null) {

            user.setRole(
                    newUser.getRole()
            );

        }



        return userRepository.save(user);

    }





    @Transactional
    public void deleteUser(Long id) {


        User user = getUserById(id);



        userRepository.delete(user);


    }


}
