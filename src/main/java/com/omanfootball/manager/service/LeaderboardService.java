package com.omanfootball.manager.service;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.util.Comparator;
import java.util.List;


import com.omanfootball.manager.entity.FantasyTeam;
import com.omanfootball.manager.repository.FantasyTeamRepository;



@Service
@RequiredArgsConstructor
public class LeaderboardService {



    private final FantasyTeamRepository fantasyTeamRepository;





    public List<FantasyTeam> getLeaderboard() {


        return fantasyTeamRepository
                .findAll()
                .stream()
                .sorted(
                        Comparator.comparing(
                                FantasyTeam::getTotalPoints,
                                Comparator.nullsLast(
                                        Comparator.reverseOrder()
                                )
                        )
                )
                .toList();


    }


}