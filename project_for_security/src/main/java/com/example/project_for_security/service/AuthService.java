package com.example.project_for_security.service;

import com.example.project_for_security.entity.User;
import com.example.project_for_security.repository.UserRepository;
import com.example.project_for_security.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthService {
       private final UserRepository userRepository;
       private final PasswordEncoder passwordEncoder;
       private final JwtService jwtService;

       public String register(String username, String password) {
         if(userRepository.findByUsername(username).isPresent()){
           throw new RuntimeException("Username is already in use");
         }
           User user=new User();
           user.setUsername(username);
           user.setPassword(passwordEncoder.encode(password));
           userRepository.save(user);
           return "registered Successfully";
       }

       public String login(String username, String password) {
          User user=userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("Username not found"));
          if(!passwordEncoder.matches(password,user.getPassword())){
             throw new RuntimeException("Passwords do not match");
          }
          return jwtService.generateToken(user.getUsername());
       }
}
