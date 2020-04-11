package com.bigbrotherlee.security.core.validate.code.img;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

import com.bigbrotherlee.security.core.validate.code.ValidateCode;

/**
 * @author lee
 * 图片验证码对象
 */
public class ImageCode extends ValidateCode{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2215934547576466347L;
	private BufferedImage image;//图片
	
	
	public ImageCode(BufferedImage image, String code, int expireIn) {
		super(code,expireIn);
		this.image = image;
	}
	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
		super(code,expireTime);
		this.image = image;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
