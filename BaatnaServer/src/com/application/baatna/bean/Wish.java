package com.application.baatna.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

public class Wish implements Serializable {

	private String title;
	private String description;
	private long timeOfPost;
	private int wishId;
	private int userId; // wish posting user
	private int requiredFor; // Number of days for which the wish is required
	private int status;

	public Wish() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getTimeOfPost() {
		return timeOfPost;
	}

	public void setTimeOfPost(long timeOfPost) {
		this.timeOfPost = timeOfPost;
	}

	public int getWishId() {
		return wishId;
	}

	public void setWishId(int wishId) {
		this.wishId = wishId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRequiredFor() {
		return requiredFor;
	}

	public void setRequiredFor(int requiredFor) {
		this.requiredFor = requiredFor;
	}
	
}
