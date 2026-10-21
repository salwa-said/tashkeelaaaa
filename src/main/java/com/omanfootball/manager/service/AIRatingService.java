package com.omanfootball.manager.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.omanfootball.manager.entity.AIRating;
import com.omanfootball.manager.repository.AIRatingRepository;


@Service
@RequiredArgsConstructor
public class AIRatingService {


    private final AIRatingRepository repository;



    public List<AIRating> getAllRatings() {

        return repository.findAll();

    }



    public AIRating getRatingById(Long id) {

        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "AI rating not found with id: " + id
                        )
                );

    }



    @Transactional
    public AIRating createRating(AIRating rating) {

        return repository.save(rating);

    }



    @Transactional
    public AIRating updateRating(
            Long id,
            AIRating updatedRating
    ) {


        AIRating existingRating = getRatingById(id);



        existingRating.setOverall(
                updatedRating.getOverall()
        );



        existingRating.setPassing(
                updatedRating.getPassing()
        );



        existingRating.setDefense(
                updatedRating.getDefense()
        );



        existingRating.setPhysical(
                updatedRating.getPhysical()
        );



        existingRating.setPredictionScore(
                updatedRating.getPredictionScore()
        );



        existingRating.setRecommendation(
                updatedRating.getRecommendation()
        );



        existingRating.setAnalysis(
                updatedRating.getAnalysis()
        );



        existingRating.setPlayer(
                updatedRating.getPlayer()
        );



        return repository.save(existingRating);

    }



    @Transactional
    public void deleteRating(Long id) {

        AIRating rating = getRatingById(id);

        repository.delete(rating);

    }


}