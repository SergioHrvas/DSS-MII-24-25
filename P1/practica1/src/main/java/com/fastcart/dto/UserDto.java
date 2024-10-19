package com.fastcart.dto;

import com.fastcart.model.Role;

public class UserDto {

	 private String username;
	 private String password;

	 private Role role;
	 
	 public UserDto(String username, String password) {
	  super();
	  this.username = username;
	  this.password = password;
	 }

	 public String getUsername() {
	  return username;
	 }

	 public void setUsername(String username) {
	  this.username = username;
	 }

	 public String getPassword() {
	  return password;
	 }

	 public void setPassword(String password) {
	  this.password = password;
	 }

	 public Role getRole() {
		  return role;
	 }

	 public void setRole(Role role) {
		  this.role = role;
	 }

	 @Override
	 public String toString() {
	  return "UserDto [username=" + username + ", password=" + password + "]";
	 }
}