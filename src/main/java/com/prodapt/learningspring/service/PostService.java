package com.prodapt.learningspring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.learningspring.entity.Post;
import com.prodapt.learningspring.repository.CommentCRUDRepository;
import com.prodapt.learningspring.repository.LikeCRUDRepository;
import com.prodapt.learningspring.repository.PostRepository;

import jakarta.transaction.Transactional;

@Service
public class PostService {
	
	@Autowired
    private PostRepository postRepository;
	
	@Autowired
	private LikeCRUDRepository likeCRUDRepository;
	
	@Autowired
	private CommentCRUDRepository commentCRUDRepository;
	
	public List<Post> getMyPosts(long userid) {
		return postRepository.findByAuthorId(userid);
	}
	
	@Transactional
	public void deletePostById(long id) {
//		postRepository.delete(postRepository.findById(id).get());
//		postRepository.delete(postRepository.findById(id).get());
		Post postToDelete = postRepository.findById(id).orElse(null);

        if (postToDelete != null) {
            // Remove the association between User and Post
            postToDelete.setAuthor(null);

            // Delete the Post
            postRepository.delete(postToDelete);
        }
	}
	
	@Transactional
	public void deleteLikeAndComment(long id) {
		likeCRUDRepository.deleteByPostId(id);
		commentCRUDRepository.deleteByPostId(id);
	}
	
	public void editPost(long id) {
		
	}
	
}
