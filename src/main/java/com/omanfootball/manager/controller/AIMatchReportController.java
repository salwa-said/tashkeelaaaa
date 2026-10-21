package com.omanfootball.manager.controller;


import com.omanfootball.manager.entity.AIMatchReport;
import com.omanfootball.manager.service.AIMatchReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ai-match-reports")
@RequiredArgsConstructor
public class AIMatchReportController {

    private final AIMatchReportService service;

    @PostMapping("/generate/{matchId}")
    public ResponseEntity<AIMatchReport> generate(@PathVariable Long matchId) {
        return ResponseEntity.ok(service.generateForMatch(matchId));
    }





    @GetMapping("/all")
    public ResponseEntity<List<AIMatchReport>> getAll() {



        return ResponseEntity.ok(
                service.getAll()
        );



    }





    @GetMapping("/by-id/{id}")
    public ResponseEntity<AIMatchReport> getById(

            @PathVariable Long id

    ) {



        return ResponseEntity.ok(
                service.getReportById(id)
        );



    }





    @PostMapping("/create")
    public ResponseEntity<AIMatchReport> create(

            @RequestBody AIMatchReport report

    ) {



        return ResponseEntity.ok(
                service.generateReport(report)
        );



    }





    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(

            @PathVariable Long id

    ) {



        service.deleteReport(id);



        return ResponseEntity.ok(
                "AI report deleted successfully"
        );



    }

}
