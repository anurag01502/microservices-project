package com.authservice.dao.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.authservice.dao.LoginDao;
import com.authservice.dao.UserDao;
import com.authservice.exception.CustomRuntimeException;
import com.authservice.model.LoginRequest;
import com.authservice.model.UserModel;
import com.authservice.rowmapper.UserRowMapper;


@Repository
public class LoginDaoImpl implements LoginDao 
{
	
	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;
	private final JdbcTemplate jdbcTemplate;
	
	public LoginDaoImpl(UserDao userDao, PasswordEncoder passwordEncoder, JdbcTemplate jdbcTemplate) {
		super();
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
		this.jdbcTemplate = jdbcTemplate;
	}

	private static final Logger logger = LoggerFactory.getLogger(LoginDaoImpl.class);

	@Override
	public UserModel login(LoginRequest request) {

	    try {
	    	
	        UserModel user = userDao.findByIdentifier(request.getIdentifier());

	        
	        String requestPassword = request.getPassword();
	        String hashedPassword = user.getPassword();
	        boolean validPassword =passwordEncoder.matches(requestPassword,hashedPassword);
	        
	        
	        
	        logger.info("Login attempt for identifier: {}", request.getIdentifier());

	        logger.debug("Raw Password: {}", request.getPassword()); // Only for debugging
	        logger.debug("Stored Hash: {}", user.getPassword());  
	        
	        if (!validPassword) {

	            throw new CustomRuntimeException("Invalid Credentials", HttpStatus.UNAUTHORIZED);
	        }
	        return jdbcTemplate.queryForObject(
	                VALIDATE_LOGIN_CREDENTIALS, new UserRowMapper(),request.getIdentifier(), request.getIdentifier(),
	                request.getIdentifier(),
	               hashedPassword
	        );
	    } catch (EmptyResultDataAccessException ex) {
	        throw new CustomRuntimeException( "Invalid Login Credentials", HttpStatus.NOT_FOUND);
	    }
	}

}
