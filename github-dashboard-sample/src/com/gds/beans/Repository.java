package com.gds.beans;

import java.util.Date;

public class Repository {
	private String name;
	private String language;
	private int stars;
	private String description;
	private Date creationDate;
	private Date updateDate;
	private String githubLink;
	
	public Repository(String name) {
		this.name = name;
	}
	
	public Repository() {
	}

	public String getName() {
		return this.name;
	}
	
	public int getStars() {
		return this.stars;
	}
	
	public String getLanguage() {
		return this.language;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Date getCreationDate() {
		return this.creationDate;
	}
	
	public Date getUpdateDate() {
		return this.updateDate;
	}
	
	public String getGithubLink() {
		return this.githubLink;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setStars(int stars) {
		this.stars = stars;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public void setGithubLink(String githubLink) {
		this.githubLink = githubLink;
	}
}
