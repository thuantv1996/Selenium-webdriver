package edu.uit.models;

import java.util.List;

//class comment
public class Comment {
	private String userName;
	private String content;
	private int numberLike;
	private int numberDislike;	
	private List<Comment> comments;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getNumberLike() {
		return numberLike;
	}

	public void setNumberLike(int numberLike) {
		this.numberLike = numberLike;
	}

	public int getNumberDislike() {
		return numberDislike;
	}

	public void setNumberDislike(int numberDislike) {
		this.numberDislike = numberDislike;
	}

	public Comment(String userName, String content, int numberLike, int numberDislike) {
		super();
		this.userName = userName;
		this.content = content;
		this.numberLike = numberLike;
		this.numberDislike = numberDislike;
	}
	
	public Comment() {}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}