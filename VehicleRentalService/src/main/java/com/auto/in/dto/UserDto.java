package com.auto.in.dto;

import com.auto.in.enums.UserRole;

import lombok.Data;

@Data
public class UserDto {
	
    private long id ;
	
	private String name;
	
	private String email;
	
	private UserRole UserRole;


}
