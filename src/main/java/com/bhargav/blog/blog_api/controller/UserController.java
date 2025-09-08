package com.bhargav.blog.blog_api.controller;

import com.bhargav.blog.blog_api.model.Users;
import com.bhargav.blog.blog_api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add/user")
    public ResponseEntity<HashMap<String, String>> addUser(@RequestBody Users user){
        HashMap<String, String> response = userService.addUser(user);

        if(response.containsKey("token")){
            response.put("message", "User created successfully!");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/users")
    public ResponseEntity<Object> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int limit,
                                              HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page, limit);

        Page<Users> postPage = userService.getAllUsers(pageable);

        if (postPage.isEmpty()) {
            return new ResponseEntity<>("No users available.", HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(
                    postPage.getContent(),
                    HttpStatus.OK
            );
        }

    }
}
