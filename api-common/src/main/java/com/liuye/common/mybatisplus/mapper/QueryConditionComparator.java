package com.liuye.common.mybatisplus.mapper;

import java.util.Comparator;

public class QueryConditionComparator implements Comparator<IQueryCondition> {

	@Override
	public int compare(IQueryCondition o1, IQueryCondition o2) {
		return o1.getIndex()-o2.getIndex();
	}

}
