package com.liuye.security.core.properties;

import lombok.Data;

/**
 * @program: frame
 * @description
 * @author: liuhui
 * @create: 2020-08-31 15:04
 **/
@Data
public class SmsCodeProperties {

    /**
     * 验证码长度
     */
    private int length = 6;
    /**
     * 过期时间
     */
    private int expireIn = 300;
    /**
     * 要拦截的url，多个url用逗号隔开，ant pattern
     */
    private String url;
}
