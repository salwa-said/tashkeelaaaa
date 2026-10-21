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

        return repository.save(fantasyTeamPlayer);

    }





    @Transactional
    public FantasyTeamPlayer update(
            Long id,
            FantasyTeamPlayer newPlayer
    ) {


        FantasyTeamPlayer player = getById(id);



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



        return repository.save(player);

    }





    @Transactional
    public void delete(Long id) {


        FantasyTeamPlayer player = getById(id);


        repository.delete(player);


    }


}