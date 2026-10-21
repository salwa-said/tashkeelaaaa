package com.omanfootball.manager.service;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


import com.omanfootball.manager.entity.FantasyChallenge;
import com.omanfootball.manager.repository.FantasyChallengeRepository;



@Service
@RequiredArgsConstructor
public class ChallengeService {



    private final FantasyChallengeRepository repository;





    public List<FantasyChallenge> getAll() {

        return repository.findAll();

    }





    public FantasyChallenge getById(Long id) {

        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Challenge not found with id: " + id
                        )
                );

    }





    @Transactional
    public FantasyChallenge create(FantasyChallenge challenge) {

        return repository.save(challenge);

    }





    @Transactional
    public FantasyChallenge update(
            Long id,
            FantasyChallenge updatedChallenge
    ) {


        FantasyChallenge existingChallenge = getById(id);



        existingChallenge.setTitle(
                updatedChallenge.getTitle()
        );



        existingChallenge.setDescription(
                updatedChallenge.getDescription()
        );



        existingChallenge.setChallengeType(
                updatedChallenge.getChallengeType()
        );



        existingChallenge.setRewardPoints(
                updatedChallenge.getRewardPoints()
        );



        existingChallenge.setStartDate(
                updatedChallenge.getStartDate()
        );



        existingChallenge.setEndDate(
                updatedChallenge.getEndDate()
        );



        return repository.save(existingChallenge);

    }





    @Transactional
    public void delete(Long id) {


        FantasyChallenge challenge = getById(id);


        repository.delete(challenge);


    }


}