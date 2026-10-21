package com.omanfootball.manager.service;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


import com.omanfootball.manager.entity.FantasyPoints;
import com.omanfootball.manager.repository.FantasyPointsRepository;



@Service
@RequiredArgsConstructor
public class FantasyPointsService {



    private final FantasyPointsRepository fantasyPointsRepository;





    public List<FantasyPoints> getAllPoints() {

        return fantasyPointsRepository.findAll();

    }





    public FantasyPoints getPointsById(Long id) {


        return fantasyPointsRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Fantasy points not found with id: " + id
                        )
                );

    }





    @Transactional
    public FantasyPoints createPoints(FantasyPoints points) {

        return fantasyPointsRepository.save(points);

    }





    @Transactional
    public FantasyPoints updatePoints(
            Long id,
            FantasyPoints updatedPoints
    ) {


        FantasyPoints existingPoints = getPointsById(id);



        existingPoints.setPoints(
                updatedPoints.getPoints()
        );



        existingPoints.setMultiplier(
                updatedPoints.getMultiplier()
        );



        existingPoints.setExplanation(
                updatedPoints.getExplanation()
        );



        existingPoints.setMatch(
                updatedPoints.getMatch()
        );



        existingPoints.setPlayer(
                updatedPoints.getPlayer()
        );



        existingPoints.setFantasyTeam(
                updatedPoints.getFantasyTeam()
        );



        return fantasyPointsRepository.save(existingPoints);

    }





    @Transactional
    public void deletePoints(Long id) {


        FantasyPoints points = getPointsById(id);



        fantasyPointsRepository.delete(points);


    }


}