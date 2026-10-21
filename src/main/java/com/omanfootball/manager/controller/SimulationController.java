package com.omanfootball.manager.controller;


import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


import java.util.List;


import com.omanfootball.manager.entity.WhatIfSimulation;
import com.omanfootball.manager.service.SimulationService;



@RestController
@RequestMapping("/simulations")
@RequiredArgsConstructor
public class SimulationController {



    private final SimulationService service;

    @GetMapping("/mine")
    public ResponseEntity<List<WhatIfSimulation>> getMine(Authentication authentication) {
        return ResponseEntity.ok(service.getMine(authentication.getName()));
    }

    @PostMapping("/mine")
    public ResponseEntity<WhatIfSimulation> simulateMine(@RequestBody SimulationRequest request, Authentication authentication) {
        return ResponseEntity.ok(service.simulateMine(authentication.getName(), request.removePlayerId(),
                request.addPlayerId(), request.captainPlayerId(), request.formation()));
    }

    public record SimulationRequest(Long removePlayerId, Long addPlayerId, Long captainPlayerId, String formation) {}





    @GetMapping("/all")
    public ResponseEntity<List<WhatIfSimulation>> getAll() {



        return ResponseEntity.ok(
                service.getAll()
        );



    }





    @GetMapping("/by-id/{id}")
    public ResponseEntity<WhatIfSimulation> getById(

            @PathVariable Long id

    ) {



        return ResponseEntity.ok(
                service.getById(id)
        );



    }





    @PostMapping("/create")
    public ResponseEntity<WhatIfSimulation> create(

            @RequestBody WhatIfSimulation simulation

    ) {



        return ResponseEntity.ok(
                service.create(simulation)
        );



    }





    @PutMapping("/update/{id}")
    public ResponseEntity<WhatIfSimulation> update(

            @PathVariable Long id,

            @RequestBody WhatIfSimulation simulation

    ) {



        return ResponseEntity.ok(
                service.update(id, simulation)
        );



    }





    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(

            @PathVariable Long id

    ) {



        service.delete(id);



        return ResponseEntity.ok(
                "Simulation deleted successfully"
        );



    }


}
