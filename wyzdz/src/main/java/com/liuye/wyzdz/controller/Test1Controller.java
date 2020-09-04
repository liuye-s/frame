package com.liuye.wyzdz.controller;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.liuye.common.controller.BaseController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuye.common.authorize.core.util.AuthUtil;
import com.liuye.common.authorize.rbac.entities.CommonUser;
import com.liuye.wyzdz.entities.Test1;
import com.liuye.wyzdz.service.ITest1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author orange
 * @since 2020-09-04
 */
@Controller
@RequestMapping("/wyzdz/test1")
public class Test1Controller extends BaseController {

    @Autowired
    private ITest1Service test1ServiceImpl;
    
    @ModelAttribute
    public Test1 get(@RequestParam(required = false) Long id) {
        Test1 entity = null;
        if (id!=null) {
            entity = test1ServiceImpl.getById(id);
        }
        if (entity == null) {
            entity = new Test1();
        }
        return entity;
    }

    @RequestMapping(value = "/list")
    public ModelAndView list(@RequestParam Map<String, Object> data,
                                    HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();

        mav.addObject("data", data);
        mav.setViewName("wyzdz/Test1/list");

        return mav;
    }

    @RequestMapping(value = "queryPage")
    @ResponseBody
    public Map<String, Object> queryPage(
            @RequestParam Map<String, Object> data
            , @RequestParam(required = false) Integer draw
            , @RequestParam(required = false) Integer start
            , @RequestParam(required = false) Integer length
            , Authentication authentication
            , HttpServletRequest request
            , HttpServletResponse response) throws Exception {

        if (start==null || start<0)
            start = 0;
        if (length==null || length<=0)
            length = 10;

        IPage<Test1> page = new Page<Test1>(start/length + 1, length);
        page = test1ServiceImpl.pageByFilter(page, data, null);

        Map<String, Object> map = new HashMap<>();
        map.put("draw", draw);
        map.put("page", page);

        return renderSuccessMap(map);
    }

    @RequestMapping(value = "/input")
    public ModelAndView input(Test1 test1, @RequestParam Map<String, Object> data,
                             HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();

        if (test1!=null)
            data.put("test1", test1);
        mav.addObject("data", data);
        mav.setViewName("wyzdz/Test1/input");

        return mav;
    }

    @RequestMapping(value = "/show")
    public ModelAndView show(Test1 test1, @RequestParam Map<String, Object> data,
                              HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();

        if (test1!=null)
            data.put("test1", test1);
        mav.addObject("data", data);
        mav.setViewName("wyzdz/Test1/show");

        return mav;
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public Map<String, Object> delete(
            @RequestParam Long[] ids
            , Authentication authentication
            , HttpServletRequest request
            , HttpServletResponse response) throws Exception {
        if (ids!=null && ids.length>0) {
            if (ids.length>1) {
                List idList = Arrays.asList(ids);
                test1ServiceImpl.removeByIds(idList);
            } else if (ids.length==1) {
                test1ServiceImpl.removeById(ids[0]);
            }
        }

        return renderSuccess();
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public Map<String, Object> save(
            Test1 test1
            , Authentication authentication
            , HttpServletRequest request
            , HttpServletResponse response) throws Exception {
        CommonUser userInfo = AuthUtil.getUserInfo(authentication);

        if (test1!=null) {
            test1ServiceImpl.saveOrUpdate(test1);
        }

        return renderSuccess();
    }

}

