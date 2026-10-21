package com.omanfootball.manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


import com.omanfootball.manager.entity.RatingHistory;



@Repository
public interface RatingHistoryRepository
        extends JpaRepository<RatingHistory, Long> {



    List<RatingHistory> findByPlayer_Id(Long playerId);



}