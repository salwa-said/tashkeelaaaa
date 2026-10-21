package com.omanfootball.manager.repository;


import com.omanfootball.manager.entity.StartingXIRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StartingXIRecommendationRepository extends JpaRepository<StartingXIRecommendation, Long> {

    Optional<StartingXIRecommendation> findByMatch_Id(Long matchId);
}
