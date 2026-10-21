package com.omanfootball.manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


import com.omanfootball.manager.entity.FantasyPoints;



@Repository
public interface FantasyPointsRepository
        extends JpaRepository<FantasyPoints, Long> {



    List<FantasyPoints> findByPlayer_Id(Long playerId);



    List<FantasyPoints> findByFantasyTeam_TeamId(Long teamId);



    List<FantasyPoints> findByMatch_Id(Long matchId);



}