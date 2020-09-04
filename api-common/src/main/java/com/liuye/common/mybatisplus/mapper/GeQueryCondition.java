package com.liuye.common.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GeQueryCondition extends SimpleQueryCondition {

	@Override
	public String getQueryType() {
		return "ge";
	}

	@Override
	protected boolean oper(QueryWrapper condition, String fieldName, Object value) {
		if (condition==null || StringUtils.isBlank(fieldName) || value==null)
			return false;
		
		condition.ge(fieldName, value);
		return true;
	}

}
