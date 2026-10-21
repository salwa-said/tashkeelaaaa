package com.omanfootball.manager.service;


import com.omanfootball.manager.entity.Match;
import com.omanfootball.manager.entity.PlayerPerformance;
import com.omanfootball.manager.repository.MatchRepository;
import com.omanfootball.manager.repository.PlayerPerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final PlayerPerformanceRepository repository;
    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;

    @Transactional
    public PlayerPerformance createForPlayerAndMatch(Long playerId, Long matchId, PlayerPerformance performance) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Match not found"));
        if (repository.existsByPlayer_IdAndMatch_Id(playerId, matchId)) {
            throw new IllegalArgumentException("A performance record already exists for this player and match");
        }
        performance.setPlayer(player);
        performance.setMatch(match);
        return repository.save(performance);
    }





    public List<PlayerPerformance> getAll() {

        return repository.findAll();

    }





    public PlayerPerformance getById(Long id) {


        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Performance not found with id: " + id
                        )
                );

    }





    @Transactional
    public PlayerPerformance create(
            PlayerPerformance performance
    ) {

        return repository.save(performance);

    }





    @Transactional
    public PlayerPerformance update(
            Long id,
            PlayerPerformance newPerformance
    ) {


        PlayerPerformance performance = getById(id);



        performance.setGoals(
                newPerformance.getGoals()
        );



        performance.setAssists(
                newPerformance.getAssists()
        );



        performance.setYellowCards(
                newPerformance.getYellowCards()
        );



        performance.setRedCards(
                newPerformance.getRedCards()
        );



        performance.setPassAccuracy(
                newPerformance.getPassAccuracy()
        );



        performance.setMinutesPlayed(
                newPerformance.getMinutesPlayed()
        );



        performance.setCleanSheet(
                newPerformance.getCleanSheet()
        );



        performance.setManOfMatch(
                newPerformance.getManOfMatch()
        );



        performance.setMatch(
                newPerformance.getMatch()
        );



        performance.setPlayer(
                newPerformance.getPlayer()
        );



        return repository.save(performance);

    }





    @Transactional
    public void delete(Long id) {


        PlayerPerformance performance = getById(id);



        repository.delete(performance);


    }
}
