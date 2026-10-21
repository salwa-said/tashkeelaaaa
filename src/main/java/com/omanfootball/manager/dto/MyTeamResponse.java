package com.omanfootball.manager.dto;

import java.util.List;

public record MyTeamResponse(
        Long teamId,
        String teamName,
        Double budget,
        Integer totalPoints,
        Double overallRating,
        String formation,
        List<TeamMemberResponse> players
) {}
