package com.liuye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: frame
 * @description
 * @author: liuhui
 * @create: 2020-08-31 16:57
 **/
@SpringBootApplication
@ServletComponentScan
@RestController
public class WyzdzApplication {

    public static void main(String[] args){
        SpringApplication.run(WyzdzApplication.class,args);
    }

}
