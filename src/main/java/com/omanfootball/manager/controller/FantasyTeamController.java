package com.omanfootball.manager.controller;


import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;


import com.omanfootball.manager.entity.FantasyTeam;
import com.omanfootball.manager.service.FantasyTeamService;
import com.omanfootball.manager.dto.MyTeamResponse;



@RestController
@RequestMapping("/fantasy-teams")
@RequiredArgsConstructor
public class FantasyTeamController {



    private final FantasyTeamService service;

    @GetMapping("/mine")
    public ResponseEntity<MyTeamResponse> getMine(Authentication authentication) {
        return ResponseEntity.ok(service.getMine(authentication.getName()));
    }

    @PostMapping("/mine")
    public ResponseEntity<MyTeamResponse> createMine(Authentication authentication,
                                                     @RequestBody java.util.Map<String, String> request) {
        return ResponseEntity.ok(service.createMine(authentication.getName(), request.get("teamName"), request.get("formation")));
    }

    @PostMapping("/mine/players/{playerId}")
    public ResponseEntity<MyTeamResponse> addMinePlayer(Authentication authentication, @PathVariable Long playerId,
                                                        @RequestParam(required = false) String slot) {
        return ResponseEntity.ok(service.addPlayerToMine(authentication.getName(), playerId, slot));
    }

    @PutMapping("/mine/formation")
    public ResponseEntity<MyTeamResponse> changeMineFormation(Authentication authentication,
                                                              @RequestBody java.util.Map<String, String> request) {
        return ResponseEntity.ok(service.changeFormation(authentication.getName(), request.get("formation")));
    }

    @DeleteMapping("/mine/players/{entryId}")
    public ResponseEntity<MyTeamResponse> removeMinePlayer(Authentication authentication, @PathVariable Long entryId) {
        return ResponseEntity.ok(service.removePlayerFromMine(authentication.getName(), entryId));
    }





    @GetMapping("/all")
    public ResponseEntity<List<FantasyTeam>> getAll() {



        return ResponseEntity.ok(
                service.getAllTeams()
        );



    }





    @GetMapping("/by-id/{id}")
    public ResponseEntity<FantasyTeam> getById(

            @PathVariable Long id

    ) {



        return ResponseEntity.ok(
                service.getTeamById(id)
        );



    }





    @PostMapping("/create")
    public ResponseEntity<FantasyTeam> create(

            @RequestBody FantasyTeam team

    ) {



        return ResponseEntity.ok(
                service.createTeam(team)
        );



    }





    @PutMapping("/update/{id}")
    public ResponseEntity<FantasyTeam> update(

            @PathVariable Long id,

            @RequestBody FantasyTeam team

    ) {



        return ResponseEntity.ok(
                service.updateTeam(id, team)
        );



    }





    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(

            @PathVariable Long id

    ) {



        service.deleteTeam(id);



        return ResponseEntity.ok(
                "Fantasy team deleted successfully"
        );



    }


}
