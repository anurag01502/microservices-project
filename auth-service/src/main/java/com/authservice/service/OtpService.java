package com.authservice.service;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.authservice.dao.RegistrationDao;

import jakarta.transaction.Transactional;


@Service
public class OtpService {

    private RedisTemplate<String, String> redisTemplate;
    private static final long OTP_EXPIRY = 5;
    private RegistrationDao registrationDao;

    Logger logger = LoggerFactory.getLogger(getClass());
	public OtpService(RedisTemplate<String, String> redisTemplate, RegistrationDao registrationDao)
	{
		this.redisTemplate =redisTemplate;
		this.registrationDao=registrationDao;
	}
	
	@Transactional
    public void saveOtp(String email, String otp) {

        redisTemplate.opsForValue().set(
                "OTP:" + email,
                otp,
                OTP_EXPIRY,
                TimeUnit.MINUTES);
    }
    
    public String getOtp(String email) {

        String otp=  redisTemplate.opsForValue().get("OTP:" + email);
        logger.info("otp for email {} is {} ",email,otp);
        
        return otp;
    }

    @Transactional
    public void deleteOtp(String email) {

        redisTemplate.delete("OTP:" + email);
    }
    @Transactional
    public String generateOtp() {

        SecureRandom random = new SecureRandom();

        return String.format("%06d", random.nextInt(1000000));
    }
    
    @Transactional
    public boolean verifyOtp(String email, String enteredOtp) {

        String storedOtp = getOtp(email);

 
        if(storedOtp == null) {
            return false;
        }

        if(storedOtp.equals(enteredOtp)) {

        		deleteOtp(email);
        		
        		registrationDao.verifyUser(email);

            return true;
        }

        return false;
    }
	
}
