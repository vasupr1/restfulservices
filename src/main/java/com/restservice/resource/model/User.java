package com.restservice.resource.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Bean class for User
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

  @JsonProperty("username")
  private String username;

  @JsonProperty("phone")
  private String phone;

  @JsonProperty("password")
  private String password;
  
  @JsonProperty("id")
  private Long id;
  
  @JsonProperty("token")
  private String token;
  
  public String getToken() {
	return token;
}

public void setToken(String token) {
	this.token = token;
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getPhone() {
	return phone;
}

public void setPhone(String phone) {
	this.phone = phone;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

  

}
