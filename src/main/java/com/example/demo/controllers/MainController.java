package com.example.demo.controllers;

import com.example.demo.dto.FollowDto;
import com.example.demo.dto.UserAuthDto;
import com.example.demo.dto.UserDto;
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
}

