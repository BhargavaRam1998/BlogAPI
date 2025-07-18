package com.bhargav.blog.blog_api.service;

import com.bhargav.blog.blog_api.model.Post;
import com.bhargav.blog.blog_api.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepo postRepo;

    public void createPost(Post post){

        postRepo.save(post);

    }

    public ResponseEntity<String> deletePost(int id) {

        if(postRepo.existsById(id)) {
            postRepo.deleteById(id);
            return new ResponseEntity<>("Post " + id +  " deleted succesfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Post with id " + id + " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepo.findAll(pageable);
    }

    public List<Post> searchPosts(String tag) {
       return postRepo.findAll().stream()
                .filter(post -> post.getTags() != null && post.getTags().contains(tag))
                .toList();

    }

    public void deleteAllPosts() {
        postRepo.deleteAll();
    }

    public boolean updatePost(int id, Post post) {
        if(postRepo.existsById(id)) {
            post.setId(id);
            postRepo.save(post);
            return true;
        } else {
            return false;
        }
    }
}
