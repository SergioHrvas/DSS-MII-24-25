package com.fastcart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastcart.dto.UserDto;
import com.fastcart.model.Role;
import com.fastcart.model.User;
import com.fastcart.repository.UserRepo;
import com.fastcart.service.UserService;

@RestController
@RequestMapping("/api/user")

public class UserController {
	
    @Autowired
	UserService userService;
	
    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody UserDto user) {
		System.out.println(user);
		User new_user = userService.register(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new_user);
   
		
    }
}
