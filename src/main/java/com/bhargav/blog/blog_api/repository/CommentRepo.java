package com.bhargav.blog.blog_api.repository;

import com.bhargav.blog.blog_api.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer> {

    // Additional query methods can be defined here if needed
}
