package com.bhargav.blog.blog_api.config;

import com.bhargav.blog.blog_api.model.Post;
import com.bhargav.blog.blog_api.model.Users;
import com.bhargav.blog.blog_api.repository.PostRepo;
import com.bhargav.blog.blog_api.repository.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Optional;

@Component
@AllArgsConstructor
public class JwtUtil {

    private final PostRepo postRepo;
    private final UserRepo userRepo;

    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String email) {

        Users user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return Jwts.builder()
                .setSubject(email)
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            String email = extractEmail(token);
            return email != null;
        } catch (Exception e) {
            return false;
        }
    }

    private String extractEmail(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return claims.getSubject();
    }

    public String extractRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.get("role", String.class);
    }

    public String extractToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;

    }

    public boolean isAuthorized(HttpServletRequest request) {

        String token = extractToken(request);

        return validateToken(token);
    }

    public boolean isSameUser(int id, HttpServletRequest request) {

        String token = extractToken(request);

        String currentUser = extractEmail(token);

        Optional<Post> existingPost = postRepo.findById(id);

        if (currentUser == null || !currentUser.equals(existingPost.get().getAuthorEmail())) {
            return false;
        }
        return true;
    }

    public boolean isUserAdmin(HttpServletRequest request) {
        String token = extractToken(request);

        String role = extractRole(token);

        return role.equals("ADMIN");
    }
}
