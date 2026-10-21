package com.omanfootball.manager.repository;


import com.omanfootball.manager.entity.Player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface PlayerRepository
        extends JpaRepository<Player, Long> {


    List<Player> findByClub_Id(Long clubId);


    List<Player> findByPosition(String position);


}