package com.omanfootball.manager.repository;


import com.omanfootball.manager.entity.MatchStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchStatisticsRepository extends JpaRepository<MatchStatistics, Long>{
    java.util.Optional<MatchStatistics> findByMatch_Id(Long matchId);
}
