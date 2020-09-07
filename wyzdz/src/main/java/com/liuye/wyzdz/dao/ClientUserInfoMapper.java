package com.liuye.wyzdz.dao;

import com.liuye.wyzdz.entities.ClientUserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author orange
 * @since 2020-09-07
 */
@Repository
@Mapper
public interface ClientUserInfoMapper extends BaseMapper<ClientUserInfo> {

    public ClientUserInfo getDetailInfo(Long id);
}
