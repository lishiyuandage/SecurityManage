package com.bigbrotherlee.security.core.validate.code.img;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.apache.commons.lang.math.RandomUtils;

import com.bigbrotherlee.security.core.validate.code.ValidateCodeGenerator;

/**
 * @author lee
 * 封装了绘制图片的过程
 */
public abstract class DefaultValidateCodeGenerator implements ValidateCodeGenerator {
	private final String [] FONT= {"黑体","宋体","楷体"};
	private final int [] STYLE= {Font.BOLD,Font.PLAIN,Font.ITALIC,Font.BOLD+Font.ITALIC};
	/**
	 * 得到图片的流BufferedImage对象
	 * @param codeArray 你的验证码数组
	 */
	public BufferedImage getImg(char[] codeArray,int width,int height) {
		BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D bg=(Graphics2D) image.getGraphics();
		bg.setColor(Color.WHITE);
		bg.fillRect(0, 0, width, height);
		for(int index=0;index<codeArray.length;index++) {
			String get_code=codeArray[index]+"";
			bg.setColor(randomColor());
			bg.setFont(randomFont());
			bg.drawString(get_code, 2+index*22,36-RandomUtils.nextInt(3));
		}
		for(int index=0;index<4;index++) {
			bg.setColor(randomColor());
			bg.setStroke(new BasicStroke(2));//设置笔画粗细
			bg.drawLine(RandomUtils.nextInt(width),RandomUtils.nextInt(40) , RandomUtils.nextInt(width), RandomUtils.nextInt(40));
		}
		return image;
	}

	//得到随机字体
	private Font randomFont() {
		return new Font(FONT[RandomUtils.nextInt(3)],STYLE[RandomUtils.nextInt(4)],38);
	}
	//得到随机颜色
	private Color randomColor() {
		int red=RandomUtils.nextInt(150);
		int green=RandomUtils.nextInt(150);
		int blue=RandomUtils.nextInt(150);
		return new Color(red,green,blue);
		
	}
}
