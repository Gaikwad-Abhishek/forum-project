package com.prodapt.learningspring.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prodapt.learningspring.entity.Post;
import com.prodapt.learningspring.repository.PostRepository;

@RestController
//@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
@CrossOrigin
@RequestMapping("/api/forum")
public class ForumRestApi {
	
	@Autowired
	PostRepository postRepository;
	
    @GetMapping("post/{id}")
    public ResponseEntity<Post> getPostDetail(@PathVariable long id) {
        try {
            Optional<Post> post = postRepository.findById(id);
            if (post.isEmpty()) {
                return ResponseEntity.notFound().build();
            } 
            return ResponseEntity.ok(post.get());
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        	return null;
        }
    }

}
