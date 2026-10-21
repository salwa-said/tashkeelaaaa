package com.omanfootball.manager.controller;


import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import java.util.List;


import com.omanfootball.manager.entity.Player;
import com.omanfootball.manager.service.PlayerService;



@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {



    private final PlayerService service;





    @GetMapping("/all")
    public ResponseEntity<List<Player>> getAll() {



        return ResponseEntity.ok(
                service.getAllPlayers()
        );



    }





    @GetMapping("/by-id/{id}")
    public ResponseEntity<Player> getById(

            @PathVariable Long id

    ) {



        return ResponseEntity.ok(
                service.getPlayerById(id)
        );



    }





    @PostMapping("/create")
    public ResponseEntity<Player> create(

            @Valid @RequestBody Player player

    ) {



        return ResponseEntity.ok(
                service.createPlayer(player)
        );



    }





    @PutMapping("/update/{id}")
    public ResponseEntity<Player> update(

            @PathVariable Long id,

            @Valid @RequestBody Player player

    ) {



        return ResponseEntity.ok(
                service.updatePlayer(id, player)
        );



    }





    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(

            @PathVariable Long id

    ) {



        service.deletePlayer(id);



        return ResponseEntity.ok(
                "Player deleted successfully"
        );



    }


}
