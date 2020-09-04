package com.liuye.common.mybatisplus.mapper;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liuye.common.mybatisplus.utils.PropertyNameUtils;
import com.liuye.common.util.date.DateUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

public abstract class CollectionQueryCondition implements IQueryCondition {

	protected abstract boolean oper(QueryWrapper condition, String fieldName, Collection value);

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
			//String _value = (String)queryValue;
			Object _value = queryValue;

			//if (StringUtils.isBlank(_value))
			if (_value==null || _value instanceof String && StringUtils.isBlank((String)_value))
				return false;
			
			//Collection<String> values = Arrays.asList(_value.split(","));
			Collection values;
			if (_value instanceof String) {
				values = Arrays.asList(((String)_value).split(","));
			} else if (_value instanceof Collection) {
				values = (Collection)_value;
			} else {
				return false;
			}
			
			if (values==null || values.size()<=0)
				return false;

			if (StringUtils.isNotBlank(tablePrefix)) {
				String fieldName = PropertyNameUtils.underscoreName(queryName);

				return oper(condition, tablePrefix + "." + fieldName, values);
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
			
			Collection value;
			Class fieldClazz = field.getType();
			if (fieldClazz==null)
				return false;
			if (fieldClazz==Integer.class) {
				value = new ArrayList();
				//for (String val : values) {
				for (Object val : values) {
					//value.add(Integer.valueOf(val));
					if (val instanceof String)
						value.add(Integer.valueOf((String) val));
					else
						value.add(val);
				}
			} else if (fieldClazz==Long.class) {
				value = new ArrayList();
				//for (String val : values) {
				for (Object val : values) {
					//value.add(Long.valueOf(val));
					if (val instanceof String)
						value.add(Long.valueOf((String) val));
					else
						value.add(val);
				}
			} else if (fieldClazz==Float.class) {
				value = new ArrayList();
				//for (String val : values) {
				for (Object val : values) {
					//value.add(Float.valueOf(val));
					if (val instanceof String)
						value.add(Float.valueOf((String) val));
					else
						value.add(val);
				}
			} else if (fieldClazz==Double.class) {
				value = new ArrayList();
				//for (String val : values) {
				for (Object val : values) {
					//value.add(Double.valueOf(val));
					if (val instanceof String)
						value.add(Double.valueOf((String) val));
					else
						value.add(val);
				}
			} else if (fieldClazz==BigDecimal.class) {
				value = new ArrayList();
				//for (String val : values) {
				for (Object val : values) {
					//value.add(new BigDecimal(val));
					if (val instanceof String)
						value.add(new BigDecimal((String) val));
					else
						value.add(val);
				}
			} else if (fieldClazz==Date.class) {
				value = new ArrayList();
				//for (String val : values) {
				for (Object val : values) {
					if (val instanceof String)
						value.add(DateUtils.parseDate(val));
					else
						value.add(val);
				}
			} else {
				value = values;
			}

			if (StringUtils.isNotBlank(tablePrefix)) {
				fieldName = tablePrefix + "." + fieldName;
			} else if (StringUtils.isNotBlank(defaultPrefix)) {
				fieldName = defaultPrefix + "." + fieldName;
			}

			return oper(condition, fieldName, value);
			
		} catch (Exception e) {
			
		}

		return false;
	}

}
