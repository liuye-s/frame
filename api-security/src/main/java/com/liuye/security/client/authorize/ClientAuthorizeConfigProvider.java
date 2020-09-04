package com.liuye.security.client.authorize;

import com.liuye.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;


/**
 * @program: baseframe
 * @description 浏览器环境默认的授权配置，对常见的静态资源，如js,css，图片等不验证身份
 * @author: liuhui
 * @create: 2019-05-19 16:50
 **/
@Component
@Order(Integer.MIN_VALUE)
public class ClientAuthorizeConfigProvider implements AuthorizeConfigProvider {

	/* (non-Javadoc)
	 * @see com.imooc.security.core.authorize.AuthorizeConfigProvider#config(org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry)
	 */
	@Override
	public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		config.antMatchers(HttpMethod.GET,
			"/**/*.js",
			"/**/*.css",
			"/**/*.jpg",
			"/**/*.png",
			"/**/*.woff2",
			"/**/*.woff",
			"/**/*.ttf",
			"/**/*.ico",
			"/**/*.map",
			"/**/*.gif",
			"/**/*.svg").permitAll();
		return false;
	}

}
