package com.prodapt.learningspring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prodapt.learningspring.entity.Comment;
import com.prodapt.learningspring.entity.Post;

@Repository
public interface CommentCRUDRepository extends CrudRepository<Comment, Long>{
	List<Comment> findByPostId(long postId);
}

