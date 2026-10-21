package com.omanfootball.manager.service;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


import com.omanfootball.manager.entity.RatingHistory;
import com.omanfootball.manager.repository.RatingHistoryRepository;



@Service
@RequiredArgsConstructor
public class RatingHistoryService {



    private final RatingHistoryRepository repository;

    public List<RatingHistory> getByPlayerId(Long playerId) {
        return repository.findByPlayer_Id(playerId);
    }





    public List<RatingHistory> getAll() {

        return repository.findAll();

    }





    public RatingHistory getById(Long id) {


        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Rating history not found with id: " + id
                        )
                );

    }





    @Transactional
    public RatingHistory create(
            RatingHistory history
    ) {

        return repository.save(history);

    }





    @Transactional
    public RatingHistory update(
            Long id,
            RatingHistory newHistory
    ) {


        RatingHistory history = getById(id);



        history.setOldRating(
                newHistory.getOldRating()
        );



        history.setNewRating(
                newHistory.getNewRating()
        );



        history.setReason(
                newHistory.getReason()
        );



        history.setChangeDate(
                newHistory.getChangeDate()
        );



        history.setPlayer(
                newHistory.getPlayer()
        );



        return repository.save(history);

    }





    @Transactional
    public void delete(Long id) {


        RatingHistory history = getById(id);


        repository.delete(history);


    }


}
