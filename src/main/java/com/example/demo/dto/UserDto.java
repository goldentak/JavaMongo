package com.example.demo.dto;

import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String password;
    private String username;
    private String bio;
    private Integer age;
}
