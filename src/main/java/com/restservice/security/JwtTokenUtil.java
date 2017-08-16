package com.restservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;

    static final String CLAIM_KEY_USER_ID= "userId";
    static final String CLAIM_KEY_USER_CODE= "sub";

    @Value("${jwt.secret}")
    private String secret;
    
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }


    public String getUsernameFromToken(String authToken) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(authToken);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public String validateTokenWithClientId(String authToken) {
        String id;
        try {
            final Claims claims = getClaimsFromToken(authToken);
            id= (String) claims.get(CLAIM_KEY_USER_ID);
        } catch (Exception e) {
        	id = null;
        }
        return id;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
    	User user = (User) userDetails;
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()));
    }

    public String generateToken(String id,String usr) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USER_ID, id);
        claims.put(CLAIM_KEY_USER_CODE, usr);
        return generateToken(claims);
    }

    String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }
}