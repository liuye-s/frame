package com.liuye.wyzdz.service.impl;

import com.liuye.wyzdz.entities.ClientUserInfo;
import com.liuye.wyzdz.dao.ClientUserInfoMapper;
import com.liuye.wyzdz.service.IClientUserInfoService;
import com.liuye.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author orange
 * @since 2020-09-07
 */
@Service
@Transactional(readOnly = true)
public class ClientUserInfoServiceImpl extends BaseService<ClientUserInfoMapper, ClientUserInfo> implements IClientUserInfoService {

    @Autowired
    public ClientUserInfoMapper clientUserInfoMapper;
    @Override
    public ClientUserInfo getDetailInfo(Long id) {
        return clientUserInfoMapper.getDetailInfo(id);
    }
}
