package com.omanfootball.manager.repository;


import com.omanfootball.manager.entity.PlayerPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerPerformanceRepository extends JpaRepository<PlayerPerformance, Long> {

    List<PlayerPerformance> findByPlayer_Id(Long playerId);



    List<PlayerPerformance> findByMatch_Id(Long matchId);

    boolean existsByPlayer_IdAndMatch_Id(Long playerId, Long matchId);
}
