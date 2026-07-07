package com.authservice.security;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	
	
	Logger logger  = LoggerFactory.getLogger(JwtUtil.class);
	
	@Value("${jwt.secret}")
    private  String SECRET_KEY ;
	
	
	@Value("${jwt.expiration}")
	private long TOKEN_EXPIRATION;
	
	
	
	@Value("${jwt.refresh-expiration}")
	private long REFRESH_TOKEN_EXPIRATION;


    public String generateToken(String identifier) {

        return Jwts.builder().subject(identifier).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+ TOKEN_EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }
    
    
    public String refreshToken(String identifier) {

        return Jwts.builder().subject(identifier).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+ REFRESH_TOKEN_EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }
    public String extractUsername(String token) {

        return Jwts.parser()
                .verifyWith( Keys.hmacShaKeyFor(SECRET_KEY.getBytes() ))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token,String username) {

        return extractUsername(token).equals(username) ;
        	}
    
    

}