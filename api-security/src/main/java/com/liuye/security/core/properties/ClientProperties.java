package com.liuye.security.core.properties;

import lombok.Data;

/**
 * @program: frame
 * @description 客户端参数
 * @author: liuhui
 * @create: 2020-08-31 14:52
 **/
@Data
public class ClientProperties {
    /**
     * session管理配置项
     */
    private SessionProperties session = new SessionProperties();

    /**
     * 登录页面，当引发登录行为的url以html结尾时，会跳到这里配置的url上
     */
    private String signInPage = SecurityConstants.DEFAULT_SIGN_IN_PAGE_URL;
    /**
     * '记住我'功能的有效时间，默认1周
     */
    private int rememberMeSeconds = 604800;

    /**
     * 登录响应的方式，默认是json
     */
    private LoginResponseType signInResponseType = LoginResponseType.JSON;
    /**
     * 登录成功后跳转的地址，如果设置了此属性，则登录成功后总是会跳到这个地址上。
     *
     * 只在signInResponseType为REDIRECT时生效
     */
    private String singInSuccessUrl;

    /**
     * 退出成功时跳转的url，如果配置了，则跳到指定的url，如果没配置，则返回json数据。
     */
    private String signOutUrl;


    /**
     * 所有需要抛出的授权，用， 分割开
     */
    private String permitUrl;

}
