package com.omanfootball.manager.service;


import com.omanfootball.manager.entity.OtpVerification;
import com.omanfootball.manager.repository.OtpRepository;


import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Random;



@Service
@RequiredArgsConstructor
public class OtpService {


    private final OtpRepository otpRepository;



    public String generateOtp(
            String email
    ){


        String otp =
                String.valueOf(
                        new Random()
                                .nextInt(900000)
                                +100000
                );



        OtpVerification verification =
                otpRepository
                        .findByEmail(email)
                        .orElse(new OtpVerification());



        verification.setEmail(email);

        verification.setOtp(otp);


        verification.setExpiryTime(
                LocalDateTime.now()
                        .plusMinutes(5)
        );



        otpRepository.save(
                verification
        );



        return otp;

    }




    public boolean verifyOtp(
            String email,
            String otp
    ){


        OtpVerification verification =
                otpRepository
                        .findByEmail(email)
                        .orElse(null);



        if(verification == null){
            return false;
        }



        if(!verification.getOtp()
                .equals(otp)){
            return false;
        }



        if(LocalDateTime.now()
                .isAfter(
                        verification.getExpiryTime()
                )){
            return false;
        }



        otpRepository.delete(
                verification
        );


        return true;

    }

}