package com.authservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.authservice.dao.RegistrationDao;
import com.authservice.exception.CustomRuntimeException;
import com.authservice.model.RegistrationRequest;


@Service
public class RegistrationService {

    private PasswordEncoder passwordEncoder;
    private RegistrationDao registrationDao;

    
	Logger logger  = LoggerFactory.getLogger(RegistrationService.class);
    private OtpService otpService;
    public RegistrationService(PasswordEncoder passwordEncoder, RegistrationDao registrationDao) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.registrationDao = registrationDao;
	}

    @Transactional
    public void register(RegistrationRequest request) {

		

        boolean userExistanceFlag =
                registrationDao.userExists(
                        request.getUsername(),
                        request.getEmail()
                );

        if (userExistanceFlag) {

            throw new CustomRuntimeException(
                    "User with the credentials already exists!",
                    HttpStatus.CONFLICT
            );
        }

        
        logger.info("password : {}",request.getPassword());
        logger.info("confirm password {}",request.getConfirmPassword());
        
        
        if (!request.getPassword()
                .equals(request.getConfirmPassword())) {

            throw new CustomRuntimeException(
                    "Password and Confirm Password do not match",
                    HttpStatus.BAD_REQUEST
            );
        }
        
        String encodedPassword =
                passwordEncoder.encode(
                        request.getPassword()
                );

        
        
        request.setPassword(encodedPassword);

        registrationDao.register(request);

        String otp= otpService.generateOtp();
        
        
        logger.info(" otp for email : {} is {}",request.getEmail(),otp);
        otpService.saveOtp(request.getEmail(), otp);

        
    }
}
