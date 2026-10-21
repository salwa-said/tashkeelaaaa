package com.omanfootball.manager.controller;


import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


import com.omanfootball.manager.entity.FantasyTeam;
import com.omanfootball.manager.service.LeaderboardService;
import com.omanfootball.manager.dto.LeaderboardEntryResponse;



@RestController
@RequestMapping("/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {



    private final LeaderboardService service;





    @GetMapping("/all")
    public ResponseEntity<List<LeaderboardEntryResponse>> getLeaderboard() {
        List<FantasyTeam> teams = service.getLeaderboard();
        List<LeaderboardEntryResponse> response = java.util.stream.IntStream.range(0, teams.size())
                .mapToObj(index -> {
                    FantasyTeam team = teams.get(index);
                    return new LeaderboardEntryResponse(index + 1,
                            team.getUser() == null ? "Manager" : team.getUser().getFullName(),
                            team.getTeamName(), team.getTotalPoints(), team.getOverallRating());
                }).toList();
        return ResponseEntity.ok(response);



    }


}
