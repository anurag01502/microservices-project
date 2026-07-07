package com.authservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {

	
	@NotBlank(message= " provide username or email ")
    @Size(min = 8,max = 200,  message = "Identifier must be between 8 and 200 characters")

	private String identifier;
	
	
	@NotBlank(message= " provide password")
    @Size( min = 8,  max = 200,message="Password must be between 8 and 200 characters")
	private String password;


	public String getIdentifier() {
		return identifier;
	}


	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	
	
}
