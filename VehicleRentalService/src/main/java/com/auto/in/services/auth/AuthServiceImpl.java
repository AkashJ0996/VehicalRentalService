package com.auto.in.services.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auto.in.dto.ChangePasswordDto;
import com.auto.in.dto.SignupRequest;
import com.auto.in.dto.UserDto;
import com.auto.in.repository.UserRepository;

import jakarta.annotation.PostConstruct;

import com.auto.in.entities.User;
import com.auto.in.enums.UserRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	
	private final UserRepository userRepository;
	
	
	
	@PostConstruct
	public void createAdminAccount() {
		User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
		
		if(adminAccount == null) {
			User admin = new User();
			admin.setName("admin");
			admin.setEmail("admin@gmail.com");
			admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
			admin.setUserRole(UserRole.ADMIN);
			
			userRepository.save(admin);
			System.out.println("Admin account is created successfully");
		}
	}

	@Override
	public UserDto createCustomer(SignupRequest signupRequest) {
		User user = new User();
		user.setName(signupRequest.getName());
		user.setEmail(signupRequest.getEmail());
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		user.setUserRole(UserRole.CUSTOMER);
		
		User createdUser = userRepository.save(user);
		UserDto dto = new UserDto();
		
		dto.setId(createdUser.getId());
		
		return dto;
	}

	@Override
	public boolean alreadyUsedEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email).isPresent();
	}

	
	
	

}
