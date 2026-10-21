package com.omanfootball.manager.service;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


import com.omanfootball.manager.entity.FantasyTeamPlayer;
import com.omanfootball.manager.repository.FantasyTeamPlayerRepository;



@Service
@RequiredArgsConstructor
public class FantasyTeamPlayerService {



    private final FantasyTeamPlayerRepository repository;

    private final FantasyScoringService fantasyScoringService;





    public List<FantasyTeamPlayer> getAll() {

        return repository.findAll();

    }





    public FantasyTeamPlayer getById(Long id) {


        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Fantasy team player not found with id: " + id
                        )
                );

    }





    @Transactional
    public FantasyTeamPlayer create(
            FantasyTeamPlayer fantasyTeamPlayer
    ) {

        FantasyTeamPlayer saved = repository.save(fantasyTeamPlayer);
        if (saved.getFantasyTeam() != null) {
            fantasyScoringService.refreshTeamPoints(saved.getFantasyTeam().getTeamId());
        }
        return saved;

    }





    @Transactional
    public FantasyTeamPlayer update(
            Long id,
            FantasyTeamPlayer newPlayer
    ) {


        FantasyTeamPlayer player = getById(id);
        Long oldTeamId = player.getFantasyTeam() == null ? null : player.getFantasyTeam().getTeamId();



        player.setPosition(
                newPlayer.getPosition()
        );



        player.setPrice(
                newPlayer.getPrice()
        );



        player.setOverallRating(
                newPlayer.getOverallRating()
        );



        player.setIsCaptain(
                newPlayer.getIsCaptain()
        );



        player.setFantasyTeam(
                newPlayer.getFantasyTeam()
        );



        player.setPlayer(
                newPlayer.getPlayer()
        );


        if (Boolean.TRUE.equals(player.getIsCaptain()) && player.getFantasyTeam() != null) {
            repository.findByFantasyTeam_TeamId(player.getFantasyTeam().getTeamId()).forEach(entry -> {
                if (!entry.getId().equals(player.getId())) {
                    entry.setIsCaptain(false);
                }
            });
            player.setIsCaptain(true);
        }



        FantasyTeamPlayer saved = repository.save(player);
        Long newTeamId = saved.getFantasyTeam() == null ? null : saved.getFantasyTeam().getTeamId();
        if (oldTeamId != null && !oldTeamId.equals(newTeamId)) {
            fantasyScoringService.refreshTeamPoints(oldTeamId);
        }
        if (newTeamId != null) {
            fantasyScoringService.refreshTeamPoints(newTeamId);
        }
        return saved;

    }





    @Transactional
    public void delete(Long id) {


        FantasyTeamPlayer player = getById(id);
        Long teamId = player.getFantasyTeam() == null ? null : player.getFantasyTeam().getTeamId();


        repository.delete(player);

        if (teamId != null) {
            fantasyScoringService.refreshTeamPoints(teamId);
        }


    }


}
