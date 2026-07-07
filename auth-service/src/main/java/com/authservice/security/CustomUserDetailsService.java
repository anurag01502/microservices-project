package com.authservice.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.authservice.dao.UserDao;
import com.authservice.exception.CustomRuntimeException;
import com.authservice.model.UserModel;


@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    
    private final UserDao userDao;

    
    
    public CustomUserDetailsService(UserDao userDao) {
		super();
		this.userDao = userDao;
	}



	@Override
    public UserDetails loadUserByUsername(String identifier) throws CustomRuntimeException {

        UserModel user =userDao.findByIdentifier(identifier);

        if(user == null) {

            throw new CustomRuntimeException( "User not found",HttpStatus.NOT_FOUND);
        }

        return org.springframework.security
                .core.userdetails.User
                .builder()
                .username(user.getEmail())                
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}