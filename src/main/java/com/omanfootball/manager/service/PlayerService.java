package com.omanfootball.manager.service;


import com.omanfootball.manager.entity.Player;
import com.omanfootball.manager.exception.ResourceNotFoundException;
import com.omanfootball.manager.repository.PlayerRepository;


import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;



@Service
@RequiredArgsConstructor
public class PlayerService {



    private final PlayerRepository playerRepository;





    public List<Player> getAllPlayers() {

        return playerRepository.findAll();

    }





    public Player getPlayerById(Long id) {


        return playerRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Player not found with id: " + id
                        )
                );

    }





    @Transactional
    public Player createPlayer(Player player) {

        return playerRepository.save(player);

    }





    @Transactional
    public Player updatePlayer(
            Long id,
            Player newPlayer
    ) {


        Player player = getPlayerById(id);



        player.setName(
                newPlayer.getName()
        );



        player.setPosition(
                newPlayer.getPosition()
        );



        player.setAge(
                newPlayer.getAge()
        );



        player.setPrice(
                newPlayer.getPrice()
        );




        player.setOverallRating(
                newPlayer.getOverallRating()
        );



        player.setFantasyPoints(
                newPlayer.getFantasyPoints()
        );



        player.setNationality(
                newPlayer.getNationality()
        );



        player.setImage(
                newPlayer.getImage()
        );



        player.setClub(
                newPlayer.getClub()
        );



        return playerRepository.save(player);

    }





    @Transactional
    public void deletePlayer(Long id) {


        Player player = getPlayerById(id);


        playerRepository.delete(player);

    }




    public List<Player> getPlayersByClub(Long clubId) {

        return playerRepository.findByClub_Id(clubId);
    }





    public List<Player> getPlayersByPosition(String position) {

        return playerRepository.findByPosition(position);

    }



}