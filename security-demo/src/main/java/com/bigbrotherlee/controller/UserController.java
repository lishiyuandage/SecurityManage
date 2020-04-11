package com.bigbrotherlee.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.bigbrotherlee.dto.User;
import com.bigbrotherlee.dto.User.userall;
import com.bigbrotherlee.dto.User.usersimple;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("user")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private ProviderSignInUtils providerSignInUtils;
	
	@JsonView(usersimple.class)
	@GetMapping
	public List<User> user(@ApiParam("swagger注解，简单参数描述，用户名") String name){
		List<User> list=new ArrayList<User>();
		User u=new User();
		u.setUsername(name);
		u.setPassword("1423153");
		list.add(u);
		list.add(new User());
		list.add(new User());
		return list;
	}
	
	@PostMapping("regist")
	private void register(User user,HttpServletRequest request) {
		log.info("regist"+user);
		String userId=user.getUsername();
		providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));//社交账号注册
	}
	
	//取得已经认证的用户信息
	@GetMapping("/me")
	public Object getMe(Authentication authentication,@AuthenticationPrincipal  UserDetails user) {
		System.out.println(user);
		return authentication;
	}
	
	@JsonView(userall.class)
	@GetMapping("/{username:\\d+}")//嵌入正则表达式
	public User  queryUser(@PathVariable String username) {
		User u=new User();
		u.setUsername(username);
		u.setPassword("1423153");
		return u;
	}
	
	@JsonView(userall.class)
	@PostMapping("/create")
	public User  create(@Valid @RequestBody User u,BindingResult result) {
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));//打印错误信息
		}
		
		System.out.println(u.getBirthday()+"=========================================");
		return u;
	}
	
	@JsonView(userall.class)
	@PutMapping("/{username:\\d+}")//嵌入正则表达式
	public User  update(@Valid @RequestBody User u,BindingResult result) {
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> {
				FieldError fieldError=(FieldError)error;
				System.out.println(fieldError.getField()+"---- :"+error.getDefaultMessage());
			});//打印错误信息
		}
		
		System.out.println(u.getBirthday()+"=========================================");
		return u;
	}
}
