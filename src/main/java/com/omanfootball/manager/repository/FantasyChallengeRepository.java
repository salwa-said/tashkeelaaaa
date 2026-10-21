package com.omanfootball.manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.omanfootball.manager.entity.FantasyChallenge;



@Repository
public interface FantasyChallengeRepository
        extends JpaRepository<FantasyChallenge, Long> {


}