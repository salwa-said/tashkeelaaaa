package com.omanfootball.manager.controller;


import com.omanfootball.manager.entity.MatchStatistics;
import com.omanfootball.manager.service.MatchStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/match-statistics")
@RequiredArgsConstructor
public class MatchStatisticsController {

    private final MatchStatisticsService service;

    @PutMapping({"/for-match/{matchId}", "/for-match/update/{matchId}"})
    public ResponseEntity<MatchStatistics> saveForMatch(
            @PathVariable Long matchId,
            @RequestBody MatchStatistics statistics
    ) {
        return ResponseEntity.ok(service.saveForMatch(matchId, statistics));
    }





    @GetMapping("/all")
    public ResponseEntity<List<MatchStatistics>> getAll() {



        return ResponseEntity.ok(
                service.getAllStatistics()
        );



    }





    @GetMapping("/by-id/{id}")
    public ResponseEntity<MatchStatistics> getById(

            @PathVariable Long id

    ) {



        return ResponseEntity.ok(
                service.getStatisticsById(id)
        );



    }





    @PostMapping("/create")
    public ResponseEntity<MatchStatistics> create(

            @RequestBody MatchStatistics statistics

    ) {



        return ResponseEntity.ok(
                service.createStatistics(statistics)
        );



    }





    @PutMapping("/update/{id}")
    public ResponseEntity<MatchStatistics> update(

            @PathVariable Long id,

            @RequestBody MatchStatistics statistics

    ) {



        return ResponseEntity.ok(
                service.updateStatistics(id, statistics)
        );



    }





    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(

            @PathVariable Long id

    ) {



        service.deleteStatistics(id);



        return ResponseEntity.ok(
                "Statistics deleted successfully"
        );



    }
}
