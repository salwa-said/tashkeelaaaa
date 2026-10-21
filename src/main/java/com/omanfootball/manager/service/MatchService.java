package com.omanfootball.manager.service;


import com.omanfootball.manager.entity.Match;
import com.omanfootball.manager.exception.ResourceNotFoundException;
import com.omanfootball.manager.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final FantasyScoringService fantasyScoringService;





    public List<Match> getAllMatches() {

        return matchRepository.findAll();

    }





    public Match getMatchById(Long id) {


        return matchRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Match not found with id: " + id
                        )
                );

    }





    @Transactional
    public Match createMatch(Match match) {

        return matchRepository.save(match);

    }





    @Transactional
    public Match updateMatch(
            Long id,
            Match newMatch
    ) {


        Match match = getMatchById(id);



        match.setMatchDate(
                newMatch.getMatchDate()
        );



        match.setVenue(
                newMatch.getVenue()
        );



        match.setHomeScore(
                newMatch.getHomeScore()
        );



        match.setAwayScore(
                newMatch.getAwayScore()
        );



        match.setStatus(
                newMatch.getStatus()
        );



        match.setHomeClub(
                newMatch.getHomeClub()
        );



        match.setAwayClub(
                newMatch.getAwayClub()
        );



        Match saved = matchRepository.save(match);
        fantasyScoringService.refreshMatchPoints(saved.getId());
        return saved;


    }





    @Transactional
    public void deleteMatch(Long id) {


        Match match = getMatchById(id);


        matchRepository.delete(match);


    }

}
