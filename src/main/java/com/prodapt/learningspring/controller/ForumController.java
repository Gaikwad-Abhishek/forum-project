package com.prodapt.learningspring.controller;

import java.util.ArrayList;
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
import com.prodapt.learningspring.business.NeedsAuth;
import com.prodapt.learningspring.controller.binding.AddPostForm;
import com.prodapt.learningspring.controller.exception.ResourceNotFoundException;
import com.prodapt.learningspring.entity.Comment;
//import com.prodapt.learningspring.entity.CommentId;
//import com.prodapt.learningspring.entity.CommentRecord;
//import com.prodapt.learningspring.entity.CommentId;
//import com.prodapt.learningspring.entity.CommentRecord;
import com.prodapt.learningspring.entity.LikeId;
import com.prodapt.learningspring.entity.LikeRecord;
import com.prodapt.learningspring.entity.Post;
import com.prodapt.learningspring.entity.User;
import com.prodapt.learningspring.model.RegistrationForm;
import com.prodapt.learningspring.repository.CommentCRUDRepository;
//import com.prodapt.learningspring.repository.CommentCRUDRepository;
import com.prodapt.learningspring.repository.LikeCRUDRepository;
import com.prodapt.learningspring.repository.LikeCountRepository;
import com.prodapt.learningspring.repository.PostRepository;
import com.prodapt.learningspring.repository.UserRepository;
import com.prodapt.learningspring.service.DomainUserService;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;

@Controller
@RequestMapping("/forum")
public class ForumController {
  
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private PostRepository postRepository;
  
  @Autowired
  private LikeCountRepository likeCountRepository;
  
  @Autowired
  private LikeCRUDRepository likeCRUDRepository;
  
  @Autowired
  private LoggedInUser loggedInUser;
  
  @Autowired
  private DomainUserService domainUserService;
  
  @Autowired
  private CommentCRUDRepository commentCRUDRepository ;
  
  private List<User> userList;
  
//  @PostConstruct
//  public void init() {
//    userList = new ArrayList<>();
//  }
  
  @GetMapping("/post/form")
//  @NeedsAuth(loginPage = "/loginpage")
  public String getPostForm(Model model,@AuthenticationPrincipal UserDetails userDetails) {
//	  if (this.loggedInUser.getLoggedInUser() == null) {
//          return "redirect:/loginpage";
//      }
//	userList = new ArrayList<>();
//    model.addAttribute("postForm", new AddPostForm());
//    userRepository.findAll().forEach(user -> userList.add(user));
//    model.addAttribute("userList", userList);
//    model.addAttribute("authorid", 0);
	  AddPostForm postForm = new AddPostForm();
	  User author = domainUserService.getByName(userDetails.getUsername()).get();
	  System.out.println("UserId  "+ author.getId());
	  postForm.setUserId(author.getId());
	  model.addAttribute("postForm", postForm);
    return "forum/postForm";
  }
  
  @PostMapping("/post/add")
//  @NeedsAuth(loginPage = "/loginpage")
  public String addNewPost(@ModelAttribute("postForm") AddPostForm postForm, BindingResult bindingResult, RedirectAttributes attr) throws ServletException {
    if (bindingResult.hasErrors()) {
      System.out.println(bindingResult.getFieldErrors());
      attr.addFlashAttribute("org.springframework.validation.BindingResult.post", bindingResult);
      attr.addFlashAttribute("post", postForm);
      return "redirect:/forum/post/form";
    }
//    User log_user = this.loggedInUser.getLoggedInUser();
    Optional<User> user = userRepository.findById(postForm.getUserId());
//    Optional<User> user = userRepository.findById(log_user.getUserId());
    if (user.isEmpty()) {
      throw new ServletException("Something went seriously wrong and we couldn't find the user in the DB");
    }
    Post post = new Post();
    post.setAuthor(user.get());
    post.setContent(postForm.getContent());
    postRepository.save(post);
    
    return String.format("redirect:/forum/post/%d", post.getId());
  }
  
  @GetMapping("/post/{id}")
//  @NeedsAuth(loginPage = "/loginpage")
  public String postDetail(@PathVariable int id, Model model,@AuthenticationPrincipal UserDetails userDetails) throws ResourceNotFoundException {
    Optional<Post> post = postRepository.findById(id);
    if (post.isEmpty()) {
      throw new ResourceNotFoundException("No post with the requested ID");
    }
    model.addAttribute("post", post.get());
    model.addAttribute("userList", userList);
    //int numLikes = likeCountRepository.countByPostId(id);
    model.addAttribute("likerName", userDetails.getUsername());
    model.addAttribute("commenterName", userDetails.getUsername());
    model.addAttribute("comments",commentCRUDRepository.findByPostId(post.get().getId()));
    int numLikes = likeCRUDRepository.countByLikeIdPost(post.get());
    model.addAttribute("likeCount", numLikes);
    return "forum/postDetail";
  }
  
  @PostMapping("/post/{id}/like")
//  @NeedsAuth(loginPage = "/loginpage")
  public String postLike(@PathVariable int id,String likerName, RedirectAttributes attr) {
//	if (this.loggedInUser.getLoggedInUser() == null) {
//          return "redirect:/loginpage";
//    }
	LikeId likeId = new LikeId();
//    Optional<User> user = userRepository.findUserById(this.loggedInUser.getLoggedInUser().getUserId());
	likeId.setUser(userRepository.findByName(likerName).get());
//	User user = this.loggedInUser.getLoggedInUser();
//    likeId.setUser(userRepository.findById(user.get().getUserId()));
//    likeId.setUser(user);
    likeId.setPost(postRepository.findById(id).get());
    LikeRecord like = new LikeRecord();
    like.setLikeId(likeId);
    try {
    likeCRUDRepository.save(like);
    }catch(Exception e) {
    	return String.format("redirect:/forum/post/%d", id);
    }
    return String.format("redirect:/forum/post/%d", id);
  }
  
  @GetMapping("/register")
  public String getRegistrationForm(Model model) {
    if (!model.containsAttribute("registrationForm")) {
      model.addAttribute("registrationForm", new RegistrationForm());
    }
    return "forum/register";
  }

  @PostMapping("/register")
  public String register(@ModelAttribute("registrationForm") RegistrationForm registrationForm, 
  BindingResult bindingResult, 
  RedirectAttributes attr) {
    if (bindingResult.hasErrors()) {
      attr.addFlashAttribute("org.springframework.validation.BindingResult.registrationForm", bindingResult);
      attr.addFlashAttribute("registrationForm", registrationForm);
      return "redirect:/register";
    }
    if (!registrationForm.isValid()) {
      attr.addFlashAttribute("message", "Passwords must match");
      attr.addFlashAttribute("registrationForm", registrationForm);
      return "redirect:/register";
    }
    System.out.println(domainUserService.save(registrationForm.getUsername(), registrationForm.getPassword()));
    attr.addFlashAttribute("result", "Registration success!");
    return "redirect:/login";
  }
  
  @PostMapping("/post/{id}/comment")
  public String addNewComment(@PathVariable int id, String commenterName,@RequestParam("content") String content, RedirectAttributes attr) {
	Comment comment = new Comment();
	comment.setUser(userRepository.findByName(commenterName).get());
	comment.setPost(postRepository.findById(id).get());
	comment.setContent(content);
	commentCRUDRepository.save(comment);
    return  String.format("redirect:/forum/post/%d", id);
  }
}