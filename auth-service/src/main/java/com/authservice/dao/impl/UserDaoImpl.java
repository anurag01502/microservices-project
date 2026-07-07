package com.authservice.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.authservice.dao.UserDao;
import com.authservice.exception.CustomRuntimeException;
import com.authservice.model.UserModel;
import com.authservice.rowmapper.UserRowMapper;




@Repository
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}


    @Override
    public UserModel findByUsername(String username) {

        try {

            return jdbcTemplate.queryForObject(
                    FIND_BY_USERNAME,
                    new UserRowMapper(),
                    username
            );

        } catch (EmptyResultDataAccessException ex) {

             throw new CustomRuntimeException("User Not Found!", HttpStatus.NOT_FOUND);
        }
    }

	@Override
	public UserModel findByIdentifier(String identifier) {

        try {

            return jdbcTemplate.queryForObject(
                    FIND_BY_IDENTIFIER,
                    new UserRowMapper(),
                    identifier,identifier,identifier
            );

        } catch (EmptyResultDataAccessException ex) {

             throw new CustomRuntimeException("User Not Found!", HttpStatus.NOT_FOUND);
        }
	}


	public Integer updateUserProfile(
	        UserModel userModel,
	        String email) {

	    return jdbcTemplate.update(
	            UPDATE_PROFILE,
	            userModel.getFirstName(),
	            userModel.getLastName(),
	            userModel.getPhoneNumber(),
	            userModel.getGender(),
	            userModel.getDateOfBirth(),
	            userModel.getCountryCode(),
	            email);
	}


	@Override
	public void deleteProfile(String userEmail) {
		
		Integer status = jdbcTemplate.update(DELETE_PROFILE,userEmail);

		
		if(status==0)
		{
			throw new CustomRuntimeException("Credentials does not exist!", HttpStatus.NOT_FOUND);
		}
	}


	@Override
	public Integer updatePassword(String password, String email) {
		
		Integer status = jdbcTemplate.update(UPDATE_PASSWORD,password,email);
		
		if(status==0)
		{
			throw new CustomRuntimeException("Credentials does not exist!", HttpStatus.NOT_FOUND);
		}
		return status;
	}


	@Override
	public void blackListToken(String tokenId) {

	    try {
	        int rowsAffected = jdbcTemplate.update(BLACK_LIST_TOKEN, tokenId);

	        if (rowsAffected == 0) {
	            throw new CustomRuntimeException(
	                    "Failed to blacklist token!",
	                    HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } catch (DataAccessException ex) {
	        throw new CustomRuntimeException(
	                "Error while blacklisting token",
	                HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	@Override
	public Boolean isBlackListed(String tokenId) {
	
	
		Boolean status= jdbcTemplate.queryForObject(BLACKLISTED_STATUS, Boolean.class,tokenId);
		
		return status;
	}
}