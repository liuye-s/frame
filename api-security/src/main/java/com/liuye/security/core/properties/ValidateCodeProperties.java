package com.liuye.security.core.properties;

import lombok.Data;

/**
 * @program: frame
 * @description
 * @author: liuhui
 * @create: 2020-08-31 15:04
 **/
@Data
public class ValidateCodeProperties {

    /**
     * 图片验证码配置
     */
    private ImageCodeProperties image = new ImageCodeProperties();
    /**
     * 短信验证码配置
     */
    private SmsCodeProperties sms = new SmsCodeProperties();

}
