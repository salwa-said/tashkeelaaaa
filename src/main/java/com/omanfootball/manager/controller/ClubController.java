package com.omanfootball.manager.controller;


import com.omanfootball.manager.entity.Club;
import com.omanfootball.manager.service.ClubService;


import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import java.util.List;



@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {



    private final ClubService service;





    @GetMapping("/all")
    public ResponseEntity<List<Club>> getAll() {



        return ResponseEntity.ok(
                service.getAllClubs()
        );



    }





    @GetMapping("/by-id/{id}")
    public ResponseEntity<Club> getById(

            @PathVariable Long id

    ) {



        return ResponseEntity.ok(
                service.getClubById(id)
        );



    }





    @PostMapping("/create")
    public ResponseEntity<Club> create(

            @Valid @RequestBody Club club

    ) {



        return ResponseEntity.ok(
                service.createClub(club)
        );



    }





    @PutMapping("/update/{id}")
    public ResponseEntity<Club> update(

            @PathVariable Long id,

            @Valid @RequestBody Club club

    ) {



        return ResponseEntity.ok(
                service.updateClub(id, club)
        );



    }





    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(

            @PathVariable Long id

    ) {



        service.deleteClub(id);



        return ResponseEntity.ok(
                "Club deleted successfully"
        );



    }


}
