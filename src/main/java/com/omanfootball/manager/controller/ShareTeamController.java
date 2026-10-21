package com.omanfootball.manager.controller;


import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


import com.omanfootball.manager.entity.SharedFantasyTeam;
import com.omanfootball.manager.service.ShareTeamService;
import com.omanfootball.manager.dto.SharedTeamView;
import com.omanfootball.manager.dto.ShareTeamResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



@RestController
@RequestMapping("/shared-teams")
@RequiredArgsConstructor
public class ShareTeamController {



    private final ShareTeamService service;

    @PostMapping("/mine")
    public ResponseEntity<ShareTeamResponse> shareMine(Authentication authentication) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        SharedFantasyTeam shared = service.shareMine(authentication.getName(),
                baseUrl + "/shared-team.html?code=PLACEHOLDER");
        ShareTeamResponse response = new ShareTeamResponse();
        response.setShareCode(shared.getShareCode());
        response.setShareUrl(shared.getShareUrl());
        response.setMessage("Squad share link is ready");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-code/{shareCode}")
    public ResponseEntity<SharedTeamView> getPublicTeam(@PathVariable String shareCode) {
        return ResponseEntity.ok(service.getPublicTeam(shareCode));
    }





    @GetMapping("/all")
    public ResponseEntity<List<SharedFantasyTeam>> getAll() {



        return ResponseEntity.ok(
                service.getAll()
        );



    }





    @GetMapping("/by-id/{id}")
    public ResponseEntity<SharedFantasyTeam> getById(

            @PathVariable Long id

    ) {



        return ResponseEntity.ok(
                service.getById(id)
        );



    }





    @PostMapping("/create")
    public ResponseEntity<SharedFantasyTeam> create(

            @RequestBody SharedFantasyTeam sharedTeam

    ) {



        return ResponseEntity.ok(
                service.create(sharedTeam)
        );



    }





    @PutMapping("/update/{id}")
    public ResponseEntity<SharedFantasyTeam> update(

            @PathVariable Long id,

            @RequestBody SharedFantasyTeam sharedTeam

    ) {



        return ResponseEntity.ok(
                service.update(id, sharedTeam)
        );



    }





    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(

            @PathVariable Long id

    ) {



        service.delete(id);



        return ResponseEntity.ok(
                "Shared team deleted successfully"
        );



    }


}
