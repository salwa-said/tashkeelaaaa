package com.omanfootball.manager.controller;


import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


import com.omanfootball.manager.entity.FantasyChallenge;
import com.omanfootball.manager.service.ChallengeService;



@RestController
@RequestMapping("/challenges")
@RequiredArgsConstructor
public class ChallengeController {



    private final ChallengeService service;





    @GetMapping("/all")
    public ResponseEntity<List<FantasyChallenge>> getAll() {



        return ResponseEntity.ok(
                service.getAll()
        );



    }





    @GetMapping("/by-id/{id}")
    public ResponseEntity<FantasyChallenge> getById(

            @PathVariable Long id

    ) {



        return ResponseEntity.ok(
                service.getById(id)
        );



    }





    @PostMapping("/create")
    public ResponseEntity<FantasyChallenge> create(

            @RequestBody FantasyChallenge challenge

    ) {



        return ResponseEntity.ok(
                service.create(challenge)
        );



    }





    @PutMapping("/update/{id}")
    public ResponseEntity<FantasyChallenge> update(

            @PathVariable Long id,

            @RequestBody FantasyChallenge challenge

    ) {



        return ResponseEntity.ok(
                service.update(id, challenge)
        );



    }





    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(

            @PathVariable Long id

    ) {



        service.delete(id);



        return ResponseEntity.ok(
                "Challenge deleted successfully"
        );



    }


}