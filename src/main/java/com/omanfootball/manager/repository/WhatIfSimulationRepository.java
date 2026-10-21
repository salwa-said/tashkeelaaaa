package com.omanfootball.manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


import com.omanfootball.manager.entity.WhatIfSimulation;



@Repository
public interface WhatIfSimulationRepository
        extends JpaRepository<WhatIfSimulation, Long> {



    List<WhatIfSimulation> findByUser_Id(Long userId);



}