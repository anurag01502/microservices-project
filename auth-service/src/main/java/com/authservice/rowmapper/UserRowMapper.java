package com.authservice.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.authservice.model.ProfileResponse;
import com.authservice.model.UserModel;
import com.authservice.model.UserRequest;



public class UserRowMapper implements RowMapper<UserModel>
{
	

	@Override
	public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		
		UserModel login = new UserModel();
		
		login.setUserId(rs.getLong("user_id"));
		login.setFirstName(rs.getString("first_name"));
		login.setLastName(rs.getString("last_name"));
		login.setUserName(rs.getString("user_name"));
		login.setCountryCode(rs.getString("country_code"));
		login.setPhoneNumber(rs.getString("phone_number"));
		login.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
		login.setEmail(rs.getString("email"));
		login.setRole(rs.getString("role"));
		login.setGender(rs.getString("gender"));
		login.setPassword(rs.getString("password_hash"));
		return login;
	}
	
	
	public static ProfileResponse toDto(UserModel loginModel )
	{
		ProfileResponse profileResponseDto = new ProfileResponse();
		
		profileResponseDto.setUserId(loginModel.getUserId());
		profileResponseDto.setFirstName(loginModel.getFirstName());
		profileResponseDto.setLastName(loginModel.getLastName());
		profileResponseDto.setEmail(loginModel.getEmail());
		profileResponseDto.setUserName(loginModel.getUserName());
		profileResponseDto.setDateOfBirth(loginModel.getDateOfBirth());
		profileResponseDto.setCountryCode(loginModel.getCountryCode());
		profileResponseDto.setPhoneNumber(loginModel.getPhoneNumber());
		profileResponseDto.setRole(loginModel.getRole());
		profileResponseDto.setGender(loginModel.getGender());
		profileResponseDto.setPassword(loginModel.getPassword());
		
		return profileResponseDto;
	}
	
	public static UserModel toModel(UserRequest userRequestDto) {

	    UserModel userModel = new UserModel();

	    userModel.setFirstName(userRequestDto.getFirstName());
	    userModel.setLastName(userRequestDto.getLastName());
	    userModel.setUserName(userRequestDto.getUserName());
	    userModel.setEmail(userRequestDto.getEmail());
	    userModel.setPhoneNumber(userRequestDto.getPhoneNumber());
	    userModel.setGender(userRequestDto.getGender());
	    userModel.setDateOfBirth(userRequestDto.getDateOfBirth());
	    userModel.setCountryCode(userRequestDto.getCountryCode());

	    return userModel;
	}

}
