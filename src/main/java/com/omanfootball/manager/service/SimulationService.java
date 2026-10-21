package com.omanfootball.manager.service;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


import com.omanfootball.manager.entity.WhatIfSimulation;
import com.omanfootball.manager.repository.WhatIfSimulationRepository;
import com.omanfootball.manager.repository.FantasyTeamRepository;
import com.omanfootball.manager.repository.FantasyTeamPlayerRepository;
import com.omanfootball.manager.repository.PlayerRepository;
import com.omanfootball.manager.entity.FantasyTeam;
import com.omanfootball.manager.entity.FantasyTeamPlayer;
import com.omanfootball.manager.entity.Player;



@Service
@RequiredArgsConstructor
public class SimulationService {



    private final WhatIfSimulationRepository repository;
    private final FantasyTeamRepository fantasyTeamRepository;
    private final FantasyTeamPlayerRepository teamPlayerRepository;
    private final PlayerRepository playerRepository;

    @Transactional
    public WhatIfSimulation simulateMine(String email, Long removePlayerId, Long addPlayerId, Long captainPlayerId, String formation) {
        FantasyTeam team = fantasyTeamRepository.findByUser_Email(email)
                .orElseThrow(() -> new IllegalArgumentException("Create a fantasy squad before running a simulation"));
        List<FantasyTeamPlayer> entries = teamPlayerRepository.findByFantasyTeam_TeamId(team.getTeamId());
        if (entries.isEmpty()) throw new IllegalArgumentException("Add players to your squad before running a simulation");
        List<Player> current = entries.stream().map(FantasyTeamPlayer::getPlayer).toList();
        List<Player> proposed = new java.util.ArrayList<>(current);
        StringBuilder changes = new StringBuilder();
        if (removePlayerId != null || addPlayerId != null) {
            if (removePlayerId == null || addPlayerId == null) throw new IllegalArgumentException("Choose both a player to remove and a replacement");
            int index = -1;
            for (int i = 0; i < proposed.size(); i++) if (proposed.get(i).getId().equals(removePlayerId)) index = i;
            if (index < 0) throw new IllegalArgumentException("The player to remove is not in your squad");
            Player replacement = playerRepository.findById(addPlayerId).orElseThrow(() -> new IllegalArgumentException("Replacement player not found"));
            if (proposed.stream().anyMatch(p -> p.getId().equals(addPlayerId))) throw new IllegalArgumentException("Replacement player is already in your squad");
            double availableBudget = (team.getBudget() == null ? 0.0 : team.getBudget())
                    + entries.stream().filter(e -> e.getPlayer().getId().equals(removePlayerId))
                    .mapToDouble(e -> e.getPrice() == null ? 0.0 : e.getPrice()).sum();
            if (replacement.getPrice() > availableBudget) throw new IllegalArgumentException("The replacement is over your available squad budget");
            proposed.set(index, replacement);
            changes.append("Replace ").append(current.get(index).getName()).append(" with ").append(replacement.getName()).append(". ");
        }
        double before = current.stream().mapToDouble(Player::getFantasyPoints).sum();
        double after = proposed.stream().mapToDouble(Player::getFantasyPoints).sum();
        Player existingCaptain = entries.stream().filter(e -> Boolean.TRUE.equals(e.getIsCaptain()))
                .map(FantasyTeamPlayer::getPlayer).findFirst().orElse(null);
        if (existingCaptain != null) before += existingCaptain.getFantasyPoints();
        if (captainPlayerId != null) {
            Player captain = proposed.stream().filter(p -> p.getId().equals(captainPlayerId)).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Captain must be in the proposed squad"));
            after += captain.getFantasyPoints();
            boolean wasCaptain = entries.stream().anyMatch(e -> Boolean.TRUE.equals(e.getIsCaptain()) && e.getPlayer().getId().equals(captainPlayerId));
            if (!wasCaptain) changes.append("Set ").append(captain.getName()).append(" as captain (captain points are doubled). ");
        }
        if (changes.isEmpty()) changes.append("No player or captain changes; baseline squad projection.");
        WhatIfSimulation simulation = new WhatIfSimulation();
        simulation.setUser(team.getUser());
        simulation.setOriginalTeam(current.stream().map(Player::getName).collect(java.util.stream.Collectors.joining(", ")));
        simulation.setChanges(changes.toString());
        simulation.setFormationChange(formation == null || formation.isBlank() ? "No formation change" : formation);
        simulation.setCaptainChange(captainPlayerId == null ? "Unchanged" : proposed.stream().filter(p -> p.getId().equals(captainPlayerId)).findFirst().orElseThrow().getName());
        simulation.setPredictedPoints(after);
        simulation.setExpectedDifference(after - before);
        return repository.save(simulation);
    }

    @Transactional(readOnly = true)
    public List<WhatIfSimulation> getMine(String email) {
        return fantasyTeamRepository.findByUser_Email(email)
                .map(team -> repository.findByUser_Id(team.getUser().getId()))
                .orElse(List.of());
    }





    public List<WhatIfSimulation> getAll() {

        return repository.findAll();

    }





    public WhatIfSimulation getById(Long id) {


        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Simulation not found with id: " + id
                        )
                );

    }





    @Transactional
    public WhatIfSimulation create(
            WhatIfSimulation simulation
    ) {

        return repository.save(simulation);

    }





    @Transactional
    public WhatIfSimulation update(
            Long id,
            WhatIfSimulation newSimulation
    ) {


        WhatIfSimulation simulation = getById(id);



        simulation.setOriginalTeam(
                newSimulation.getOriginalTeam()
        );



        simulation.setChanges(
                newSimulation.getChanges()
        );



        simulation.setFormationChange(
                newSimulation.getFormationChange()
        );



        simulation.setCaptainChange(
                newSimulation.getCaptainChange()
        );



        simulation.setPredictedPoints(
                newSimulation.getPredictedPoints()
        );



        simulation.setExpectedDifference(
                newSimulation.getExpectedDifference()
        );



        simulation.setUser(
                newSimulation.getUser()
        );



        return repository.save(simulation);

    }





    @Transactional
    public void delete(Long id) {


        WhatIfSimulation simulation = getById(id);



        repository.delete(simulation);


    }


}
