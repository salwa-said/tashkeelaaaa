package com.omanfootball.manager.service;


import com.omanfootball.manager.entity.AIMatchReport;
import com.omanfootball.manager.entity.Match;
import com.omanfootball.manager.entity.PlayerPerformance;
import com.omanfootball.manager.repository.AIMatchReportRepository;
import com.omanfootball.manager.repository.MatchRepository;
import com.omanfootball.manager.repository.MatchStatisticsRepository;
import com.omanfootball.manager.repository.PlayerPerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AIMatchReportService {

    private final AIMatchReportRepository repository;
    private final MatchRepository matchRepository;
    private final MatchStatisticsRepository statisticsRepository;
    private final PlayerPerformanceRepository performanceRepository;

    @Transactional
    public AIMatchReport generateForMatch(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + matchId));
        if (!"COMPLETED".equalsIgnoreCase(match.getStatus())) {
            throw new IllegalArgumentException("Match reports can only be generated for completed matches");
        }
        var stats = statisticsRepository.findByMatch_Id(matchId).orElse(null);
        var performances = performanceRepository.findByMatch_Id(matchId);
        var standout = performances.stream()
                .max(java.util.Comparator.comparingInt((PlayerPerformance p) -> (p.getGoals() == null ? 0 : p.getGoals()) * 3
                        + (p.getAssists() == null ? 0 : p.getAssists()) * 2
                        + (Boolean.TRUE.equals(p.getManOfMatch()) ? 3 : 0)))
                .orElse(null);
        String home = match.getHomeClub() == null ? "Home side" : match.getHomeClub().getClubName();
        String away = match.getAwayClub() == null ? "Away side" : match.getAwayClub().getClubName();
        String score = match.getHomeScore() + "-" + match.getAwayScore();
        String outcome = match.getHomeScore() == match.getAwayScore() ? "The match finished level"
                : match.getHomeScore() > match.getAwayScore() ? home + " won" : away + " won";
        String keyPlayer = standout == null || standout.getPlayer() == null ? "No standout player recorded"
                : standout.getPlayer().getName();
        String summary = home + " " + score + " " + away + ". " + outcome + ".";
        StringBuilder analysis = new StringBuilder(summary);
        if (stats != null) {
            analysis.append(" Possession was ").append(value(stats.getPossessionHome())).append("%-")
                    .append(value(stats.getPossessionAway())).append("%; shots were ")
                    .append(value(stats.getShotsHome())).append("-").append(value(stats.getShotsAway()))
                    .append(" (on target ").append(value(stats.getShotsOnTargetHome())).append("-")
                    .append(value(stats.getShotsOnTargetAway())).append(").");
        } else {
            analysis.append(" Team match statistics have not been entered, so tactical detail is limited.");
        }
        analysis.append(standout == null ? " No player performance records were entered for this match."
                : " Standout player: " + keyPlayer + " (" + value(standout.getGoals()) + " goals, "
                + value(standout.getAssists()) + " assists).");
        AIMatchReport report = repository.findByMatch_Id(matchId).orElseGet(AIMatchReport::new);
        report.setMatch(match);
        report.setSummary(summary);
        report.setKeyPlayer(keyPlayer);
        report.setAiAnalysis(analysis.toString());
        report.setConfidenceScore(stats == null ? 0.55 : (performances.isEmpty() ? 0.75 : 0.9));
        return repository.save(report);
    }

    private int value(Integer value) { return value == null ? 0 : value; }



    public List<AIMatchReport> getAll() {

        return repository.findAll();

    }



    public AIMatchReport getReportById(Long id) {

        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "AI Match Report not found with id: " + id
                        )
                );

    }



    @Transactional
    public AIMatchReport generateReport(AIMatchReport report) {

        return repository.save(report);

    }



    @Transactional
    public void deleteReport(Long id) {

        AIMatchReport report = getReportById(id);

        repository.delete(report);

    }

}
