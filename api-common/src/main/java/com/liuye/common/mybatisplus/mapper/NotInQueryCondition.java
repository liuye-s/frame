package com.liuye.common.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional(readOnly = true)
public class NotInQueryCondition extends CollectionQueryCondition {

	@Override
	public String getQueryType() {
		return "notIn";
	}

	@Override
	protected boolean oper(QueryWrapper condition, String fieldName, Collection value) {
		if (condition==null || StringUtils.isBlank(fieldName) || value==null)
			return false;
		
		condition.notIn(fieldName, value);
		return true;
	}

}
