package com.omanfootball.manager.service;


import com.omanfootball.manager.entity.Match;
import com.omanfootball.manager.entity.MatchStatistics;
import com.omanfootball.manager.repository.MatchRepository;
import com.omanfootball.manager.repository.MatchStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchStatisticsService {

    private final MatchStatisticsRepository matchStatisticsRepository;
    private final MatchRepository matchRepository;

    @Transactional
    public MatchStatistics saveForMatch(Long matchId, MatchStatistics incoming) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + matchId));
        MatchStatistics statistics = matchStatisticsRepository.findByMatch_Id(matchId)
                .orElseGet(MatchStatistics::new);
        statistics.setMatch(match);
        statistics.setPossessionHome(incoming.getPossessionHome());
        statistics.setPossessionAway(incoming.getPossessionAway());
        statistics.setShotsHome(incoming.getShotsHome());
        statistics.setShotsAway(incoming.getShotsAway());
        statistics.setShotsOnTargetHome(incoming.getShotsOnTargetHome());
        statistics.setShotsOnTargetAway(incoming.getShotsOnTargetAway());
        statistics.setCornersHome(incoming.getCornersHome());
        statistics.setCornersAway(incoming.getCornersAway());
        statistics.setFoulsHome(incoming.getFoulsHome());
        statistics.setFoulsAway(incoming.getFoulsAway());
        statistics.setYellowCardsHome(incoming.getYellowCardsHome());
        statistics.setYellowCardsAway(incoming.getYellowCardsAway());
        statistics.setRedCardsHome(incoming.getRedCardsHome());
        statistics.setRedCardsAway(incoming.getRedCardsAway());
        return matchStatisticsRepository.save(statistics);
    }





    public List<MatchStatistics> getAllStatistics() {

        return matchStatisticsRepository.findAll();

    }





    public MatchStatistics getStatisticsById(Long id) {


        return matchStatisticsRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Match statistics not found with id: " + id
                        )
                );

    }





    @Transactional
    public MatchStatistics createStatistics(
            MatchStatistics statistics
    ) {

        return matchStatisticsRepository.save(statistics);

    }





    @Transactional
    public MatchStatistics updateStatistics(
            Long id,
            MatchStatistics newStatistics
    ) {


        MatchStatistics statistics =
                getStatisticsById(id);




        statistics.setPossessionHome(
                newStatistics.getPossessionHome()
        );



        statistics.setPossessionAway(
                newStatistics.getPossessionAway()
        );



        statistics.setShotsHome(
                newStatistics.getShotsHome()
        );



        statistics.setShotsAway(
                newStatistics.getShotsAway()
        );



        statistics.setShotsOnTargetHome(
                newStatistics.getShotsOnTargetHome()
        );



        statistics.setShotsOnTargetAway(
                newStatistics.getShotsOnTargetAway()
        );



        statistics.setCornersHome(
                newStatistics.getCornersHome()
        );



        statistics.setCornersAway(
                newStatistics.getCornersAway()
        );



        statistics.setFoulsHome(
                newStatistics.getFoulsHome()
        );



        statistics.setFoulsAway(
                newStatistics.getFoulsAway()
        );



        statistics.setYellowCardsHome(
                newStatistics.getYellowCardsHome()
        );



        statistics.setYellowCardsAway(
                newStatistics.getYellowCardsAway()
        );



        statistics.setRedCardsHome(
                newStatistics.getRedCardsHome()
        );



        statistics.setRedCardsAway(
                newStatistics.getRedCardsAway()
        );



        statistics.setMatch(
                newStatistics.getMatch()
        );



        return matchStatisticsRepository.save(statistics);

    }





    @Transactional
    public void deleteStatistics(Long id) {


        MatchStatistics statistics =
                getStatisticsById(id);



        matchStatisticsRepository.delete(statistics);


    }
}
