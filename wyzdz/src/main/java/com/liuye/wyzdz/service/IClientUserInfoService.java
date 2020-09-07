package com.liuye.wyzdz.service;

import com.liuye.wyzdz.entities.ClientUserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuye.common.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author orange
 * @since 2020-09-07
 */
public interface IClientUserInfoService extends IService<ClientUserInfo>, IBaseService<ClientUserInfo> {

    public ClientUserInfo getDetailInfo(Long id);


}
