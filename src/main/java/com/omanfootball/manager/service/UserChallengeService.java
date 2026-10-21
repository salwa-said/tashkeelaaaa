package com.omanfootball.manager.service;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;


import com.omanfootball.manager.entity.UserChallenge;
import com.omanfootball.manager.repository.UserChallengeRepository;



@Service
@RequiredArgsConstructor
public class UserChallengeService {



    private final UserChallengeRepository repository;





    public List<UserChallenge> getAll() {

        return repository.findAll();

    }





    public UserChallenge getById(Long id) {


        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "User challenge not found with id: " + id
                        )
                );

    }





    @Transactional
    public UserChallenge create(
            UserChallenge userChallenge
    ) {

        return repository.save(userChallenge);

    }





    @Transactional
    public UserChallenge update(
            Long id,
            UserChallenge newChallenge
    ) {


        UserChallenge challenge = getById(id);



        challenge.setEarnedPoints(
                newChallenge.getEarnedPoints()
        );



        challenge.setCompletedAt(
                newChallenge.getCompletedAt()
        );



        challenge.setUser(
                newChallenge.getUser()
        );



        challenge.setChallenge(
                newChallenge.getChallenge()
        );



        return repository.save(challenge);

    }





    @Transactional
    public UserChallenge completeChallenge(
            Long id,
            Integer earnedPoints
    ) {


        UserChallenge challenge = getById(id);



        challenge.setEarnedPoints(
                earnedPoints
        );



        challenge.setCompletedAt(
                LocalDateTime.now()
        );



        return repository.save(challenge);

    }





    @Transactional
    public void delete(Long id) {


        UserChallenge challenge = getById(id);



        repository.delete(challenge);


    }


}