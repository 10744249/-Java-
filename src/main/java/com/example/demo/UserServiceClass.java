package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

//註冊功能和登入驗證功能
@Service
public class UserServiceClass {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceClass(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // 註冊新用戶
    public boolean registerUser(User user) {
        // Check if phone number is already in use
        Optional<User> existingUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (existingUser.isPresent()) {
            // Phone number already in use
            return false;
        }

        // 加密
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        // 將註冊和上次登入時間設定為目前時間
        LocalDateTime now = LocalDateTime.now();
        user.setRegistrationTime(now);
        user.setLastLoginTime(now);

        // Save user to the database
        userRepository.save(user);
        return true;
    }

    // 使用著登入
    public boolean authenticateUser(User user) {
        // Retrieve the user by phone number
        Optional<User> foundUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (!foundUser.isPresent()) {
            return false; // User not found
        }

        // 確認密碼
        return passwordEncoder.matches(user.getPassword(), foundUser.get().getPassword());
    }
}
