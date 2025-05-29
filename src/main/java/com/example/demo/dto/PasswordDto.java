package com.example.demo.dto;

import lombok.Data;

@Data
public class PasswordDto {
    private String email;
    private String username;
    private String password;
    private String newPassword;
}
