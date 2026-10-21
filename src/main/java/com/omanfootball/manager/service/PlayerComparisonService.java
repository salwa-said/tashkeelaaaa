package com.omanfootball.manager.service;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


import com.omanfootball.manager.entity.PlayerComparison;
import com.omanfootball.manager.repository.PlayerComparisonRepository;
import com.omanfootball.manager.repository.PlayerRepository;
import com.omanfootball.manager.repository.PlayerPerformanceRepository;
import com.omanfootball.manager.entity.Player;
import com.omanfootball.manager.entity.PlayerPerformance;



@Service
@RequiredArgsConstructor
public class PlayerComparisonService {



    private final PlayerComparisonRepository repository;
    private final PlayerRepository playerRepository;
    private final PlayerPerformanceRepository performanceRepository;

    @Transactional
    public PlayerComparison generate(Long playerOneId, Long playerTwoId) {
        if (playerOneId.equals(playerTwoId)) throw new IllegalArgumentException("Choose two different players");
        Player one = playerRepository.findById(playerOneId).orElseThrow(() -> new RuntimeException("First player not found"));
        Player two = playerRepository.findById(playerTwoId).orElseThrow(() -> new RuntimeException("Second player not found"));
        int goalsOne = sum(performanceRepository.findByPlayer_Id(playerOneId), true);
        int goalsTwo = sum(performanceRepository.findByPlayer_Id(playerTwoId), true);
        int assistsOne = sum(performanceRepository.findByPlayer_Id(playerOneId), false);
        int assistsTwo = sum(performanceRepository.findByPlayer_Id(playerTwoId), false);
        PlayerComparison comparison = new PlayerComparison();
        comparison.setPlayerOne(one);
        comparison.setPlayerTwo(two);
        comparison.setGoalsDifference(goalsOne - goalsTwo);
        comparison.setAssistsDifference(assistsOne - assistsTwo);
        comparison.setRatingDifference(one.getOverallRating() - two.getOverallRating());
        comparison.setFantasyPointsDifference((double) one.getFantasyPoints() - two.getFantasyPoints());
        String leader = one.getOverallRating() >= two.getOverallRating() ? one.getName() : two.getName();
        comparison.setAiExplanation(one.getName() + " vs " + two.getName() + ": recorded goals " + goalsOne + "-" + goalsTwo
                + ", assists " + assistsOne + "-" + assistsTwo + ", overall ratings "
                + Math.round(one.getOverallRating()) + "-" + Math.round(two.getOverallRating())
                + ". " + leader + " currently leads on overall rating. This comparison uses saved records, not a trained AI model.");
        return repository.save(comparison);
    }

    private int sum(List<PlayerPerformance> rows, boolean goals) {
        return rows.stream().mapToInt(row -> {
            Integer value = goals ? row.getGoals() : row.getAssists();
            return value == null ? 0 : value;
        }).sum();
    }





    public List<PlayerComparison> getAll() {

        return repository.findAll();

    }





    public PlayerComparison getById(Long id) {

        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Comparison not found with id: " + id
                        )
                );

    }





    @Transactional
    public PlayerComparison create(
            PlayerComparison comparison
    ) {

        return repository.save(comparison);

    }





    @Transactional
    public PlayerComparison update(
            Long id,
            PlayerComparison newComparison
    ) {


        PlayerComparison comparison = getById(id);



        comparison.setGoalsDifference(
                newComparison.getGoalsDifference()
        );



        comparison.setAssistsDifference(
                newComparison.getAssistsDifference()
        );



        comparison.setRatingDifference(
                newComparison.getRatingDifference()
        );



        comparison.setFantasyPointsDifference(
                newComparison.getFantasyPointsDifference()
        );



        comparison.setAiExplanation(
                newComparison.getAiExplanation()
        );



        comparison.setPlayerOne(
                newComparison.getPlayerOne()
        );



        comparison.setPlayerTwo(
                newComparison.getPlayerTwo()
        );



        return repository.save(comparison);

    }





    @Transactional
    public void delete(Long id) {


        PlayerComparison comparison = getById(id);


        repository.delete(comparison);


    }


}
