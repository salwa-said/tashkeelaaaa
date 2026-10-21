package com.omanfootball.manager.repository;


import com.omanfootball.manager.entity.AIFantasyPoints;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface AIFantasyPointsRepository
        extends JpaRepository<AIFantasyPoints, Long> {



    List<AIFantasyPoints> findByPlayer_Id(Long playerId);



    List<AIFantasyPoints> findByMatch_Id(Long matchId);



}