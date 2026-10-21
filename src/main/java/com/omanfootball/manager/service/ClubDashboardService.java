package com.omanfootball.manager.service;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


import com.omanfootball.manager.entity.ClubDashboard;
import com.omanfootball.manager.repository.ClubDashboardRepository;



@Service
@RequiredArgsConstructor
public class ClubDashboardService {



    private final ClubDashboardRepository repository;





    public List<ClubDashboard> getAll() {

        return repository.findAll();

    }





    public ClubDashboard getById(Long id) {

        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Club dashboard not found with id: " + id
                        )
                );

    }





    @Transactional
    public ClubDashboard create(ClubDashboard dashboard) {

        return repository.save(dashboard);

    }





    @Transactional
    public ClubDashboard update(
            Long id,
            ClubDashboard updatedDashboard
    ) {


        ClubDashboard existingDashboard = getById(id);



        existingDashboard.setSquadStrength(
                updatedDashboard.getSquadStrength()
        );



        existingDashboard.setTeamForm(
                updatedDashboard.getTeamForm()
        );



        existingDashboard.setTopPlayer(
                updatedDashboard.getTopPlayer()
        );



        existingDashboard.setWeaknesses(
                updatedDashboard.getWeaknesses()
        );



        existingDashboard.setAverageRating(
                updatedDashboard.getAverageRating()
        );



        existingDashboard.setTotalPlayers(
                updatedDashboard.getTotalPlayers()
        );



        existingDashboard.setClub(
                updatedDashboard.getClub()
        );



        return repository.save(existingDashboard);

    }





    @Transactional
    public void delete(Long id) {


        ClubDashboard dashboard = getById(id);


        repository.delete(dashboard);


    }


}