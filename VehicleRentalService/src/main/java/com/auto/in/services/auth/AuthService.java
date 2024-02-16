package com.auto.in.services.auth;

import com.auto.in.dto.ChangePasswordDto;
import com.auto.in.dto.SignupRequest;
import com.auto.in.dto.UserDto;

public interface AuthService {

	UserDto createCustomer(SignupRequest signupRequest);
	
	boolean alreadyUsedEmail(String email);
	
	
}
