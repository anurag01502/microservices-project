package com.authservice.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import com.authservice.dao.LoginDao;
import com.authservice.dto.RefreshTokenResponse;
import com.authservice.model.LoginRequest;
import com.authservice.security.JwtUtil;


@Service
public class LoginService  {

	
	private final LoginDao loginDao;
	private final JwtUtil jwtUtil;
	public LoginService(LoginDao loginDao,JwtUtil jwtUtil)
	{
		this.loginDao=loginDao;
		this.jwtUtil=jwtUtil;
		
	}
	
	public Map<Object, String> login(LoginRequest loginRequest) {
		
		
		Map<Object, String> loginResponse = new ConcurrentHashMap<>();
		
		loginDao.login(loginRequest);		
		String token = jwtUtil.generateToken(loginRequest.getIdentifier());
		
		loginResponse.put("message", "Successfully LoggedIn!");
		loginResponse.put("token", token);
		return loginResponse;
	}
	
	
	public RefreshTokenResponse refreshToken(String refreshToken) {

	    // 1. Validate refresh token

	    // 2. Extract username from refresh token
	    String username = jwtUtil.extractUsername(refreshToken);

	    // 3. Verify it exists and isn't revoked

	    // 4. Generate new access token
	    String accessToken = jwtUtil.generateToken(username);

	    // 5. Optionally rotate refresh token
	    String newRefreshToken = jwtUtil.refreshToken(username);

	    return new RefreshTokenResponse(accessToken, newRefreshToken);
	}

}
