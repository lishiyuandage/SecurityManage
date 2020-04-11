package com.bigbrotherlee.security.core.social.qq.api;

public class QQUserInfo {
	private String ret;//返回码
	private String openId;
	private String msg;//返回消息
	private String nickname;//昵称
	private String figureurl_qq_1;//头像40*40
	private String figureurl_qq_2;//头像100*100
	private String gender;//性别
	public String getRet() {
		return ret;
	}
	public void setRet(String ret) {
		this.ret = ret;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getFigureurl_qq_1() {
		return figureurl_qq_1;
	}
	public void setFigureurl_qq_1(String figureurl_qq_1) {
		this.figureurl_qq_1 = figureurl_qq_1;
	}
	public String getFigureurl_qq_2() {
		return figureurl_qq_2;
	}
	public void setFigureurl_qq_2(String figureurl_qq_2) {
		this.figureurl_qq_2 = figureurl_qq_2;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@Override
	public String toString() {
		return "QQUserInfo [ret=" + ret + ", openId=" + openId + ", msg=" + msg + ", nickname=" + nickname
				+ ", figureurl_qq_1=" + figureurl_qq_1 + ", figureurl_qq_2=" + figureurl_qq_2 + ", gender=" + gender
				+ "]";
	}
	
}
