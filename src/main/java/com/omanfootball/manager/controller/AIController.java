package com.omanfootball.manager.controller;


import com.omanfootball.manager.entity.AIFantasyPoints;
import com.omanfootball.manager.service.AIService;


import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIController {



    private final AIService service;





    @GetMapping("/player/{id}")
    public ResponseEntity<AIFantasyPoints> analyzePlayer(

            @PathVariable Long id,

            @RequestParam(required = false) Integer goals,

            @RequestParam(required = false) Integer assists,

            @RequestParam(required = false) Integer appearances,

            @RequestParam(required = false) Double price,

            @RequestParam(required = false) Double rating

    ) {



        AIFantasyPoints result = goals == null || assists == null || appearances == null || price == null || rating == null
                ? service.analyzePlayerFromHistory(id)
                : service.analyzePlayer(
                        id, goals, assists, appearances, price, rating
                );

        return ResponseEntity.ok(result);



    }


}
