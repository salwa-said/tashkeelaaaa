package com.omanfootball.manager.controller;


import com.omanfootball.manager.entity.PlayerPerformance;
import com.omanfootball.manager.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/performances")
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceService service;

    @PostMapping("/record")
    public ResponseEntity<PlayerPerformance> record(@RequestBody PerformanceRecord request) {
        PlayerPerformance performance = new PlayerPerformance();
        performance.setGoals(request.goals());
        performance.setAssists(request.assists());
        performance.setMinutesPlayed(request.minutesPlayed());
        performance.setPassAccuracy(request.passAccuracy());
        performance.setYellowCards(request.yellowCards());
        performance.setRedCards(request.redCards());
        performance.setCleanSheet(request.cleanSheet());
        performance.setManOfMatch(request.manOfMatch());
        return ResponseEntity.ok(service.createForPlayerAndMatch(request.playerId(), request.matchId(), performance));
    }

    public record PerformanceRecord(Long playerId, Long matchId, Integer goals, Integer assists,
                                    Integer minutesPlayed, Double passAccuracy, Integer yellowCards,
                                    Integer redCards, Boolean cleanSheet, Boolean manOfMatch) {}





    @GetMapping("/all")
    public ResponseEntity<List<PlayerPerformance>> getAll() {



        return ResponseEntity.ok(
                service.getAll()
        );



    }





    @GetMapping("/by-id/{id}")
    public ResponseEntity<PlayerPerformance> getById(

            @PathVariable Long id

    ) {



        return ResponseEntity.ok(
                service.getById(id)
        );



    }





    @PostMapping("/create")
    public ResponseEntity<PlayerPerformance> create(

            @RequestBody PlayerPerformance performance

    ) {



        return ResponseEntity.ok(
                service.create(performance)
        );



    }





    @PutMapping("/update/{id}")
    public ResponseEntity<PlayerPerformance> update(

            @PathVariable Long id,

            @RequestBody PlayerPerformance performance

    ) {



        return ResponseEntity.ok(
                service.update(id, performance)
        );



    }





    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(

            @PathVariable Long id

    ) {



        service.delete(id);



        return ResponseEntity.ok(
                "Performance deleted successfully"
        );



    }
}
