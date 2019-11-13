package com.example.demo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "account")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="user_id")
	private int userid;
	@Column(name = "name")
	private String name;
	
	
	private String emailId;
	private String password;
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	//private Date Date;
	private boolean isEnabled;
	@OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Post> posts;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Comment> comments;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	/*public Date getDueDate() {
		return Date;
	}
	public void setDueDate(Date Date) {
		this.Date = Date;
	}*/
	@Column(name="gender")
	private String gender;

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name="profession")
	private String profession;
	@Column(name="phn_no")
	private String phn_no;
	@Column(name="bio")
	private String bio;
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getPhn_no() {
		return phn_no;
	}
	public void setPhn_no(String phn_no) {
		this.phn_no = phn_no;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public boolean isEnabled() {
		return isEnabled;
	}
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	
	
}
