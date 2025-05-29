package com.example.demo.controllers;

import com.example.demo.dto.*;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity changeUsername(@RequestBody UserAuthDto userAuthDto) {
        boolean updated = userService.changeUsername(userAuthDto);

        if (!updated) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        return ResponseEntity.ok("username updated");
    }

    @PostMapping("/change-password")
    public ResponseEntity changePassword(@RequestBody EmailDto emailDto) {
        boolean updated = userService.changePassword(emailDto);

        if (!updated) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        return ResponseEntity.ok("password updated");
    }
}

