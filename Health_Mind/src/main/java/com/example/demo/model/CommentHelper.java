package com.example.demo.model;

public class CommentHelper {
private String content;
private int userId;
private int postId;



public CommentHelper() {
	super();
}
public CommentHelper(String content, int userId, int postId) {
	super();
	this.content = content;
	this.userId = userId;
	this.postId = postId;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public int getUserId() {
	return userId;
}
public void setUserId(int userId) {
	this.userId = userId;
}
public int getPostId() {
	return postId;
}
public void setPostId(int postId) {
	this.postId = postId;
}

}
