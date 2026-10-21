package com.omanfootball.manager.service;

import com.omanfootball.manager.entity.FantasyTeam;
import com.omanfootball.manager.entity.FantasyTeamPlayer;
import com.omanfootball.manager.entity.Match;
import com.omanfootball.manager.entity.Player;
import com.omanfootball.manager.entity.PlayerPerformance;
import com.omanfootball.manager.dto.PlayerPointsBreakdown;
import com.omanfootball.manager.repository.FantasyTeamPlayerRepository;
import com.omanfootball.manager.repository.FantasyTeamRepository;
import com.omanfootball.manager.repository.PlayerPerformanceRepository;
import com.omanfootball.manager.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FantasyScoringService {

    private final PointsCalculator pointsCalculator;
    private final PlayerPerformanceRepository performanceRepository;
    private final PlayerRepository playerRepository;
    private final FantasyTeamPlayerRepository teamPlayerRepository;
    private final FantasyTeamRepository teamRepository;

    @Transactional
    public void refreshPlayerPoints(Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));
        int total = performanceRepository.findByPlayer_Id(playerId).stream()
                .mapToInt(performance -> pointsFor(player, performance))
                .sum();
        player.setFantasyPoints(total);
        playerRepository.save(player);

        Set<Long> teamIds = new LinkedHashSet<>();
        teamPlayerRepository.findByPlayer_Id(playerId).forEach(entry -> {
            if (entry.getFantasyTeam() != null) {
                teamIds.add(entry.getFantasyTeam().getTeamId());
            }
        });
        teamIds.forEach(this::refreshTeamPoints);
    }

    @Transactional
    public void refreshMatchPoints(Long matchId) {
        Set<Long> playerIds = new LinkedHashSet<>();
        performanceRepository.findByMatch_Id(matchId).forEach(performance -> {
            if (performance.getPlayer() != null) {
                playerIds.add(performance.getPlayer().getId());
            }
        });
        playerIds.forEach(this::refreshPlayerPoints);
    }

    @Transactional(readOnly = true)
    public PlayerPointsBreakdown getPlayerPointsBreakdown(Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));
        List<PlayerPointsBreakdown.MatchBreakdown> matches = performanceRepository.findByPlayer_Id(playerId)
                .stream()
                .map(performance -> {
                    Match match = performance.getMatch();
                    boolean teamWon = teamWon(player, match);
                    PointsCalculator.PointsBreakdown points = pointsCalculator.calculateBreakdown(
                            value(performance.getGoals()), value(performance.getAssists()),
                            value(performance.getYellowCards()), value(performance.getRedCards()),
                            Boolean.TRUE.equals(performance.getCleanSheet()), teamWon,
                            Boolean.TRUE.equals(performance.getManOfMatch()),
                            performance.getAiRating() == null ? 0.0 : performance.getAiRating());
                    String matchName = match == null ? "Match details unavailable"
                            : (match.getHomeClub() == null ? "Home" : match.getHomeClub().getClubName())
                            + " vs " + (match.getAwayClub() == null ? "Away" : match.getAwayClub().getClubName());
                    return new PlayerPointsBreakdown.MatchBreakdown(matchName,
                            match == null ? null : match.getMatchDate(), value(performance.getGoals()),
                            value(performance.getAssists()), value(performance.getYellowCards()),
                            value(performance.getRedCards()), Boolean.TRUE.equals(performance.getCleanSheet()),
                            teamWon, Boolean.TRUE.equals(performance.getManOfMatch()),
                            performance.getAiRating() == null ? 0.0 : performance.getAiRating(),
                            points.goalsPoints(), points.assistsPoints(), points.cleanSheetPoints(),
                            points.winPoints(), points.manOfMatchPoints(), points.aiRatingPoints(),
                            points.yellowCardPoints(), points.redCardPoints(), points.totalPoints());
                }).toList();
        int total = matches.stream().mapToInt(PlayerPointsBreakdown.MatchBreakdown::totalPoints).sum();
        return new PlayerPointsBreakdown(player.getId(), player.getName(), total, matches);
    }

    @Transactional
    public void refreshTeamPoints(Long teamId) {
        FantasyTeam team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Fantasy team not found"));
        List<FantasyTeamPlayer> entries = teamPlayerRepository.findByFantasyTeam_TeamId(teamId);
        int total = entries.stream()
                .mapToInt(entry -> {
                    int points = entry.getPlayer() == null ? 0 : entry.getPlayer().getFantasyPoints();
                    return Boolean.TRUE.equals(entry.getIsCaptain()) ? points * 2 : points;
                })
                .sum();
        team.setTotalPoints(total);
        teamRepository.save(team);
    }

    private int pointsFor(Player player, PlayerPerformance performance) {
        return performancePoints(player, performance).totalPoints();
    }

    private PointsCalculator.PointsBreakdown performancePoints(Player player, PlayerPerformance performance) {
        Match match = performance.getMatch();
        return pointsCalculator.calculateBreakdown(
                value(performance.getGoals()), value(performance.getAssists()),
                value(performance.getYellowCards()), value(performance.getRedCards()),
                Boolean.TRUE.equals(performance.getCleanSheet()), teamWon(player, match),
                Boolean.TRUE.equals(performance.getManOfMatch()),
                performance.getAiRating() == null ? 0.0 : performance.getAiRating());
    }

    private boolean teamWon(Player player, Match match) {
        boolean teamWin = false;
        if (match != null && player.getClub() != null) {
            Long clubId = player.getClub().getId();
            if (match.getHomeClub() != null && clubId.equals(match.getHomeClub().getId())) {
                teamWin = match.getHomeScore() > match.getAwayScore();
            } else if (match.getAwayClub() != null && clubId.equals(match.getAwayClub().getId())) {
                teamWin = match.getAwayScore() > match.getHomeScore();
            }
        }
        return teamWin;
    }

    private int value(Integer value) {
        return value == null ? 0 : value;
    }
}
