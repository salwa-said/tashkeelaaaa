package com.omanfootball.manager.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;


import java.nio.charset.StandardCharsets;
import java.util.Date;



@Service
public class JwtService {



    private static final String SECRET_KEY =
            "TASHKEELA_OMAN_FANTASY_FOOTBALL_SECRET_KEY_2026_SECURITY_256_BITS";



    private static final long EXPIRATION_TIME =
            1000L * 60 * 60 * 24;





    private SecretKey getKey() {

        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(
                        StandardCharsets.UTF_8
                )
        );

    }





    public String generateToken(String email) {


        Date now = new Date();


        Date expiration = new Date(
                now.getTime()
                        +
                        EXPIRATION_TIME
        );



        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(
                        getKey()
                )
                .compact();

    }





    public String extractEmail(String token) {

        return extractClaims(token)
                .getSubject();

    }





    private Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(
                        getKey()
                )
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }





    public boolean validateToken(
            String token,
            String email
    ) {


        try {


            String tokenEmail =
                    extractEmail(token);



            return tokenEmail.equals(email);



        } catch (Exception e) {


            return false;


        }

    }


}