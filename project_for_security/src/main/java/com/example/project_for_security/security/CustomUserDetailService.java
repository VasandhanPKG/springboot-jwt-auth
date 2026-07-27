package com.example.project_for_security.security;

import com.example.project_for_security.entity.User;
import com.example.project_for_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository
                .findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Username Not found"));

       return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),null);
    }
}
