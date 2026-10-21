package com.omanfootball.manager.controller;

import java.util.Map;

import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import com.omanfootball.manager.dto.LoginRequest;
import com.omanfootball.manager.dto.LoginResponse;
import com.omanfootball.manager.dto.OtpRequest;
import com.omanfootball.manager.dto.RegisterRequest;

import com.omanfootball.manager.service.AuthService;



@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {
    private final AuthService authService;





    // =========================
    // REGISTER
    // =========================

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(

            @Valid @RequestBody RegisterRequest request

    ){


        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Account created successfully"));


    }





    // =========================
    // LOGIN
    // EMAIL + PASSWORD
    // SEND OTP
    // =========================

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(

            @RequestBody LoginRequest request

    ){



        LoginResponse response =

                authService.login(

                        request.getEmail(),

                        request.getPassword()

                );



        return ResponseEntity.ok(
                response
        );



    }











    // =========================
    // FORGOT PASSWORD
    // SEND OTP
    // =========================

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(

            @RequestBody OtpRequest request

    ){



        authService.sendResetOtp(

                request.getEmail()

        );



        return ResponseEntity.ok(
                "OTP sent"
        );



    }





    // =========================
    // RESET PASSWORD
    // =========================

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(

            @RequestBody OtpRequest request

    ){



        authService.resetPassword(

                request.getEmail(),

                request.getOtp(),

                request.getPassword()

        );



        return ResponseEntity.ok(
                "Password changed successfully"
        );



    }



}
