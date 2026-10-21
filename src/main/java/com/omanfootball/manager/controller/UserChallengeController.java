package com.omanfootball.manager.controller;


import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;


import com.omanfootball.manager.entity.UserChallenge;
import com.omanfootball.manager.service.UserChallengeService;



@RestController
@RequestMapping("/user-challenges")
@RequiredArgsConstructor
public class UserChallengeController {



    private final UserChallengeService service;





    @GetMapping("/all")
    public ResponseEntity<List<UserChallenge>> getAll() {



        return ResponseEntity.ok(
                service.getAll()
        );



    }





    @GetMapping("/by-id/{id}")
    public ResponseEntity<UserChallenge> getById(

            @PathVariable Long id

    ) {



        return ResponseEntity.ok(
                service.getById(id)
        );



    }





    @PostMapping("/create")
    public ResponseEntity<UserChallengeActionResponse> create(

            @RequestBody UserChallenge challenge

    ) {



        UserChallenge created = service.create(challenge);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new UserChallengeActionResponse("User challenge created successfully", created)
        );



    }





    @PutMapping("/update/{id}")
    public ResponseEntity<UserChallengeActionResponse> update(

            @PathVariable Long id,

            @RequestBody UserChallenge challenge

    ) {



        UserChallenge updated = service.update(id, challenge);
        return ResponseEntity.ok(
                new UserChallengeActionResponse("User challenge updated successfully", updated)
        );



    }





    @PutMapping("/complete/{id}")
    public ResponseEntity<UserChallengeActionResponse> complete(

            @PathVariable Long id,

            @RequestParam Integer earnedPoints

    ) {



        UserChallenge completed = service.completeChallenge(id, earnedPoints);
        return ResponseEntity.ok(
                new UserChallengeActionResponse("User challenge completed successfully", completed)
        );



    }





    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(

            @PathVariable Long id

    ) {



        service.delete(id);



        return ResponseEntity.ok(
                "User challenge deleted successfully"
        );



    }

    public record UserChallengeActionResponse(String message, UserChallenge userChallenge) {}


}
