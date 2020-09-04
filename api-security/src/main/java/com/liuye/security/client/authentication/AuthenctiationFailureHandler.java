package com.liuye.security.client.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liuye.security.core.properties.LoginResponseType;
import com.liuye.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @program: baseframe
 * @description  浏览器环境下登录失败的处理器
 *  * 模块默认的配置
 * @author: liuhui
 * @create: 2019-05-19 16:18
 **/
@Component("authenctiationFailureHandler")
public class AuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	public AuthenctiationFailureHandler(SecurityProperties securityProperties) {
		super(securityProperties.getClientProperties().getSignInPage());
	}

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
		
		logger.info("登录失败");
		exception.printStackTrace();

		Map<String,Object> map= new HashMap<>();
		map.put("success", false);
		map.put("message", exception.getMessage());


		response.getWriter().write(objectMapper.writeValueAsString(map));

		if (LoginResponseType.JSON.equals(securityProperties.getClientProperties().getSignInResponseType())) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json;charset=UTF-8");

			response.getWriter().write(objectMapper.writeValueAsString(map));

//			response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
		}else{

			super.onAuthenticationFailure(request, response, exception);

		}
		
	}
}
