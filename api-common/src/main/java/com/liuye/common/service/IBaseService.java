package com.liuye.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

public interface IBaseService<T> {

    public IPage<T> pageByFilter(IPage<T> page, Map<String, Object> data, String defaultTablePrefix);
    public List<T> listByFilter(Map<String, Object> data, String defaultTablePrefix);
    public List<T> listByFilter(Map<String, Object> data);

}
