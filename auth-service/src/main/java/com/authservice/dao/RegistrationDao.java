package com.authservice.dao;


public interface RegistrationDao {

     static final String INSERT_USER = """
        INSERT INTO users
        (
            first_name,
            last_name,
            user_name,
            email,
            country_code,
            phone_number,
            date_of_birth,
            gender,
            role,
            password_hash
        )
        VALUES
        (
            ?, ?, ?, ?, ?, ?, ?, ?,?,?
        )
        """;

     static final String VERIFY_USER = """
             UPDATE users
             SET is_verified = true
             WHERE email = ?
             """;
    
     static final String CHECK_USER_EXISTS = """
SELECT COUNT(*) FROM users WHERE user_name = ?OR email = ?""";
    void register(com.authservice.model.RegistrationRequest request);
    
    public boolean userExists(String username,String email);
    
    int verifyUser(String email);


}
