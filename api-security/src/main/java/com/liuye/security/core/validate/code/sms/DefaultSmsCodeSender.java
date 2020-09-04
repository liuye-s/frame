package com.liuye.security.core.validate.code.sms;


import com.liuye.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 默认的短信验证码发送器
 *
 * @author zhailiang
 *
 */
public class DefaultSmsCodeSender implements SmsCodeSender {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 系统配置
	 */
	@Autowired
	private SecurityProperties securityProperties;

	/* (non-Javadoc)
	 * @see com.imooc.security.core.validate.code.sms.SmsCodeSender#send(java.lang.String, java.lang.String)
	 */
	@Override
	public void send(String mobile, String code) {


		logger.warn("请配置真实的短信验证码发送器(SmsCodeSender)");
		logger.warn("向手机"+mobile+"发送短信验证码"+code);


	}

}
