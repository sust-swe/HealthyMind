package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.model.Post;
import com.example.demo.model.PostHelper;
import com.example.demo.model.UploadFileResponse;
//import com.care.care.userregistration.model.Tag;
//import com.care.care.userregistration.model.TagList;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
//import com.example.demo.repository.TagRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.FileStorageService;


@Controller
public class PostController {
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private FileStorageService fileStorageService;
	//@Autowired
	//private TagRepository tagRepository;
	@Autowired
	private UserRepository userRepository;
	
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getAllPost",method = RequestMethod.GET)
    public List<Post> getAll(){
    	return postRepository.getAllPost();
    }
    
	
	@CrossOrigin
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file); 
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
       
        
        return fileDownloadUri;
    }
	
	
	@RequestMapping(value = "/blog/{id}", method = RequestMethod.GET)
	public List<Post> getAllPostByUserID(@PathVariable Long id){
		return postRepository.findAllByUserID(id);
	}
	@GetMapping(value = "/blog/{blogId}")
	public Post postDetails(@PathVariable Long articleId) {
		return postRepository.getOne(articleId);
	}
	
	@CrossOrigin
	@PostMapping(value = "/blog/create")
	@ResponseBody
	public String createPost(@RequestPart("file") MultipartFile file,@RequestPart("post") PostHelper post,@RequestParam(value = "userID") int userID ) {
		Post postToSubmit = new Post();
		postToSubmit.setBody(post.getBody());
		String imageUrl = uploadFile(file);
		postToSubmit.setcontentHtml(imageUrl);
		User user = userRepository.findByUserid(userID);
		postToSubmit.setUser(user);
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
