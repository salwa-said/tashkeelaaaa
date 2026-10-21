package com.omanfootball.manager.service;


import com.omanfootball.manager.entity.Match;
import com.omanfootball.manager.entity.Player;
import com.omanfootball.manager.entity.PlayerPerformance;
import com.omanfootball.manager.entity.Club;
import com.omanfootball.manager.dto.NewPlayerPerformanceRequest;
import com.omanfootball.manager.dto.PlayerRequest;
import com.omanfootball.manager.repository.ClubRepository;
import com.omanfootball.manager.repository.MatchRepository;
import com.omanfootball.manager.repository.PlayerPerformanceRepository;
import com.omanfootball.manager.repository.PlayerRepository;
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
    private final ClubRepository clubRepository;
    private final FantasyScoringService fantasyScoringService;

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
        PlayerPerformance saved = repository.save(performance);
        fantasyScoringService.refreshPlayerPoints(playerId);
        return saved;
    }

    @Transactional
    public PlayerPerformance createForNewPlayer(NewPlayerPerformanceRequest request) {
        if (request.player() == null || request.performance() == null) {
            throw new IllegalArgumentException("Player details and performance stats are required");
        }
        PlayerRequest details = request.player();
        Match match = matchRepository.findById(request.matchId())
                .orElseThrow(() -> new IllegalArgumentException("Match not found"));
        Player player = new Player();
        player.setName(details.getName());
        player.setPosition(details.getPosition());
        player.setAge(details.getAge());
        player.setPrice(details.getPrice());
        player.setOverallRating(details.getOverallRating());
        player.setNationality(details.getNationality());
        player.setImage(details.getImage());
        player.setFantasyPoints(0);
        if (details.getClubId() != null) {
            Club club = clubRepository.findById(details.getClubId())
                    .orElseThrow(() -> new IllegalArgumentException("Club not found"));
            player.setClub(club);
        }
        player = playerRepository.save(player);

        NewPlayerPerformanceRequest.PerformanceData stats = request.performance();
        PlayerPerformance performance = new PlayerPerformance();
        performance.setPlayer(player);
        performance.setMatch(match);
        performance.setGoals(stats.goals());
        performance.setAssists(stats.assists());
        performance.setMinutesPlayed(stats.minutesPlayed());
        performance.setPassAccuracy(stats.passAccuracy());
        performance.setYellowCards(stats.yellowCards());
        performance.setRedCards(stats.redCards());
        performance.setCleanSheet(stats.cleanSheet());
        performance.setManOfMatch(stats.manOfMatch());
        performance.setAiRating(stats.aiRating());
        PlayerPerformance saved = repository.save(performance);
        fantasyScoringService.refreshPlayerPoints(player.getId());
        return saved;
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
        PlayerPerformance saved = repository.save(performance);
        if (saved.getPlayer() != null) {
            fantasyScoringService.refreshPlayerPoints(saved.getPlayer().getId());
        }
        return saved;

    }





    @Transactional
    public PlayerPerformance update(
            Long id,
            PlayerPerformance newPerformance
    ) {


        PlayerPerformance performance = getById(id);
        Long oldPlayerId = performance.getPlayer() == null ? null : performance.getPlayer().getId();


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

        performance.setAiRating(newPerformance.getAiRating());



        if (newPerformance.getMatch() != null) {
            performance.setMatch(newPerformance.getMatch());
        }
        if (newPerformance.getPlayer() != null) {
            performance.setPlayer(newPerformance.getPlayer());
        }



        PlayerPerformance saved = repository.save(performance);
        Long newPlayerId = saved.getPlayer() == null ? null : saved.getPlayer().getId();
        if (oldPlayerId != null && !oldPlayerId.equals(newPlayerId)) {
            fantasyScoringService.refreshPlayerPoints(oldPlayerId);
        }
        if (newPlayerId != null) {
            fantasyScoringService.refreshPlayerPoints(newPlayerId);
        }
        return saved;

    }





    @Transactional
    public void delete(Long id) {


        PlayerPerformance performance = getById(id);
        Long playerId = performance.getPlayer() == null ? null : performance.getPlayer().getId();


        repository.delete(performance);
        if (playerId != null) {
            fantasyScoringService.refreshPlayerPoints(playerId);
        }

    }
}
