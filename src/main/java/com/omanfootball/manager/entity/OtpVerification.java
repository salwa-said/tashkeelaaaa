package com.omanfootball.manager.entity;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;



@Entity
@Table(name = "otp_verifications")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpVerification {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    @Column(nullable = false)
    private String email;




    @Column(nullable = false)
    private String otp;




    private LocalDateTime createdAt;




    private LocalDateTime expiresAt;





    @PrePersist
    public void createTime() {


        createdAt = LocalDateTime.now();


        expiresAt = createdAt.plusMinutes(5);


    }



}