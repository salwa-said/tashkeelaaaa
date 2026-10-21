package com.omanfootball.manager.controller;


import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


import com.omanfootball.manager.entity.AIRating;
import com.omanfootball.manager.service.AIRatingService;



@RestController
@RequestMapping("/ai-ratings")
@RequiredArgsConstructor
public class AIRatingController {



    private final AIRatingService service;





    @GetMapping("/all")
    public ResponseEntity<List<AIRating>> getAll() {



        return ResponseEntity.ok(
                service.getAllRatings()
        );



    }





    @GetMapping("/by-id/{id}")
    public ResponseEntity<AIRating> getById(

            @PathVariable Long id

    ) {



        return ResponseEntity.ok(
                service.getRatingById(id)
        );



    }





    @PostMapping("/create")
    public ResponseEntity<AIRating> create(

            @RequestBody AIRating rating

    ) {



        return ResponseEntity.ok(
                service.createRating(rating)
        );



    }





    @PutMapping("/update/{id}")
    public ResponseEntity<AIRating> update(

            @PathVariable Long id,

            @RequestBody AIRating rating

    ) {



        return ResponseEntity.ok(
                service.updateRating(id, rating)
        );



    }





    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(

            @PathVariable Long id

    ) {



        service.deleteRating(id);



        return ResponseEntity.ok(
                "AI rating deleted successfully"
        );



    }


}