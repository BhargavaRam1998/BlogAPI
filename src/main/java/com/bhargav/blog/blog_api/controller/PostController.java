package com.bhargav.blog.blog_api.controller;

import com.bhargav.blog.blog_api.config.JwtUtil;
import com.bhargav.blog.blog_api.model.Post;
import com.bhargav.blog.blog_api.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final JwtUtil jwtUtil;

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody Post post, HttpServletRequest request) {

        if(!jwtUtil.isAuthorized(request)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        postService.createPost(post);
        return new ResponseEntity<>("Post created succesfully!!!", HttpStatus.CREATED);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable int id, HttpServletRequest request) {

        if (!jwtUtil.isAuthorized(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("You are not authorized to delete this post.");
        }

        if (jwtUtil.isUserAdmin(request)) {
            return postService.deletePost(id);
        }

        if (jwtUtil.isSameUser(id, request)) {
            return postService.deletePost(id);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("You are not authorized to delete this post.");
    }

    @PutMapping("/deleteAll")
    public ResponseEntity<String> deleteAllPosts(HttpServletRequest request) {

        if(!jwtUtil.isAuthorized(request)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        postService.deleteAllPosts();
        return new ResponseEntity<>("All posts deleted succesfully!", HttpStatus.OK);
    }


    @GetMapping("/get")
    public ResponseEntity<Object> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int limit,
                                              HttpServletRequest request) {

        if(!jwtUtil.isAuthorized(request)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

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

    @GetMapping("/search")
    public ResponseEntity<Object> searchPosts(@RequestParam String tag, HttpServletRequest request) {

        if(!jwtUtil.isAuthorized(request)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        List<Post> posts =  postService.searchPosts(tag);

        if (posts.isEmpty()) {
            return new ResponseEntity<>(
                    "No posts found with tag: " + tag,
                    HttpStatus.NOT_FOUND
            );
        } else {
            return new ResponseEntity<>(posts, HttpStatus.OK);
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePost(@PathVariable int id, @RequestBody Post post, HttpServletRequest request) {

        if(!jwtUtil.isAuthorized(request)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        boolean updated = postService.updatePost(id, post);

        if (updated) {
            return new ResponseEntity<>("Post updated successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Post with id " + id + " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

}


