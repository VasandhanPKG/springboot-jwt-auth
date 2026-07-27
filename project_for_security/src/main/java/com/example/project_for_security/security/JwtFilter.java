package com.example.project_for_security.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtservice;
    private final CustomUserDetailService customUserDetailService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    final String Authorization=request.getHeader("Authorization");
    if(Authorization==null || !Authorization.startsWith("Bearer ")) {
        filterChain.doFilter(request,response);
        return;

    }
    String token=Authorization.substring(7);
    String username=jwtservice.extractUsername(token);
   if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
       UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
       if(jwtservice.validateToken(token))
       {
           UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
           SecurityContextHolder.getContext().setAuthentication(authentication);

       }

   }
        filterChain.doFilter(request,response);
    }
}
