package com.authservice.service;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.authservice.dao.UserDao;
import com.authservice.exception.CustomRuntimeException;
import com.authservice.model.ProfileResponse;
import com.authservice.model.UpdatePasswordRequest;
import com.authservice.model.UserModel;
import com.authservice.model.UserRequest;
import com.authservice.rowmapper.UserRowMapper;



@Service
public class UserService {

	
	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;
    Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	
	public UserService(UserDao userDao,PasswordEncoder passwordEncoder) {
		super();
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;

	}

	public ProfileResponse findByIdentifier(String identifier) {

		UserModel model = userDao.findByIdentifier(identifier);	
		ProfileResponse profileResponse = UserRowMapper.toDto(model);
		
		return profileResponse;
	}

	@Transactional
	public Integer updateUserProfile(UserRequest userRequestDto,String email){
		
		return userDao.updateUserProfile(UserRowMapper.toModel(userRequestDto),email);
		
	}



	@Transactional
	public void deleteProfile(String email) {
		userDao.deleteProfile(email);
		
	}



	@Transactional
	public void updatePassword(UpdatePasswordRequest updatePasswordRequestDto,String email) {
		
		UserModel userModel=   userDao.findByIdentifier(email);
		String oldPasswordHash = userModel.getPassword();
		String oldPasswordRequest = updatePasswordRequestDto.getOldPassword();
		 boolean isPasswordMatches= passwordEncoder.matches( oldPasswordRequest,oldPasswordHash);
		if(!isPasswordMatches)
		{
			throw new CustomRuntimeException("In correct Old password", HttpStatus.FORBIDDEN);
		}
		
		String newPassword = updatePasswordRequestDto.getNewPassword();
		String confirmNewPassword = updatePasswordRequestDto.getConfirmNewPassword();
		if(!newPassword.equals(confirmNewPassword))
		{
			throw new CustomRuntimeException("New password and confirm password do not match", HttpStatus.BAD_REQUEST);
		}
		 newPassword = passwordEncoder.encode(updatePasswordRequestDto.getNewPassword());
		
		
		userDao.updatePassword(newPassword, email);
		
	}

	@Transactional
	public void logout(String tokenId) {
	
		
		userDao.blackListToken(tokenId);
		
	}

	public Boolean isBlackListed(String tokenId) {
		
		return userDao.isBlackListed(tokenId);
	}



}
