package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.model.ConfirmationToken;
import com.example.demo.model.PostHelper;
import com.example.demo.model.User;
import com.example.demo.model.UserHelper;
import com.example.demo.service.EmailSenderService;
import com.example.demo.service.FileStorageService;
import com.github.javafaker.File;
import com.example.demo.repository.ConfirmationTokenRepository;
import com.example.demo.repository.UserRepository;


@Controller
public class UserAccountController {

	@Autowired
	private FileStorageService fileStorageService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	
	@CrossOrigin
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file); 
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
       
        
        return fileDownloadUri;
    }

	
	@RequestMapping(value="/emni",method = RequestMethod.POST)
	@ResponseBody
	public String registerUser(@RequestBody User user)
	{
		
		User existingUser = userRepository.findByEmailIdIgnoreCase(user.getEmailId());
		if(existingUser != null)
		{
			return "Another user exists";
		}
		else 
		{
			userRepository.save(user);
			
			ConfirmationToken confirmationToken = new ConfirmationToken(user);
			
			confirmationTokenRepository.save(confirmationToken);
			
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(user.getEmailId());
			mailMessage.setSubject("Complete Registration!");
			mailMessage.setFrom("sahasamapty@gmail.com");
			mailMessage.setText("To confirm your account, please click here : "
			+"http://localhost:8081/confirm-account?token="+confirmationToken.getConfirmationToken());
			
			emailSenderService.sendEmail(mailMessage);
			return "Success";
			
		}
		
	}
	@CrossOrigin
	@PostMapping(value="/login")
	@ResponseBody
	public User login(@RequestPart("user") UserHelper user) {
		User details= userRepository.findByNameandPassword(user.getName(), user.getPassword());
		return details;
		
	}
	
	@RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
	public String confirmUserAccount( @RequestParam("token")String confirmationToken)
	{
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
		
		if(token != null)
		{
			User user = userRepository.findByEmailIdIgnoreCase(token.getUser().getEmailId());
			user.setEnabled(true);
			userRepository.save(user);
			return "accountVerified";		
		}
		else
		{
			return "error";
		}
		
	}
	@CrossOrigin
	@RequestMapping(value="/registration",method = RequestMethod.POST)
	@ResponseBody
	public String emni(@RequestPart("user") UserHelper user) {
		User existingUser = userRepository.findByEmailIdIgnoreCase(user.getEmail());
		if(existingUser != null)
		{
			return "Another user exists";
		}else {
			User temp = new User();
			temp.setName(user.getName());
			temp.setPassword(user.getPassword());
			temp.setEmailId(user.getEmail());
			temp.setType("user");
		//	ConfirmationToken confirmationToken = new ConfirmationToken(temp);
			
		//	confirmationTokenRepository.save(confirmationToken);
			userRepository.save(temp);
//			SimpleMailMessage mailMessage = new SimpleMailMessage();
//			mailMessage.setTo(temp.getEmailId());
//			mailMessage.setSubject("Complete Registration!");
//			mailMessage.setFrom("sahasamapty@gmail.com");
//			mailMessage.setText("To confirm your account, please click here : "
//			+"http://localhost:8081/confirm-account?token="+confirmationToken.getConfirmationToken());
//			
//			emailSenderService.sendEmail(mailMessage);
			return "Success";
		}

	}
	@CrossOrigin
	@RequestMapping(value="/profile/update",method = RequestMethod.PUT)
	@ResponseBody
	public String test(@RequestPart("user") UserHelper user,@RequestPart("file") MultipartFile file,@RequestParam("userID")int id) {

		User exist = userRepository.findByUserid(id);

		System.out.println(exist.getEmailId());
		
		if(file!=null) {
			String imageURL = uploadFile(file);
			exist.setImageURL(imageURL);
		}
		
		if(user.getEmail() != null)
		exist.setEmailId(user.getEmail());
		
		if(user.getGender()!= null)
		exist.setGender(user.getGender());
		
		if(user.getName()!= null)
		exist.setName(user.getName());
		
		if(user.getBio()!= null)
		exist.setBio(user.getBio());
		
		if(user.getPhn_no()!= null)
		exist.setPhn_no(user.getPhn_no());
		
		if(user.getProfession()!= null)
		exist.setProfession(user.getProfession());
		
		
		userRepository.save(exist);
		return "Success";
	}
	
	
	@CrossOrigin
	@ResponseBody
	@RequestMapping(value="/getOneUser", method=RequestMethod.GET)
	public User getOne(@RequestParam("userId") int userId)
	{
		return userRepository.findByUserid(userId);
	}
	
	

}
