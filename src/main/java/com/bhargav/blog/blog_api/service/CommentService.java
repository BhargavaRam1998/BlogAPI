package com.bhargav.blog.blog_api.service;

import com.bhargav.blog.blog_api.model.Comment;
import com.bhargav.blog.blog_api.repository.CommentRepo;
import com.bhargav.blog.blog_api.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepo postRepo;

    private final CommentRepo commentRepo;


    public ResponseEntity<String> createComment(int postId, Comment comment) {
        if(postRepo.existsById(postId)){
            comment.setPostId(postId);
            commentRepo.save(comment);

            //return ResponseEntity.ok("Comment added successfully to post with id " + postId);
            return new ResponseEntity<>("Comment added successfully to post with id " + postId, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Post with id " + postId + " does not exist.", HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<String> updateComment(int id, Comment comment) {
        if(commentRepo.existsById(id)){
            comment.setId(id);
            commentRepo.save(comment);
            return new ResponseEntity<>("Comment with id " + id + " updated successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Comment with id " + id + " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteComment(int id) {
        if(commentRepo.existsById(id)) {
            commentRepo.deleteById(id);
            return new ResponseEntity<>("Comment with id " + id + " deleted successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Comment with id " + id + " does not exist.", HttpStatus.NOT_FOUND);
        }
    }
}
