package com.auto.in.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auto.in.dto.AuthenticationRequest;
import com.auto.in.dto.AuthenticationResponse;
import com.auto.in.dto.ChangePasswordDto;
import com.auto.in.dto.SignupRequest;
import com.auto.in.dto.UserDto;
import com.auto.in.entities.User;
import com.auto.in.repository.UserRepository;
import com.auto.in.services.auth.AuthService;
import com.auto.in.services.jwt.UserService;
import com.auto.in.utils.JWTUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	private final AuthenticationManager authenticationManager; 
	
	private final UserService userService;
	
	private final JWTUtil jwtUtil;
	
	private final UserRepository userRepository;
	
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> createUser(@RequestBody SignupRequest signupRequest){
		if(authService.alreadyUsedEmail(signupRequest.getEmail())) {
			return new ResponseEntity<>("signup failed , Email Already Exist !",HttpStatus.NOT_ACCEPTABLE);
		}
		UserDto createCustomer = authService.createCustomer(signupRequest);
		
		if(createCustomer == null) {
			return new ResponseEntity<>("signup failed , please try again later !",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(createCustomer,HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws 
		BadCredentialsException, DisabledException , UsernameNotFoundException {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
		}catch(BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect username or password");
		}
		
		final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
		Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);
		
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		if(optionalUser.isPresent()) {
			authenticationResponse.setJwt(jwt);
			authenticationResponse.setUserId(optionalUser.get().getId());
			authenticationResponse.setUserRole(optionalUser.get().getUserRole());
			
			
		}
		return authenticationResponse;
	}
	
	
	
	
	
}
