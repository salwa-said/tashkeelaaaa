package com.omanfootball.manager.service;


import com.omanfootball.manager.entity.AIFantasyPoints;
import com.omanfootball.manager.repository.AIFantasyPointsRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
@RequiredArgsConstructor
public class AIFantasyPointsService {



    private final AIFantasyPointsRepository repository;



    public List<AIFantasyPoints> getAll() {

        return repository.findAll();

    }



    public AIFantasyPoints getById(Long id) {

        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "AI Fantasy Points not found with id: " + id
                        )
                );

    }



    @Transactional
    public AIFantasyPoints create(AIFantasyPoints aiFantasyPoints) {

        return repository.save(aiFantasyPoints);

    }



    @Transactional
    public AIFantasyPoints update(
            Long id,
            AIFantasyPoints newPoints
    ) {


        AIFantasyPoints points = getById(id);



        points.setPredictedPoints(
                newPoints.getPredictedPoints()
        );



        points.setAiRating(
                newPoints.getAiRating()
        );



        points.setRecommendation(
                newPoints.getRecommendation()
        );



        points.setExplanation(
                newPoints.getExplanation()
        );



        points.setPlayer(
                newPoints.getPlayer()
        );



        points.setMatch(
                newPoints.getMatch()
        );



        return repository.save(points);

    }



    @Transactional
    public void delete(Long id) {

        AIFantasyPoints points = getById(id);

        repository.delete(points);

    }


}