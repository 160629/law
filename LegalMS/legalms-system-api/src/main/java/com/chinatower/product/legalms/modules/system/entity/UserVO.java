package com.chinatower.product.legalms.modules.system.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class UserVO {
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
	@NotEmpty(message = "登录名不能为空")
	@Column(name = "user_login_name")
    private String userLoginName;
	@NotEmpty(message = "密码不能为空")
	@Column(name = "user_login_pass")
    private String userLoginPass;
	@NotEmpty(message = "电话号码不能为空")
	@Column(name = "user_tel")
    private String userTel;
	@NotEmpty(message = "邮箱不能为空")
	@Column(name = "user_email")
    private String userEmail;

	private Date birthday;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	public String getUserLoginPass() {
		return userLoginPass;
	}

	public void setUserLoginPass(String userLoginPass) {
		this.userLoginPass = userLoginPass;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public UserVO(String userLoginName, Date birthday) {
		this.userLoginName = userLoginName;
		this.birthday = birthday;
	}
	public UserVO() {

	}
}