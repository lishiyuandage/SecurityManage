package com.bigbrotherlee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("pay")
public class PayController {
	/*
	 * 微信 wxp://f2f0aBKjuZO_MojlsBqyuHLQdctOx8nBqsxW 支付宝
	 * https://qr.alipay.com/fkx041951tf8u9nceq92s73
	 */
	@GetMapping("payunit")
	public String payUtil(String payId) {
		if(payId==null || payId.isEmpty()) {
			return "redirect:https://qr.alipay.com/fkx041951tf8u9nceq92s73";
		}
		if(payId.equals("pay")) {
			return "redirect:https://vac.qq.com/wallet/qrcode.htm?m=tenpay&a=1&u=1798390978&ac=0F5EBA1F54F811516CED292E33E75C0DF063DC3C6DACAD291A98B1CDF4108FA9&n=Zohar%20Wang&f=wallet";
		}
		return "redirect:wxp://f2f0aBKjuZO_MojlsBqyuHLQdctOx8nBqsxW";
	}
}