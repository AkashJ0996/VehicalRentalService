package com.auto.in.utils;




import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;





@Component
public class JWTUtil {
	
    
   
    public String extractUsername(String token) {
 	   return extractClaims(token , Claims::getSubject);
    }
    
    public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(),userDetails);
	}
	
	public boolean isTokenValid(String token , UserDetails userDetails) {
		   final String username = extractUsername(token);
		   return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
	}
	
	public <T> T extractClaims(String token , Function<Claims , T> claimResolver){
		   final Claims claims = extractAllClaims(token);
		   return claimResolver.apply(claims);
	}
	
	public String generateToken(Map<String , Object> claims , UserDetails userDetails) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 10000 * 60 * 24))
				.signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
   }
	
	public String generateRefreshToken(Map<String , Object> claims , UserDetails userDetails) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 604800000))
				.signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
   }
	
   private boolean isTokenExpired(String token) {
		   return extractClaims(token , Claims::getExpiration).before(new Date());
   }
	
   private Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
   }
   
   private Claims extractAllClaims(String token) {
		
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody() ;
   }
   
   private Key getSignKey() {
	   byte[] keybytes = Decoders.BASE64.decode("5367566B597124567895A1452639872D145236987521236CJ4497169886558767424H14652688978K");
	   return Keys.hmacShaKeyFor(keybytes);
   }
   
   
 
   
}


