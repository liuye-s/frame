package com.liuye.common.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public interface IQueryCondition {

	public int getIndex();
	public String getQueryType();
	public boolean addCondition(QueryWrapper condition, String queryName, Object queryValue, Class<?> clazz, String defaultPrefix);

}
