package com.omanfootball.manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


import com.omanfootball.manager.entity.AIRating;



@Repository
public interface AIRatingRepository
        extends JpaRepository<AIRating, Long> {



    Optional<AIRating> findByPlayer_Id(Long playerId);



}