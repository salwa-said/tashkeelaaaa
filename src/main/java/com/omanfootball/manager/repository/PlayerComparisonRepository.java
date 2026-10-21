package com.omanfootball.manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


import com.omanfootball.manager.entity.PlayerComparison;



@Repository
public interface PlayerComparisonRepository
        extends JpaRepository<PlayerComparison, Long> {



    List<PlayerComparison> findByPlayerOne_Id(Long playerId);



    List<PlayerComparison> findByPlayerTwo_Id(Long playerId);



}