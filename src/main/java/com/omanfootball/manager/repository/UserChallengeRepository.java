package com.omanfootball.manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


import com.omanfootball.manager.entity.UserChallenge;



@Repository
public interface UserChallengeRepository
        extends JpaRepository<UserChallenge, Long> {



    List<UserChallenge> findByUser_Id(Long userId);



    List<UserChallenge> findByChallenge_ChallengeId(Long challengeId);



}