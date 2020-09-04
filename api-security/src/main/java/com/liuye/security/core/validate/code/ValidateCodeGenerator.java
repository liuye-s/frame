package com.liuye.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @program: epp
 * @description 校验码生成器
 * @author: liuhui
 * @create: 2019-04-22 22:19
 **/
public interface ValidateCodeGenerator {

    /**
     * 生成校验码
     * @param request
     * @return
     */
    ValidateCode generate(ServletWebRequest request);
}
