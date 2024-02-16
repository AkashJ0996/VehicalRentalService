package com.auto.in.dto;

import com.auto.in.enums.UserRole;

import lombok.Data;

@Data
public class AuthenticationResponse {
	
	private String jwt ;
	private UserRole userRole ;
	private long userId ;

}
