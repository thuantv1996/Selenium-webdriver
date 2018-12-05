package edu.uit.models;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "YoutubeData")
public class YoutubeData {

	@Id private ObjectId _id;
	private String  classification;
	private String  title;
	private String channel;
	private long  numberView;
	private long  numberLike;
	private long  numberDislike;
	private long  numberComment;
	private List<Comment> comments;
	
	// constructor
	public YoutubeData() {}
	
	// constructor
	public YoutubeData(ObjectId _id, String classification, String title, String channel, long numberView,
			long numberLike, long numberDislike, long numberComment, List<Comment> comments) {
		super();
		this._id = _id;
		this.classification = classification;
		this.title = title;
		this.channel = channel;
		this.numberView = numberView;
		this.numberLike = numberLike;
		this.numberDislike = numberDislike;
		this.numberComment = numberComment;
		this.comments = comments;
	}
	
	// getter and setter
	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public long getNumberView() {
		return numberView;
	}

	public void setNumberView(long numberView) {
		this.numberView = numberView;
	}

	public long getNumberLike() {
		return numberLike;
	}

	public void setNumberLike(long numberLike) {
		this.numberLike = numberLike;
	}

	public long getNumberDislike() {
		return numberDislike;
	}

	public void setNumberDislike(long numberDislike) {
		this.numberDislike = numberDislike;
	}

	public long getNumberComment() {
		return numberComment;
	}

	public void setNumberComment(long numberComment) {
		this.numberComment = numberComment;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
