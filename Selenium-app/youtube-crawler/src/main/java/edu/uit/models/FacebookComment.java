package edu.uit.models;

import java.util.List;

public class FacebookComment {
	private String user;
	private String content;
	private FacebookLike like;
	private List<FacebookComment> comments;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public FacebookLike getLike() {
		return like;
	}
	public void setLike(FacebookLike like) {
		this.like = like;
	}
	public List<FacebookComment> getComments() {
		return comments;
	}
	public void setComments(List<FacebookComment> comments) {
		this.comments = comments;
	}
}
