package com.liuye.common.util.crypto;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;

import static com.liuye.common.util.crypto.Encrypter.KEY_MD5;

/**
 * @program: baseframe
 * @description
 * @author: liuhui
 * @create: 2019-05-19 09:54
 **/
public class MyPasswordEncoder  implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        String md5Str = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
            md5.update(((String)rawPassword).getBytes());
            byte b[] = md5.digest();
            StringBuffer buf = new StringBuffer("");
            for (int i : b) {
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            md5Str = buf.toString();// 32位的加密
        } catch (Exception e) {
            md5Str = "";
            e.printStackTrace();
        }
        return md5Str;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

        if(this.encode(rawPassword) == null){
            return false;
        }else if((this.encode(rawPassword)).equals(encodedPassword)){
            return true;
        }
        return false;
    }

    public static  void main(String[] args){
        MyPasswordEncoder myPasswordEncoder = new MyPasswordEncoder();
        System.out.println(myPasswordEncoder.encode("123456"));
    }
}
