package com.omanfootball.manager.dto;

import java.util.List;

public record SharedTeamView(
        String teamName,
        String formation,
        Integer totalPoints,
        Double overallRating,
        List<TeamMemberResponse> players
) {}
