package com.liuye.security.core.properties;

import lombok.Data;

/**
 * @program: frame
 * @description
 * @author: liuhui
 * @create: 2020-08-31 15:04
 **/
@Data
public class ImageCodeProperties  extends SmsCodeProperties {

    public ImageCodeProperties() {
        setLength(4);
    }

    /**
     * 图片宽
     */
    private int width = 60;
    /**
     * 图片高
     */
    private int height = 40;
}
