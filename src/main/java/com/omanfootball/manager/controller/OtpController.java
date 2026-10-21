package com.omanfootball.manager.controller;


import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.omanfootball.manager.dto.OtpRequest;
import com.omanfootball.manager.service.AuthService;



@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OtpController {



    private final AuthService authService;





    // =========================
    // VERIFY OTP
    // CREATE JWT TOKEN
    // =========================

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(

            @RequestBody OtpRequest request

    ){


        String token =

                authService.verifyOtp(

                        request.getEmail(),

                        request.getOtp()

                );



        return ResponseEntity.ok(
                token
        );


    }


}