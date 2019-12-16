package com.example.demo.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Comment;
import com.example.demo.model.CommentHelper;
import com.example.demo.model.Post;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
//import com.care.care.userregistration.service.repository.TagRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class CommentController {
	
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CommentRepository commentRepository;
	
	
	@CrossOrigin
	@RequestMapping(value="/createComment",method = RequestMethod.POST)
	@ResponseBody
	public String createComment(@RequestPart("comment") CommentHelper commentHelper){
		
		Comment comment = new Comment();
		comment.setBody(commentHelper.getContent());
		Post post = postRepository.getOne(commentHelper.getPostId());
		comment.setPost(post);
		comment.setUser(userRepository.findByUserid(commentHelper.getUserId()));
		commentRepository.save(comment);
		return "success";
	}
	
	
	@CrossOrigin
	@ResponseBody
	@RequestMapping(value = "/commentPost", method = RequestMethod.GET)
	 public List<Comment> commentPostWithId(@RequestParam("id") int id) {
		
		return commentRepository.getCommentByPostId(id);
	}
}


