package com.auto.in.configuration;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auto.in.services.jwt.UserService;
import com.auto.in.services.jwt.UserServiceImpl;
import com.auto.in.utils.JWTUtil;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserService  userService;
	
	private final JWTUtil jwtUtils;
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;
		
			
		if(StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader,"Bearer ")) {
			filterChain.doFilter(request, response);
			return ;
		}
			jwt = authHeader.substring(7);
			userEmail = jwtUtils.extractUsername(jwt);
			
			if(StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication()== null) {
				UserDetails userDetailS = userService.userDetailsService().loadUserByUsername(userEmail);
				
				if(jwtUtils.isTokenValid(jwt , userDetailS)) {
					SecurityContext context = SecurityContextHolder.createEmptyContext();
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetailS,null,userDetailS.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					context.setAuthentication(authToken);
					SecurityContextHolder.setContext(context);;
				}
			}
			filterChain.doFilter(request, response);
			
		}
		
	}


