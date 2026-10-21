package com.omanfootball.manager.controller;


import com.omanfootball.manager.entity.StartingXIRecommendation;
import com.omanfootball.manager.service.StartingXIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/starting-xi")
@RequiredArgsConstructor
public class StartingXIController {

    private final StartingXIService service;

    @PostMapping("/generate/{matchId}")
    public ResponseEntity<StartingXIRecommendation> generate(@PathVariable Long matchId) {
        return ResponseEntity.ok(service.generateForMatch(matchId));
    }





    @GetMapping("/all")
    public ResponseEntity<List<StartingXIRecommendation>> getAll() {



        return ResponseEntity.ok(
                service.getAll()
        );



    }





    @GetMapping("/by-id/{id}")
    public ResponseEntity<StartingXIRecommendation> getById(

            @PathVariable Long id

    ) {



        return ResponseEntity.ok(
                service.getById(id)
        );



    }





    @PostMapping("/create")
    public ResponseEntity<StartingXIRecommendation> create(

            @RequestBody StartingXIRecommendation recommendation

    ) {



        return ResponseEntity.ok(
                service.create(recommendation)
        );



    }





    @PutMapping("/update/{id}")
    public ResponseEntity<StartingXIRecommendation> update(

            @PathVariable Long id,

            @RequestBody StartingXIRecommendation recommendation

    ) {



        return ResponseEntity.ok(
                service.update(id, recommendation)
        );



    }





    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(

            @PathVariable Long id

    ) {



        service.delete(id);



        return ResponseEntity.ok(
                "Starting XI recommendation deleted successfully"
        );



    }
}
