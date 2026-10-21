package com.omanfootball.manager.service;


import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.omanfootball.manager.dto.LoginResponse;
import com.omanfootball.manager.dto.RegisterRequest;
import com.omanfootball.manager.entity.Role;
import com.omanfootball.manager.entity.User;
import com.omanfootball.manager.repository.UserRepository;



@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;


    private final OtpService otpService;


    private final EmailService emailService;


    private final JwtService jwtService;





    public User register(RegisterRequest request) {
        String email = request.getEmail().trim().toLowerCase(java.util.Locale.ROOT);
        String fullName = request.getFullName().trim();
        String password = request.getPassword();

        if (fullName.isEmpty()) {
            throw new IllegalArgumentException("Full name is required");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("An account with this email already exists");
        }

        validatePassword(password);

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        return userRepository.save(user);

    }





    public LoginResponse login(
            String email,
            String password
    ) {


        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Invalid email or password"
                        )
                );



        if (!passwordEncoder.matches(
                password,
                user.getPassword()
        )) {

            throw new RuntimeException(
                    "Invalid email or password"
            );

        }



        String otp = otpService.generateOtp(email);



        emailService.sendOtpEmail(
                email,
                otp
        );



        return new LoginResponse(
                "OTP sent to your email",
                true
        );

    }





    public String verifyOtp(
            String email,
            String otp
    ) {


        boolean valid = otpService.verifyOtp(
                email,
                otp
        );



        if (!valid) {

            throw new RuntimeException(
                    "Invalid OTP"
            );

        }



        return jwtService.generateToken(email);

    }





    public void sendResetOtp(String email) {


        if (!userRepository.existsByEmail(email)) {

            throw new RuntimeException(
                    "Email not found"
            );

        }



        String otp = otpService.generateOtp(email);



        emailService.sendOtpEmail(
                email,
                otp
        );

    }





    public void resetPassword(
            String email,
            String otp,
            String newPassword
    ) {


        boolean valid = otpService.verifyOtp(
                email,
                otp
        );



        if (!valid) {

            throw new RuntimeException(
                    "Invalid OTP"
            );

        }



        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException(
                                "User not found"
                        )
                );



        validatePassword(newPassword);



        user.setPassword(
                passwordEncoder.encode(
                        newPassword
                )
        );



        userRepository.save(user);

    }





    private void validatePassword(String password) {


        if (password == null ||
                password.length() < 8) {

            throw new IllegalArgumentException(
                    "Password must be at least 8 characters"
            );

        }



        if (!password.matches(
                ".*[A-Z].*"
        )) {

            throw new IllegalArgumentException(
                    "Password must contain uppercase letter"
            );

        }



        if (!password.matches(
                ".*[a-z].*"
        )) {

            throw new IllegalArgumentException(
                    "Password must contain lowercase letter"
            );

        }



        if (!password.matches(
                ".*\\d.*"
        )) {

            throw new IllegalArgumentException(
                    "Password must contain number"
            );

        }



        if (!password.matches(
                ".*[@#$%^&+=!].*"
        )) {

            throw new IllegalArgumentException(
                    "Password must contain special character"
            );

        }



        if (password.contains(" ")) {

            throw new IllegalArgumentException(
                    "Password must not contain spaces"
            );

        }


    }


}
