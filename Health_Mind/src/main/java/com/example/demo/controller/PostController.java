package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Post;
import com.example.demo.model.PostHelper;
//import com.care.care.userregistration.model.Tag;
//import com.care.care.userregistration.model.TagList;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
//import com.example.demo.repository.TagRepository;
import com.example.demo.repository.UserRepository;


@Controller
public class PostController {
	@Autowired
	private PostRepository postRepository;
	//@Autowired
	//private TagRepository tagRepository;
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value = "/blog/{id}", method = RequestMethod.GET)
	public List<Post> getAllPostByUserID(@PathVariable Long id){
		return postRepository.findAllByUserID(id);
	}
	@GetMapping(value = "/blog/{blogId}")
	public Post postDetails(@PathVariable Long articleId) {
		return postRepository.getOne(articleId);
	}
	
	@PostMapping(value = "/blog/create",consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String createPost(@RequestBody PostHelper post,@RequestParam(value = "userID") int userID ) {
		Post postToSubmit = new Post();
		//postToSubmit.setTitle(post.getTitle());
		postToSubmit.setBody(post.getBody());
		//postToSubmit.setAgeLimit(post.getAgeLimit());
		//postToSubmit.setcontentHtml(post.getContentHTML());
		User user = userRepository.findByUserid(userID);
		postToSubmit.setUser(user);
		//postToSubmit.setTags(post.getTaging());
		//List<Tag>existingList  = post.getTaging();
//		for(Tag temp : post.getTaging()) {
//			if(existingList.contains(temp))
//				continue;
//			else tagRepository.save(temp);
//		}
		postRepository.save(postToSubmit);
		
		return "success";
	}
	@RequestMapping(value = "/blog/delete/{blogID}",method = RequestMethod.GET)
	@ResponseBody
	public String deletePost(@PathVariable Long blogID) {
		postRepository.delete(postRepository.getOne(blogID));
		return "success";
	}
	@RequestMapping(value="/blog/update")
	@ResponseBody
	public String postUpdate(@RequestBody PostHelper post,@RequestParam(value = "postID")int id) {
		Long pId = Long.parseLong(Integer.toString(id));
		Post exist = postRepository.getOne(pId);
		exist.setBody(post.getBody());
		postRepository.save(exist);
		return "SUccess";
	}
}
