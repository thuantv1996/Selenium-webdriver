package edu.uit.models;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "FacebookData")
public class FacebookData {
	@Id private ObjectId _id;
	private String user;
	private String status;
	private FacebookLike like;
	private long numberComment;
	private List<FacebookComment> comments;
	
	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public FacebookLike getLike() {
		return like;
	}
	public void setLike(FacebookLike like) {
		this.like = like;
	}
	public long getNumberComment() {
		return numberComment;
	}
	public void setNumberComment(long numberComment) {
		this.numberComment = numberComment;
	}

	public List<FacebookComment> getComments() {
		return comments;
	}

	public void setComments(List<FacebookComment> comments) {
		this.comments = comments;
	}


}
