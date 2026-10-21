package com.omanfootball.manager.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import org.springframework.security.core.context.SecurityContextHolder;


import org.springframework.stereotype.Component;


import org.springframework.web.filter.OncePerRequestFilter;







import java.io.IOException;
import java.util.List;



@Component
public class JwtAuthenticationFilter {



}
