package com.bigbrotherlee.dto;

import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

import com.bigbrotherlee.validate.MyValidate;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModelProperty;

public class User {
	
	public interface usersimple{}
	public interface userall extends usersimple{}
	
	@NotBlank(message="不能为空")
	private String username;
	
	@ApiModelProperty("swagger注解，对象参数，密码属性")
	@MyValidate
	private String password;
	
	@Past
	private Date birthday;
	
	@JsonView(usersimple.class)
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@JsonView(usersimple.class)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@JsonView(userall.class)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
