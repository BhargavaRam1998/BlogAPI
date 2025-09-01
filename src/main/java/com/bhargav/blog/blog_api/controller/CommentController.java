package com.bhargav.blog.blog_api.controller;

import com.bhargav.blog.blog_api.model.Comment;
import com.bhargav.blog.blog_api.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import com.bhargav.blog.blog_api.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final JwtUtil jwtUtil;

    @PostMapping("/{postId}/addComment")
    public ResponseEntity<String> createComment(@PathVariable int postId, @RequestBody Comment comment, HttpServletRequest request) {

        if(!jwtUtil.isAuthorized(request)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return commentService.createComment(postId, comment);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateComment(@PathVariable int id, @RequestBody Comment comment, HttpServletRequest request) {

        if(!jwtUtil.isAuthorized(request)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return commentService.updateComment(id, comment);

    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable int id, HttpServletRequest request) {

        if(!jwtUtil.isAuthorized(request)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return commentService.deleteComment(id);

    }
}
