package com.omanfootball.manager.service;


import com.omanfootball.manager.entity.AIFantasyPoints;
import com.omanfootball.manager.entity.Player;
import com.omanfootball.manager.repository.PlayerRepository;
import com.omanfootball.manager.repository.PlayerPerformanceRepository;
import com.omanfootball.manager.repository.AIFantasyPointsRepository;
import com.omanfootball.manager.entity.PlayerPerformance;
import com.omanfootball.manager.entity.AIRating;
import com.omanfootball.manager.entity.RatingHistory;
import com.omanfootball.manager.repository.AIRatingRepository;
import com.omanfootball.manager.repository.RatingHistoryRepository;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class AIService {


    private final PlayerRepository playerRepository;
    private final PlayerPerformanceRepository performanceRepository;
    private final AIFantasyPointsRepository forecastRepository;
    private final AIRatingRepository ratingRepository;
    private final RatingHistoryRepository ratingHistoryRepository;

    @Transactional
    public AIFantasyPoints analyzePlayerFromHistory(Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + playerId));
        var performances = performanceRepository.findByPlayer_Id(playerId);
        int goals = performances.stream().map(PlayerPerformance::getGoals).filter(java.util.Objects::nonNull).mapToInt(Integer::intValue).sum();
        int assists = performances.stream().map(PlayerPerformance::getAssists).filter(java.util.Objects::nonNull).mapToInt(Integer::intValue).sum();
        int appearances = (int) performances.stream().filter(p -> p.getMinutesPlayed() != null && p.getMinutesPlayed() > 0).count();
        AIFantasyPoints result = calculate(player, goals, assists, appearances, player.getPrice(), player.getOverallRating());
        result.setGoalsAnalyzed(goals);
        result.setAssistsAnalyzed(assists);
        result.setAppearancesAnalyzed(appearances);
        updateRating(player, performances, goals, assists, appearances, result);
        return forecastRepository.save(result);
    }



    public AIFantasyPoints analyzePlayer(
            Long playerId,
            int goals,
            int assists,
            int appearances,
            double price,
            double rating
    ) {



        Player player = playerRepository.findById(playerId)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Player not found with id: " + playerId
                        )
                );



        AIFantasyPoints ai = calculate(player, goals, assists, appearances, price, rating);
        updateRating(player, performanceRepository.findByPlayer_Id(playerId), goals, assists, appearances, ai);
        return forecastRepository.save(ai);
    }

    private void updateRating(Player player, java.util.List<PlayerPerformance> performances,
                              int goals, int assists, int appearances, AIFantasyPoints forecast) {
        AIRating rating = ratingRepository.findByPlayer_Id(player.getId()).orElseGet(AIRating::new);
        int oldRating = rating.getOverall() == null ? (int) Math.round(player.getOverallRating()) : rating.getOverall();
        double form = appearances == 0 ? 0.0 : (goals * 3.0 + assists * 2.0) / appearances;
        int overall = appearances == 0 ? oldRating
                : (int) Math.round(Math.max(0, Math.min(100, player.getOverallRating() * 0.8 + form * 0.2)));
        double passing = performances.stream().map(PlayerPerformance::getPassAccuracy).filter(java.util.Objects::nonNull)
                .mapToDouble(Double::doubleValue).average().orElse(0.0);
        double cleanSheets = performances.stream().filter(p -> Boolean.TRUE.equals(p.getCleanSheet())).count();
        double defense = appearances == 0 ? 0 : Math.max(0, Math.min(100, cleanSheets * 100.0 / appearances));
        double physical = performances.stream().map(PlayerPerformance::getMinutesPlayed).filter(java.util.Objects::nonNull)
                .mapToDouble(m -> Math.min(100, m * 100.0 / 90.0)).average().orElse(0.0);
        rating.setPlayer(player);
        rating.setOverall(overall);
        rating.setPassing((int) Math.round(passing));
        rating.setDefense((int) Math.round(defense));
        rating.setPhysical((int) Math.round(physical));
        rating.setPredictionScore(forecast.getAiRating());
        rating.setRecommendation(forecast.getRecommendation());
        rating.setAnalysis("Calculated from saved player rating and " + appearances + " recorded appearances; form is weighted by goals and assists.");
        ratingRepository.save(rating);
        forecast.setPlayerRating(overall);
        forecast.setPlayerRatingAnalysis(rating.getAnalysis());
        if (oldRating != overall) {
            RatingHistory history = new RatingHistory();
            history.setPlayer(player);
            history.setOldRating(oldRating);
            history.setNewRating(overall);
            history.setReason("Updated from saved match performances");
            ratingHistoryRepository.save(history);
        }
    }

    private AIFantasyPoints calculate(Player player, int goals, int assists, int appearances, double price, double rating) {
        AIFantasyPoints ai = new AIFantasyPoints();



        ai.setPlayer(player);
        ai.setGoalsAnalyzed(goals);
        ai.setAssistsAnalyzed(assists);
        ai.setAppearancesAnalyzed(appearances);



        double predictedPoints =
                (goals * 5.0)
                        + (assists * 3.0)
                        + appearances
                        + (rating / 10.0);



        ai.setPredictedPoints(predictedPoints);



        double performanceScore =
                (rating * 0.6)
                        + ((goals + assists) * 5.0);



        double valueScore =
                price > 0
                        ? predictedPoints / price
                        : 0.0;



        double aiScore =
                performanceScore + valueScore;



        ai.setAiRating(aiScore);



        if (aiScore >= 70) {


            ai.setRecommendation("BUY");

            ai.setExplanation(
                    "Player has high performance and good fantasy value"
            );


        } else if (aiScore >= 50) {


            ai.setRecommendation("CONSIDER");

            ai.setExplanation(
                    "Player has average performance and reasonable fantasy value"
            );


        } else {


            ai.setRecommendation("AVOID");

            ai.setExplanation(
                    "Player performance or fantasy value is low"
            );

        }



        return ai;

    }

}
