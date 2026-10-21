package com.omanfootball.manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


import com.omanfootball.manager.entity.FantasyTeamPlayer;



@Repository
public interface FantasyTeamPlayerRepository
        extends JpaRepository<FantasyTeamPlayer, Long> {



    List<FantasyTeamPlayer> findByFantasyTeam_TeamId(Long teamId);



    boolean existsByFantasyTeam_TeamIdAndPlayer_Id(
            Long teamId,
            Long playerId
    );



}