package com.omanfootball.manager.dto;

public record TeamMemberResponse(
        Long entryId,
        Long playerId,
        String name,
        String position,
        String clubName,
        Double price,
        Double rating,
        String image,
        String formationSlot,
        Integer fantasyPoints,
        Boolean captain
) {}
