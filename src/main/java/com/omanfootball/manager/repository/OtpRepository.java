package com.omanfootball.manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.omanfootball.manager.entity.OtpVerification;



@Repository
public interface OtpRepository
        extends JpaRepository<OtpVerification, Long> {



    OtpVerification findByEmail(
            String email
    );



    void deleteByEmail(
            String email
    );



}