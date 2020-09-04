package com.liuye.security.core.properties;

import lombok.Data;

/**
 * @program: epp
 * @description session管理相关配置项
 * @author: liuhui
 * @create: 2020-08-31 14:56
 **/
@Data
public class SessionProperties {
    /**
     * 同一个用户在系统中的最大session数，默认1
     */
    private int maximumSessions = 1;
    /**
     * 达到最大session时是否阻止新的登录请求，默认为false，不阻止，新的登录会将老的登录失效掉
     */
    private boolean maxSessionsPreventsLogin ;
    /**
     * session失效时跳转的地址
     */
    private String sessionInvalidUrl = SecurityConstants.DEFAULT_SESSION_INVALID_URL;

    /**
     * 不限制同一账户的并发登陆，缺省为false
     */
    private Boolean unlimitConcurrentSession = false;


}
