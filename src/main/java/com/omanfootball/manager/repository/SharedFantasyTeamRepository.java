package com.omanfootball.manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


import com.omanfootball.manager.entity.SharedFantasyTeam;



@Repository
public interface SharedFantasyTeamRepository
        extends JpaRepository<SharedFantasyTeam, Long> {



    Optional<SharedFantasyTeam> findByShareCode(String shareCode);

    Optional<SharedFantasyTeam> findByUser_Id(Long userId);



}
