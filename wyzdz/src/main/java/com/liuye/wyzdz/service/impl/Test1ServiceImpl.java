package com.liuye.wyzdz.service.impl;

import com.liuye.wyzdz.entities.Test1;
import com.liuye.wyzdz.dao.Test1Mapper;
import com.liuye.wyzdz.service.ITest1Service;
import com.liuye.common.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author orange
 * @since 2020-09-04
 */
@Service
@Transactional(readOnly = true)
public class Test1ServiceImpl extends BaseService<Test1Mapper, Test1> implements ITest1Service {

}
