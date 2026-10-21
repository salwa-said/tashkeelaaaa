package com.omanfootball.manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


import com.omanfootball.manager.entity.ClubDashboard;



@Repository
public interface ClubDashboardRepository
        extends JpaRepository<ClubDashboard, Long> {



    Optional<ClubDashboard> findByClub_Id(Long clubId);

}