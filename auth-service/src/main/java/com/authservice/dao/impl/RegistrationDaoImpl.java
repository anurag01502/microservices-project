package com.authservice.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.authservice.dao.RegistrationDao;
import com.authservice.model.RegistrationRequest;


@Repository
public class RegistrationDaoImpl implements RegistrationDao {

    
    private final JdbcTemplate jdbcTemplate;
    
    public RegistrationDaoImpl(JdbcTemplate jdbcTemplate)
    {
    	this.jdbcTemplate= jdbcTemplate;
    	
    }


    @Override
    public void register(RegistrationRequest request) {

        jdbcTemplate.update(
                INSERT_USER,
                request.getFirstName(),
                request.getLastName(),
                request.getUsername(),
                request.getEmail(),
                request.getCountryCode(),
                request.getPhoneNumber(),
                request.getDateOfBirth(),
                request.getGender(),
                request.getRole(),
                request.getPassword()
        );
    }
    
    
    public boolean userExists(String username,String email) {

        Integer count = jdbcTemplate.queryForObject(CHECK_USER_EXISTS,Integer.class, username,  email);

        return count != null && count > 0;
    }


	@Override
    public int verifyUser(String email) {


        return jdbcTemplate.update(VERIFY_USER);
    }
    
}