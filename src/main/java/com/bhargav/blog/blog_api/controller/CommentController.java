package com.bhargav.blog.blog_api.controller;

import com.bhargav.blog.blog_api.model.Comment;
import com.bhargav.blog.blog_api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/addComment")
    public ResponseEntity<String> createComment(@PathVariable int postId, @RequestBody Comment comment) {

        return commentService.createComment(postId, comment);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateComment(@PathVariable int id, @RequestBody Comment comment) {

        return commentService.updateComment(id, comment);

    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable int id) {

        return commentService.deleteComment(id);

    }
}
