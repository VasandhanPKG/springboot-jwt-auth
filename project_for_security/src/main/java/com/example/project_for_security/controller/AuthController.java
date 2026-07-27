package com.example.project_for_security.controller;

import com.example.project_for_security.dto.UserDTO;
import com.example.project_for_security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
     @Autowired
     private AuthService authService;



     @PostMapping("/register")
     public String register(@RequestBody UserDTO
                            userDTO) {
         System.out.println("LOGIN API HIT");
         return authService.register(userDTO.getUsername(),  userDTO.getPassword());
     }
     @PostMapping("/login")
     public String login(@RequestBody UserDTO userDTO) {
         return authService.login(userDTO.getUsername(), userDTO.getPassword());
     }
}
