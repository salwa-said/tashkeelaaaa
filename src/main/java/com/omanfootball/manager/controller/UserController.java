package com.omanfootball.manager.controller;


import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;


import com.omanfootball.manager.dto.UserResponse;
import com.omanfootball.manager.service.UserService;



@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {



    private final UserService userService;





    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers().stream()
                .map(user -> new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getRole().name()))
                .toList());



    }





    @GetMapping("/by-id/{id}")
    public ResponseEntity<UserResponse> getUser(

            @PathVariable Long id

    ) {



        var user = userService.getUserById(id);
        return ResponseEntity.ok(new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getRole().name()));



    }





    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        var user = userService.getUserByEmail(authentication.getName());
        return ResponseEntity.ok(new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getRole().name()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> updateUser(

            @PathVariable Long id,

            @RequestBody com.omanfootball.manager.entity.User user

    ) {



        var saved = userService.updateUser(id, user);
        return ResponseEntity.ok(new UserResponse(saved.getId(), saved.getFullName(), saved.getEmail(), saved.getRole().name()));



    }





    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(

            @PathVariable Long id

    ) {



        userService.deleteUser(id);



        return ResponseEntity.ok(
                "User deleted successfully"
        );



    }


}
