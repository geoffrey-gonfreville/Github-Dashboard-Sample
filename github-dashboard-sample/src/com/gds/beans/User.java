package com.gds.beans;

import java.util.List;

public class User {
	private String login;
	private String name;
	private String avatar;
	private List<Repository> repositories;
	
	public String getLogin() {
		return this.login;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getAvatar() {
		return this.avatar;
	}
	
	public List<Repository> getRepositories(){
		return this.repositories;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public void setRepositories(List<Repository> repositories) {
		this.repositories = repositories;
	}
}
