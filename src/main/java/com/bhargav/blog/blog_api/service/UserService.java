package com.bhargav.blog.blog_api.service;

import com.bhargav.blog.blog_api.config.JwtUtil;
import com.bhargav.blog.blog_api.model.Post;
import com.bhargav.blog.blog_api.model.Users;
import com.bhargav.blog.blog_api.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public HashMap<String, String> addUser(Users user) {

        HashMap<String, String> response = new HashMap<>();

        Optional<Users> existingUser = userRepo.findByEmail(user.getEmail());

        if(existingUser.isPresent()){
            response.put("Error!!", "User already exists");
            return response;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        response.put("token", token);
        return response;
    }

    public Page<Users> getAllUsers(Pageable pageable) {
        return userRepo.findAll(pageable);

    }


}
