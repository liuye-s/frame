package com.liuye.common.mybatisplus.mapper;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuye.common.mybatisplus.utils.PropertyNameUtils;
import com.liuye.common.util.date.DateUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

public abstract class SimpleQueryCondition implements IQueryCondition {

	protected abstract boolean oper(QueryWrapper condition, String fieldName, Object value);

	@Override
	public int getIndex() {
		return 0;
	}

	@Override
	public boolean addCondition(QueryWrapper condition, String queryName, Object queryValue, Class<?> clazz, String defaultPrefix) {
		if (condition==null || StringUtils.isBlank(queryName) || queryValue==null || clazz==null)
			return false;

		String tablePrefix = null;
		int pos = queryName.indexOf(".");
		if (pos>=0) {
			tablePrefix = queryName.substring(0, pos);
			queryName = queryName.substring(pos + 1);
		}

		try {
			//String value;
			Object value;
			Class<?> valueClazz = queryValue.getClass();
			
			if (valueClazz.isArray())
				value = (String)Array.get(queryValue, 0);
			else
				//value = String.valueOf(queryValue);//(String)queryValue;
				value = queryValue;

			//if (StringUtils.isBlank(value))
			if (value==null || value instanceof String && StringUtils.isBlank((String)value))
				return false;

			if (StringUtils.isNotBlank(tablePrefix)) {
				String fieldName = PropertyNameUtils.underscoreName(queryName);

				return oper(condition, tablePrefix + "." + fieldName, value);
			}

			Field field = clazz.getDeclaredField(queryName);
			if (field==null)
				return false;
			
			String fieldName;
			TableField ann = field.getAnnotation(TableField.class);
			if (ann==null) {
				fieldName = PropertyNameUtils.underscoreName(queryName);
			} else {
				fieldName = ann.value();
				if (StringUtils.isBlank(fieldName)) {
					// fieldName = queryName;
					fieldName = PropertyNameUtils.underscoreName(queryName);
				}
			}
			if (StringUtils.isBlank(fieldName))
				return false;

			if (StringUtils.isNotBlank(tablePrefix)) {
				fieldName = tablePrefix + "." + fieldName;
			} else if (StringUtils.isNotBlank(defaultPrefix)) {
				fieldName = defaultPrefix + "." + fieldName;
			}
			
			Class fieldClazz = field.getType();
			if (fieldClazz==null)
				return false;
			if (fieldClazz==Integer.class) {
				//return oper(condition, fieldName, Integer.valueOf(value));
				if (value instanceof String)
					return oper(condition, fieldName, Integer.valueOf((String)value));
				else
					return oper(condition, fieldName, value);
			} else if (fieldClazz==Long.class) {
				//return oper(condition, fieldName, Long.valueOf(value));
				if (value instanceof String)
					return oper(condition, fieldName, Long.valueOf((String)value));
				else
					return oper(condition, fieldName, value);
			} else if (fieldClazz==Float.class) {
				//return oper(condition, fieldName, Float.valueOf(value));
				if (value instanceof String)
					return oper(condition, fieldName, Float.valueOf((String)value));
				else
					return oper(condition, fieldName, value);
			} else if (fieldClazz==Double.class) {
				//return oper(condition, fieldName, Double.valueOf(value));
				if (value instanceof String)
					return oper(condition, fieldName, Double.valueOf((String)value));
				else
					return oper(condition, fieldName, value);
			} else if (fieldClazz==BigDecimal.class) {
				//return oper(condition, fieldName, new BigDecimal(value));
				if (value instanceof String)
					return oper(condition, fieldName, new BigDecimal((String)value));
				else
					return oper(condition, fieldName, value);
			} else if (fieldClazz==Date.class) {
				//return oper(condition, fieldName, DateUtils.parseDate(value));
				if (value instanceof String)
					return oper(condition, fieldName, DateUtils.parseDate((String)value));
				else
					return oper(condition, fieldName, value);
			} else {
				return oper(condition, fieldName, value);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
