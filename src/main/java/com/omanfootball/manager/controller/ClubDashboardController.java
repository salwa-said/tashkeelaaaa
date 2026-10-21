package com.omanfootball.manager.controller;


import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


import com.omanfootball.manager.entity.ClubDashboard;
import com.omanfootball.manager.service.ClubDashboardService;



@RestController
@RequestMapping("/club-dashboards")
@RequiredArgsConstructor
public class ClubDashboardController {



    private final ClubDashboardService service;





    @GetMapping("/all")
    public ResponseEntity<List<ClubDashboard>> getAll() {



        return ResponseEntity.ok(
                service.getAll()
        );



    }





    @GetMapping("/by-id/{id}")
    public ResponseEntity<ClubDashboard> getById(

            @PathVariable Long id

    ) {



        return ResponseEntity.ok(
                service.getById(id)
        );



    }





    @PostMapping("/create")
    public ResponseEntity<ClubDashboard> create(

            @RequestBody ClubDashboard dashboard

    ) {



        return ResponseEntity.ok(
                service.create(dashboard)
        );



    }





    @PutMapping("/update/{id}")
    public ResponseEntity<ClubDashboard> update(

            @PathVariable Long id,

            @RequestBody ClubDashboard dashboard

    ) {



        return ResponseEntity.ok(
                service.update(id, dashboard)
        );



    }





    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(

            @PathVariable Long id

    ) {



        service.delete(id);



        return ResponseEntity.ok(
                "Club dashboard deleted successfully"
        );



    }


}