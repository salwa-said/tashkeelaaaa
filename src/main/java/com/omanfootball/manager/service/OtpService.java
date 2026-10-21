package com.omanfootball.manager.service;


import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;



@Service
public class OtpService {



    private final Map<String, String> otpStorage =
            new HashMap<>();



    private final Random random =
            new Random();





    public String generateOtp(String email) {



        String otp =
                String.valueOf(
                        100000 + random.nextInt(900000)
                );



        otpStorage.put(
                email,
                otp
        );



        System.out.println(
                "OTP for "
                        + email
                        + " : "
                        + otp
        );



        return otp;

    }





    public boolean verifyOtp(
            String email,
            String otp
    ) {



        String savedOtp =
                otpStorage.get(email);



        if (savedOtp == null) {

            return false;

        }





        if (savedOtp.equals(otp)) {


            otpStorage.remove(email);


            return true;

        }





        return false;

    }


}