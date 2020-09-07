package com.liuye.wyzdz.controller;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.liuye.common.controller.BaseController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuye.common.authorize.core.util.AuthUtil;
import com.liuye.common.authorize.rbac.entities.CommonUser;
import com.liuye.wyzdz.entities.ClientUserInfo;
import com.liuye.wyzdz.service.IClientUserInfoService;
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
 * @since 2020-09-07
 */
@Controller
@RequestMapping("/wyzdz/clientUserInfo")
public class ClientUserInfoController extends BaseController {

    @Autowired
    private IClientUserInfoService clientUserInfoServiceImpl;

    @RequestMapping(value = "/getById")
    public @ResponseBody ClientUserInfo get(@RequestParam(required = false) Long id) {
        ClientUserInfo entity = null;
        if (id!=null) {
            entity = clientUserInfoServiceImpl.getById(id);
        }
        if (entity == null) {
            entity = new ClientUserInfo();
        }
        return entity;
    }

    @RequestMapping(value = "/list")
    public ModelAndView list(@RequestParam Map<String, Object> data,
                                    HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();

        mav.addObject("data", data);
        mav.setViewName("wyzdz/ClientUserInfo/list");

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

        IPage<ClientUserInfo> page = new Page<ClientUserInfo>(start/length + 1, length);
        page = clientUserInfoServiceImpl.pageByFilter(page, data, null);

        Map<String, Object> map = new HashMap<>();
        map.put("draw", draw);
        map.put("page", page);

        return renderSuccessMap(map);
    }

    @RequestMapping(value = "/input")
    public ModelAndView input(ClientUserInfo clientUserInfo, @RequestParam Map<String, Object> data,
                             HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();

        if (clientUserInfo!=null)
            data.put("clientUserInfo", clientUserInfo);
        mav.addObject("data", data);
        mav.setViewName("wyzdz/ClientUserInfo/input");

        return mav;
    }

    @RequestMapping(value = "/show")
    public ModelAndView show(ClientUserInfo clientUserInfo, @RequestParam Map<String, Object> data,
                              HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();

        if (clientUserInfo!=null)
            data.put("clientUserInfo", clientUserInfo);
        mav.addObject("data", data);
        mav.setViewName("wyzdz/ClientUserInfo/show");

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
                clientUserInfoServiceImpl.removeByIds(idList);
            } else if (ids.length==1) {
                clientUserInfoServiceImpl.removeById(ids[0]);
            }
        }

        return renderSuccess();
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public Map<String, Object> save(
            ClientUserInfo clientUserInfo
            , Authentication authentication
            , HttpServletRequest request
            , HttpServletResponse response) throws Exception {
        CommonUser userInfo = AuthUtil.getUserInfo(authentication);

        if (clientUserInfo!=null) {
            clientUserInfo.setUpdateTime(new Date());
            clientUserInfoServiceImpl.saveOrUpdate(clientUserInfo);
        }

        return renderSuccess();
    }

}

