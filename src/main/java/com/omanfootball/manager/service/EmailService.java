package com.omanfootball.manager.service;


import lombok.RequiredArgsConstructor;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class EmailService {



    private final JavaMailSender mailSender;





    public void sendOtpEmail(
            String email,
            String otp
    ) {


        SimpleMailMessage message = new SimpleMailMessage();



        message.setTo(email);



        message.setSubject(
                "TASHKEELA Verification Code"
        );



        message.setText(
                "Welcome to TASHKEELA!\n\n"
                        + "Your verification code is:\n\n"
                        + otp
                        + "\n\n"
                        + "This code expires after 5 minutes.\n\n"
                        + "TASHKEELA\n"
                        + "Oman Fantasy Football League"
        );



        mailSender.send(message);

    }





    public void sendEmail(
            String email,
            String subject,
            String messageText
    ) {


        SimpleMailMessage message = new SimpleMailMessage();



        message.setTo(email);



        message.setSubject(subject);



        message.setText(messageText);



        mailSender.send(message);

    }


}