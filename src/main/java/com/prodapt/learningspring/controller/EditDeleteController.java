package com.prodapt.learningspring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.prodapt.learningspring.business.LoggedInUser;
import com.prodapt.learningspring.controller.binding.AddPostForm;
import com.prodapt.learningspring.entity.Post;
import com.prodapt.learningspring.entity.User;
import com.prodapt.learningspring.repository.CommentCRUDRepository;
import com.prodapt.learningspring.repository.LikeCRUDRepository;
import com.prodapt.learningspring.repository.LikeCountRepository;
import com.prodapt.learningspring.repository.PostRepository;
import com.prodapt.learningspring.repository.UserRepository;
import com.prodapt.learningspring.service.DomainUserService;
import com.prodapt.learningspring.service.EditDeleteService;

import jakarta.servlet.ServletException;

@Controller
@RequestMapping("/forum")
public class EditDeleteController {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private LoggedInUser loggedInUser;

	@Autowired
	private DomainUserService domainUserService;

	@Autowired
	private EditDeleteService editDeleteService;

	@GetMapping("/mypost")
	public String MyPostList(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		
		Optional<User> user = userRepository.findByName(userDetails.getUsername());
		List<Post> postList = editDeleteService.getMyPosts(user.get().getUserId());
//		model.addAttribute("likerName", userDetails.getUsername());
//		model.addAttribute("commenterName", userDetails.getUsername());
		model.addAttribute("posts", postList);
		
		return "forum/MyPosts";
	}
	
	@PostMapping("/post/{id}/delete")
	public String deletePost(@PathVariable long id) {

		editDeleteService.deleteLikes(id);
		editDeleteService.deletePostById(id);

		return String.format("redirect:/forum/mypost");
	}

	@GetMapping("/post/{id}/edit")
	public String editPost(@PathVariable long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
		
		User author = domainUserService.getByName(userDetails.getUsername()).get();
		model.addAttribute("postForm", editDeleteService.getSelectedPost(author,id));
		model.addAttribute("postId", id);

		return "forum/editForm";
	}

	@PostMapping("/post/{id}/edit/save")
	public String editPostSave(@RequestParam("postId") long id,@ModelAttribute("postForm") AddPostForm postForm, BindingResult bindingResult,
			RedirectAttributes attr) throws ServletException {
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getFieldErrors());
			attr.addFlashAttribute("org.springframework.validation.BindingResult.post", bindingResult);
			attr.addFlashAttribute("post", postForm);
			return "redirect:/forum/post/{id}/edit";
		}
		
		editDeleteService.editPost(id, postForm.getContent());
		return String.format("redirect:/forum/mypost");
	}
	
	@PostMapping("/post/{id}/comment/delete")
	public String deleteComment(@PathVariable long id) {
		
		editDeleteService.deleteComment(id);
	
		return String.format("redirect:/forum/mypost");
	}
	
	@GetMapping("/post/{id}/comment/edit")
	public String editComment(@PathVariable long id,Model model) {
		
		model.addAttribute("comment", editDeleteService.getSelectedComment(id));
		model.addAttribute("commentId", id);
		return "forum/editComment";
	}

	@PostMapping("/post/{id}/edit/comment/save")
	public String updateComment(@PathVariable long id,@RequestParam("content") String content) {
		
		editDeleteService.updateComment(id, content);

		return String.format("redirect:/forum/mypost");
	}
}
