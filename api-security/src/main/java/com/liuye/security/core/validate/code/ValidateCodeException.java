package com.liuye.security.core.validate.code;
import org.springframework.security.core.AuthenticationException;

/**
 * @program: epp
 * @description 验证码异常类
 * @author: liuhui
 * @create: 2019-04-22 22:16
 **/
public class ValidateCodeException extends AuthenticationException {

    /**
     *
     */
    private static final long serialVersionUID = -7285211528095468156L;

    public ValidateCodeException(String msg) {
        super(msg);
    }

}
