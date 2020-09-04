package com.liuye.wyzdz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: frame
 * @description
 * @author: liuhui
 * @create: 2020-09-04 09:01
 **/
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
