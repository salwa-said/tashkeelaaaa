package com.omanfootball.manager.controller;

import com.omanfootball.manager.entity.FantasyPoints;
import com.omanfootball.manager.service.FantasyPointsService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fantasy-points")
@RequiredArgsConstructor
public class FantasyPointsController {

    private final FantasyPointsService service;

    @GetMapping("/all")
    public ResponseEntity<List<FantasyPoints>> getAll() {
        return ResponseEntity.ok(
                service.getAllPoints()
        );
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<FantasyPoints> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                service.getPointsById(id)
        );
    }

    @PostMapping("/create")
    public ResponseEntity<FantasyPoints> create(
            @RequestBody FantasyPoints points
    ) {
        return ResponseEntity.ok(
                service.createPoints(points)
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FantasyPoints> update(
            @PathVariable Long id,
            @RequestBody FantasyPoints points
    ) {
        return ResponseEntity.ok(
                service.updatePoints(id, points)
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id
    ) {
        service.deletePoints(id);

        return ResponseEntity.ok(
                "Fantasy points deleted successfully"
        );
    }
}