package com.omanfootball.manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


import com.omanfootball.manager.entity.FantasyTeam;



@Repository
public interface FantasyTeamRepository
        extends JpaRepository<FantasyTeam, Long> {



    Optional<FantasyTeam> findByUser_Id(Long userId);

    Optional<FantasyTeam> findByUser_Email(String email);



}
