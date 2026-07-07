package com.authservice.service;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    private RedisTemplate<String, String> redisTemplate;
    private static final long OTP_EXPIRY = 5;

	public OtpService(RedisTemplate<String, String> redisTemplate)
	{
		this.redisTemplate =redisTemplate;
	}
	
	
    public void saveOtp(String email, String otp) {

        redisTemplate.opsForValue().set(
                "OTP:" + email,
                otp,
                OTP_EXPIRY,
                TimeUnit.MINUTES);
    }
    
    public String getOtp(String email) {

        return redisTemplate.opsForValue().get("OTP:" + email);
    }

    public void deleteOtp(String email) {

        redisTemplate.delete("OTP:" + email);
    }
    
    public String generateOtp() {

        SecureRandom random = new SecureRandom();

        return String.format("%06d", random.nextInt(1000000));
    }
	
}
