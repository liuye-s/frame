package com.liuye.common.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GtQueryCondition extends SimpleQueryCondition {

	@Override
	public String getQueryType() {
		return "gt";
	}

	@Override
	protected boolean oper(QueryWrapper condition, String fieldName, Object value) {
		if (condition==null || StringUtils.isBlank(fieldName) || value==null)
			return false;
		
		condition.gt(fieldName, value);
		return true;
	}

}
