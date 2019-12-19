package com.example.demo.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class PostHelper {
	
	private String body,contentHTML;
	
	
	
	public PostHelper(String body, String contentHTML) {
		super();
		this.body = body;
		this.contentHTML = contentHTML;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getContentHTML() {
		return contentHTML;
	}
	public void setContentHTML(String contentHTML) {
		this.contentHTML = contentHTML;
	}
	public PostHelper() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
}
