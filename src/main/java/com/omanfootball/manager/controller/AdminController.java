package com.omanfootball.manager.controller;


import com.omanfootball.manager.entity.User;
import com.omanfootball.manager.dto.UserResponse;
import com.omanfootball.manager.service.AdminService;
import com.omanfootball.manager.service.EmailService;


import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdminController {



    private final AdminService adminService;



    private final EmailService emailService;





    // =========================
    // CREATE USER
    // =========================

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(
            @RequestBody User user
    ) {


        User saved =
                adminService.createUser(user);



        return ResponseEntity.ok(new UserResponse(saved.getId(), saved.getFullName(), saved.getEmail(), saved.getRole().name()));


    }





    // =========================
    // DELETE USER
    // =========================

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id
    ) {


        adminService.deleteUser(id);



        return ResponseEntity.ok(
                "User deleted"
        );


    }





    // =========================
    // SEND EMAIL
    // =========================

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(
            @RequestParam String email,
            @RequestParam String subject,
            @RequestParam String message
    ) {


        emailService.sendEmail(
                email,
                subject,
                message
        );



        return ResponseEntity.ok(
                "Email sent successfully"
        );


    }


}
