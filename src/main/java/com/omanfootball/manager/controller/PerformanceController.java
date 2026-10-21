package com.omanfootball.manager.controller;


import com.omanfootball.manager.entity.PlayerPerformance;
import com.omanfootball.manager.service.PerformanceService;
import com.omanfootball.manager.dto.NewPlayerPerformanceRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    public ResponseEntity<PlayerPerformance> record(@Valid @RequestBody PerformanceRecord request) {
        PlayerPerformance performance = new PlayerPerformance();
        performance.setGoals(request.goals());
        performance.setAssists(request.assists());
        performance.setMinutesPlayed(request.minutesPlayed());
        performance.setPassAccuracy(request.passAccuracy());
        performance.setYellowCards(request.yellowCards());
        performance.setRedCards(request.redCards());
        performance.setCleanSheet(request.cleanSheet());
        performance.setManOfMatch(request.manOfMatch());
        performance.setAiRating(request.aiRating());
        return ResponseEntity.ok(service.createForPlayerAndMatch(request.playerId(), request.matchId(), performance));
    }

    @PostMapping("/record-with-player")
    public ResponseEntity<PlayerPerformance> recordWithNewPlayer(@Valid @RequestBody NewPlayerPerformanceRequest request) {
        return ResponseEntity.ok(service.createForNewPlayer(request));
    }

    public record PerformanceRecord(
            @NotNull @Min(1) Long playerId,
            @NotNull @Min(1) Long matchId,
            @NotNull @Min(0) @Max(30) Integer goals,
            @NotNull @Min(0) @Max(30) Integer assists,
            @NotNull @Min(0) @Max(130) Integer minutesPlayed,
            @NotNull @DecimalMin("0.0") @DecimalMax("100.0") Double passAccuracy,
            @NotNull @Min(0) @Max(10) Integer yellowCards,
            @NotNull @Min(0) @Max(5) Integer redCards,
            @NotNull Boolean cleanSheet,
            @NotNull Boolean manOfMatch,
            @NotNull @DecimalMin("0.0") @DecimalMax("10.0") Double aiRating) {}





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
