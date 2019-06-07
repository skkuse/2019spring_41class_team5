package edu.skku.dealistic.util;

import edu.skku.dealistic.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${application.jwt.secret}")
    private String secret;

    @Value("${application.jwt.expiration-sec}")
    private Integer expirationSec;

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiry = now.plusSeconds(expirationSec);

        return Jwts.builder()
                .setSubject(user.getId())
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expiry.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUserId(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Boolean validate(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
