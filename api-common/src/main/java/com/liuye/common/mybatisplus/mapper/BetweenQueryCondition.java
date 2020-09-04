package com.liuye.common.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Iterator;

@Service
@Transactional(readOnly = true)
public class BetweenQueryCondition extends CollectionQueryCondition {

	@Override
	public String getQueryType() {
		return "between";
	}

	@Override
	protected boolean oper(QueryWrapper condition, String fieldName, Collection value) {
		if (condition==null || StringUtils.isBlank(fieldName) || value==null || value.size()<2)
			return false;

		Iterator iter = value.iterator();
		Object val1 = iter.next();
		Object val2 = iter.next();
		condition.between(fieldName, val1, val2);
		return true;
	}

}
