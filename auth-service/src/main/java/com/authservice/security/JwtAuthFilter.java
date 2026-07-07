package com.authservice.security;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.authservice.dao.UserDao;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter
        extends OncePerRequestFilter {

	
	Logger logger  = LoggerFactory.getLogger(JwtAuthFilter.class);

    private JwtUtil jwtUtil;

    private UserDao userDao;
    
    private CustomUserDetailsService
            userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService,UserDao userDao) {
		super();
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
		this.userDao =userDao;
	}

	@Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        
        logger.info("URI: {} " , request.getRequestURI());

        if(authHeader != null && authHeader.startsWith("Bearer ")) 
        {

            token =  authHeader.substring(7);
            username =jwtUtil.extractUsername(token);
            
            if (userDao.isBlackListed(token)) {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails =  userDetailsService.loadUserByUsername(username);

            if(jwtUtil.validateToken(token,userDetails.getUsername())) {

            	
                UsernamePasswordAuthenticationToken authToken =	new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext() .setAuthentication(authToken);
            }
        }

        filterChain.doFilter( request, response);
    }
}