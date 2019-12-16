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
	
	@Autowired
	private UserRepository userRepository;
	
	
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getAllPost",method = RequestMethod.GET)
    public List<Post> getAll(){
    	return postRepository.getAllPost();
    }
    
    
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getAllPostForAdmin",method = RequestMethod.GET)
    public List<Post> getAllForAdmin(){
    	return postRepository.getAllPostForAdmin();
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
	
	
	@CrossOrigin
	@RequestMapping(value = "/blog/getMyPost/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Post> getAllPostByUserID(@PathVariable int id){
		return postRepository.findAllByUserID(id);
	}
	
	@CrossOrigin
	@GetMapping(value = "/blog/{blogId}")
	@ResponseBody
	public Post postDetails(@PathVariable int articleId) {
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
		postToSubmit.setApproved(false);
		postRepository.save(postToSubmit);
		
		return "success";
	}
	
	
	@CrossOrigin
	@RequestMapping(value = "/blog/delete/{blogID}",method = RequestMethod.DELETE)
	@ResponseBody
	public String deletePost(@PathVariable int blogID) {
		postRepository.delete(postRepository.getOne(blogID));
		return "success";
	}
	
	
	@CrossOrigin
	@RequestMapping(value="/blog/update",method = RequestMethod.PUT)
	@ResponseBody
	public String postUpdate(@RequestPart("file") MultipartFile file,@RequestPart("post") PostHelper post,@RequestParam(value = "postId") int postId) {

		
		Post exist = postRepository.getOne(postId);
		
		
		if(!file.isEmpty()) {
			String imageUrl = uploadFile(file);
			exist.setcontentHtml(imageUrl);
		}
		else {
			exist.setcontentHtml(post.getContentHTML());
		}
		
		if(!post.getBody().isEmpty()) {
		exist.setBody(post.getBody());
		}
		postRepository.save(exist);
		return "Success";
	}
	
	@CrossOrigin
	@RequestMapping(value = "/blog/approve/{blogID}",method = RequestMethod.PUT)
	@ResponseBody
	public String approvePost(@PathVariable int blogID) {
		Post temp = postRepository.getOne(blogID);
		temp.setApproved(true);
		postRepository.save(temp);
		return "success";
	}
		
}
