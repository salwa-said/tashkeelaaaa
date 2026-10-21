package com.omanfootball.manager.service;


import com.omanfootball.manager.entity.Match;
import com.omanfootball.manager.entity.StartingXIRecommendation;
import com.omanfootball.manager.repository.MatchRepository;
import com.omanfootball.manager.repository.StartingXIRecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StartingXIService {

    private final StartingXIRecommendationRepository repository;
    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;

    @Transactional
    public StartingXIRecommendation generateForMatch(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + matchId));
        String homeName = match.getHomeClub() == null ? "Home side" : match.getHomeClub().getClubName();
        String awayName = match.getAwayClub() == null ? "Away side" : match.getAwayClub().getClubName();
        var homeSquad = match.getHomeClub() == null ? java.util.List.<Player>of() : topEleven(match.getHomeClub().getId());
        var awaySquad = match.getAwayClub() == null ? java.util.List.<Player>of() : topEleven(match.getAwayClub().getId());
        if (homeSquad.isEmpty() || awaySquad.isEmpty()) throw new IllegalArgumentException("Both clubs need registered players before a lineup can be recommended");
        String lineup = homeName + " XI\n" + formatLineup(homeSquad) + "\n\n" + awayName + " XI\n" + formatLineup(awaySquad);
        String reason = "For each club, selected the top 11 registered players by overall rating with a small fantasy-points form adjustment. This data-based heuristic does not enforce positional balance or injury availability.";
        StartingXIRecommendation recommendation = repository.findByMatch_Id(matchId).orElseGet(StartingXIRecommendation::new);
        recommendation.setMatch(match);
        recommendation.setFormation("4-3-3");
        recommendation.setRecommendedPlayers(lineup);
        recommendation.setAiReason(reason);
        recommendation.setConfidenceScore(Math.min(0.95, 0.45 + Math.min(homeSquad.size(), awaySquad.size()) * 0.045));
        return repository.save(recommendation);
    }

    private java.util.List<Player> topEleven(Long clubId) {
        return playerRepository.findByClub_Id(clubId).stream()
                .sorted(java.util.Comparator.comparingDouble((Player p) -> p.getOverallRating() + p.getFantasyPoints() * 0.15).reversed())
                .limit(11).toList();
    }

    private String formatLineup(java.util.List<Player> players) {
        return players.stream().map(p -> p.getPosition() + " · " + p.getName()
                        + " (rating " + Math.round(p.getOverallRating()) + ")")
                .collect(java.util.stream.Collectors.joining("\n"));
    }





    public List<StartingXIRecommendation> getAll() {

        return repository.findAll();

    }





    public StartingXIRecommendation getById(Long id) {


        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Starting XI recommendation not found with id: " + id
                        )
                );

    }





    @Transactional
    public StartingXIRecommendation create(
            StartingXIRecommendation recommendation
    ) {

        return repository.save(recommendation);

    }





    @Transactional
    public StartingXIRecommendation update(
            Long id,
            StartingXIRecommendation newRecommendation
    ) {


        StartingXIRecommendation recommendation = getById(id);



        recommendation.setFormation(
                newRecommendation.getFormation()
        );



        recommendation.setRecommendedPlayers(
                newRecommendation.getRecommendedPlayers()
        );



        recommendation.setAiReason(
                newRecommendation.getAiReason()
        );



        recommendation.setConfidenceScore(
                newRecommendation.getConfidenceScore()
        );



        recommendation.setMatch(
                newRecommendation.getMatch()
        );



        return repository.save(recommendation);

    }





    @Transactional
    public void delete(Long id) {


        StartingXIRecommendation recommendation = getById(id);



        repository.delete(recommendation);


    }

}
