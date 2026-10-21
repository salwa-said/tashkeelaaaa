package com.omanfootball.manager.controller;


import com.omanfootball.manager.entity.Match;
import com.omanfootball.manager.service.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService service;





    @GetMapping("/all")
    public ResponseEntity<List<Match>> getAll() {



        return ResponseEntity.ok(
                service.getAllMatches()
        );



    }





    @GetMapping("/by-id/{id}")
    public ResponseEntity<Match> getById(

            @PathVariable Long id

    ) {



        return ResponseEntity.ok(
                service.getMatchById(id)
        );



    }





    @PostMapping("/create")
    public ResponseEntity<Match> create(

            @Valid @RequestBody Match match

    ) {



        return ResponseEntity.ok(
                service.createMatch(match)
        );



    }





    @PutMapping("/update/{id}")
    public ResponseEntity<Match> update(

            @PathVariable Long id,

            @Valid @RequestBody Match match

    ) {



        return ResponseEntity.ok(
                service.updateMatch(id, match)
        );



    }





    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(

            @PathVariable Long id

    ) {



        service.deleteMatch(id);



        return ResponseEntity.ok(
                "Match deleted successfully"
        );



    }

}
