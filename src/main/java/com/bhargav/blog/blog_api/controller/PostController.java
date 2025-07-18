package com.bhargav.blog.blog_api.controller;

import com.bhargav.blog.blog_api.model.Post;
import com.bhargav.blog.blog_api.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        postService.createPost(post);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable int id) {
        return postService.deletePost(id);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int limit) {

        Pageable pageable = PageRequest.of(page, limit);

        Page<Post> postPage = postService.getAllPosts(pageable);

        if (postPage.isEmpty()) {
            return new ResponseEntity<>("No posts available.", HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(
                    postPage.getContent(),
                    HttpStatus.OK
            );
        }

    }

}


