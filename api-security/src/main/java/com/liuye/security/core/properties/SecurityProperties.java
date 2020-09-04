package com.liuye.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: frame
 * @description
 * @author: liuhui
 * @create: 2020-08-31 14:56
 **/
@Component
@ConfigurationProperties(prefix = "liuye.security") ///配置注解表示这个类可以读取所有yml以 "liuye.security"开头的配置项
public class SecurityProperties {

    /**
     * 浏览器环境配置 //liuye.security.browser 读取到browser 对象里面
     */
    private ClientProperties clientProperties = new ClientProperties();

    public ClientProperties getClientProperties() {
        return clientProperties;
    }

    public void setClientProperties(ClientProperties clientProperties) {
        this.clientProperties = clientProperties;
    }

    /**
     * 验证码配置
     */
    private ValidateCodeProperties code = new ValidateCodeProperties();

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }

    /**
     * RBAC用户生成服务实现类
     */
    private String rbacUserService = SecurityConstants.DEFAULT_RBAC_USER_SERVICE;

    public String getRbacUserService() {
        return rbacUserService;
    }

    public void setRbacUserService(String rbacUserService) {
        this.rbacUserService = rbacUserService;
    }


}
