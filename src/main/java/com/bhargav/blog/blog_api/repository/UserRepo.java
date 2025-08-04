package com.bhargav.blog.blog_api.repository;

import com.bhargav.blog.blog_api.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {

    Optional<Users> findByEmail(String email);

    // Additional query methods can be defined here if needed
}
