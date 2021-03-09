package ru.oleg.sketchbook.security;

import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;



@Component
@Data
public class JwtTokenProvider {

    private final UserDetailsServiceImpl userDetailsService;

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.header}")
    private String header;
    @Value("${jwt.validity}")
    private long validityTime;

    public JwtTokenProvider(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String creatToken(String email, String role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);
        Date date = new Date();
        Date validity = new Date(date.getTime() + validityTime * 1000);


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /** проверяем токен на "срок годности" **/
    public boolean validityToken(String token) throws JwtAuthenticationException {
        try {
            Jws<Claims> claimsJwt = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJwt.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException exception) {
            throw new JwtAuthenticationException("Jwt token invalid", HttpStatus.UNAUTHORIZED);
        }
    }

    public Authentication getAuthentication(String token){
        UserDetails details = userDetailsService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(details, "", details.getAuthorities());
    }

    public String getEmail(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader(header);
    }
}
