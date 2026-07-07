package com.authservice.dao;


public interface UserDao {
	 static final String FIND_BY_USERNAME = """
	            SELECT user_id,first_name,last_name,user_name,,date_of_birth,, phone_number,  country_code,gender,role,email, role,password_hash 
	            FROM users
	            WHERE user_name = ?
	            """;

		
		static final String FIND_BY_IDENTIFIER ="""
SELECT user_id,first_name,last_name,user_name,date_of_birth ,country_code,gender,role, phone_number,email, role,password_hash 
	            FROM users
	            WHERE user_name = ? or email= ? or phone_number=?
	            """; 
		
		
		
		static final String UPDATE_PROFILE = "UPDATE users\r\n"
				+ "SET\r\n"
				+ "    first_name = ?,\r\n"
				+ "    last_name = ?,\r\n"
				+ "    phone_number = ?,\r\n"
				+ "    gender = ?,\r\n"
				+ "    date_of_birth = ?,\r\n"
				+ "    country_code = ?,\r\n"
				+ "    updated_at = CURRENT_TIMESTAMP\r\n"
				+ "	   WHERE email = ?;";
		
		
		static final String DELETE_PROFILE = "DELETE from users where email=?";
		static final String UPDATE_PASSWORD = "UPDATE users set password_hash=? where email=?";
		static final String BLACK_LIST_TOKEN = "INSERT INTO blacklisted_token(token_id) VALUES (?)";
		static final String BLACKLISTED_STATUS = """
			    SELECT EXISTS(
			        SELECT 1
			        FROM blacklisted_token
			        WHERE token_id = ?
			    )
			    """;
 
		com.authservice.model.UserModel findByUsername(String username);   
		com.authservice.model.UserModel findByIdentifier(String identifier);  
		Integer updateUserProfile(com.authservice.model.UserModel userModel, String email);
		void deleteProfile(String userEmail);
		Integer updatePassword(String password,String email);
		void  blackListToken(String tokenId);
		Boolean isBlackListed(String tokenId);
}
