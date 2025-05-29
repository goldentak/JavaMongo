package com.example.demo.controllers;

import com.example.demo.dto.*;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserDto userDto) {
        userService.createUserFromDto(userDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/follow")
    public ResponseEntity follow(@RequestBody FollowDto followDto) {
        userService.follow(followDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change-bio")
    public ResponseEntity changeBio(@RequestBody BioDto bioDto) {
        boolean updated = userService.changeBio(bioDto);

        if (!updated) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        return ResponseEntity.ok("Bio updated");
    }
    @PostMapping("/change-username")
    public ResponseEntity<?> changeUsername(@RequestBody UsernameDto usernameDto) {
        boolean updated = userService.changeUsername(usernameDto);

        if (!updated) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid credentials or username already in use");
        }

        return ResponseEntity.ok("Username successfully updated");
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordDto passwordDto) {
        boolean updated = userService.changePassword(passwordDto);

        if (!updated) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid credentials or username already in use");
        }

        return ResponseEntity.ok("password successfully updated");
    }
}

