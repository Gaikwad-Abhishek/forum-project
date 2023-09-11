package com.prodapt.learningspring.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import com.prodapt.learningspring.entity.LikeId;
import com.prodapt.learningspring.entity.LikeRecord;
import com.prodapt.learningspring.entity.Post;
import com.prodapt.learningspring.entity.User;

public interface LikeCRUDRepository extends CrudRepository<LikeRecord, LikeId>{
	  public Integer countByLikeIdPost(Post post);
	  
	  public LikeRecord findByLikeIdUserAndLikeIdPost(User user, Post post);
	  
	  boolean existsByLikeIdUserAndLikeIdPost(Optional<User> user, Optional<Post> post);

}
