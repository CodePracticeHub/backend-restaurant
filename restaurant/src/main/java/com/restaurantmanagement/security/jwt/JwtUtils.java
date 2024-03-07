package com.restaurantmanagement.security.jwt;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.restaurantmanagement.security.service.UserDetailsImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;



@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${faiss.app.jwtSecret}")
  //SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS384);
  private String jwtSecret;

  @Value("${faiss.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  public String generateJwtToken(Authentication authentication) {
	    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

	    // Get user roles
	    Collection<? extends GrantedAuthority> authorities = userPrincipal.getAuthorities();
	    List<String> roles = authorities.stream()
	                                    .map(GrantedAuthority::getAuthority)
	                                    .collect(Collectors.toList());
	    long expirationTimeInMillis = 30 * 24 * 60 * 60 * 1000L;
	    return Jwts.builder()
	            .setSubject(userPrincipal.getUsername())
	            .claim("roles", roles) // Add roles as a claim
	            .setIssuedAt(new Date())
	            .setExpiration(new Date((new Date()).getTime() + expirationTimeInMillis))
	            .signWith(key(), SignatureAlgorithm.HS256)
	            .compact();
	}

  
  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key()).build()
               .parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
      return true;
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } 
//    catch (ExpiredJwtException e) {
//      logger.error("JWT token is expired: {}", e.getMessage());
//    } catch (UnsupportedJwtException e) {
//      logger.error("JWT token is unsupported: {}", e.getMessage());
//    } 
    catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }
}
