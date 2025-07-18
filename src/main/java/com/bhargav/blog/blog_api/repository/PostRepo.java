package com.bhargav.blog.blog_api.repository;

import com.bhargav.blog.blog_api.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

    // Define custom query methods if needed
    // For example, find posts by author, title, etc.

}
