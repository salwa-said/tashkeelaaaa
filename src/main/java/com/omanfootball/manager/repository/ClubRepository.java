package com.omanfootball.manager.repository;


import com.omanfootball.manager.entity.Club;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ClubRepository
        extends JpaRepository<Club, Long> {



}