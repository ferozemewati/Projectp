package in.fk.main.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // ✅ MUST be at least 32 chars
    private static final String SECRET = "mysecretkeymysecretkeymysecretkey12";

    // ✅ Convert String → Key
    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // ✅ GENERATE TOKEN
    public static String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key)
                .compact();
    }

    // ✅ EXTRACT USERNAME (🔥 THIS WAS MISSING)
    public static String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // ✅ EXTRACT ROLE
    public static String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    // ✅ VALIDATE TOKEN
    public static String validateToken(String token) {
        try {
            System.out.println("VALIDATING TOKEN...");

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();

            System.out.println("EXTRACTED USERNAME: " + username);

            return username;

        } catch (Exception e) {
            System.out.println("TOKEN ERROR: " + e.getMessage());
            return null;
        }
    }
}