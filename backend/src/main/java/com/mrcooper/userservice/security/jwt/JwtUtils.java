package com.mrcooper.userservice.security.jwt;

import com.mrcooper.userservice.security.user.MRCooperUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;
    @Value("${auth.token.expirationInMils}")
    private int jwtExpirationMs;

    public String generateTokenForUser(Authentication authentication) {
        MRCooperUserDetails userPrincipal = (MRCooperUserDetails) authentication.getPrincipal();

        List<String> roles = userPrincipal.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .toList(); // ✅ Convert roles to list

        System.out.println("🚀 Generating Token for User: " + userPrincipal.getUsername());
        System.out.println("✅ Assigned Roles: " + roles);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("id", userPrincipal.getId())
                .claim("roles", roles) // ✅ Ensure roles are stored correctly
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
        }catch(MalformedJwtException | IllegalArgumentException | UnsupportedJwtException | ExpiredJwtException e){
            throw new JwtException(e.getMessage());
        }

    }

    public List<SimpleGrantedAuthority> getRolesFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<String> roles = claims.get("roles", List.class); //  Extract roles from JWT claims

        System.out.println(" Extracted Roles from Token: " + roles);

        if (roles == null) {
            System.out.println(" No roles found in token!");
            return List.of(); // Return empty list to prevent errors
        }

        return roles.stream()
                .map(SimpleGrantedAuthority::new) //  Convert roles to SimpleGrantedAuthority
                .toList();
    }


}
