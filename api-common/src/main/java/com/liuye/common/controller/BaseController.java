package com.liuye.common.controller;


import com.liuye.common.util.date.DateUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
public class BaseController {

	@InitBinder
	public void initBinder(WebDataBinder binder) {

		//binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		binder.registerCustomEditor(int.class, new com.liuye.common.util.propertyeditors.IntegerEditor());
		binder.registerCustomEditor(long.class, new com.liuye.common.util.propertyeditors.LongEditor());
		binder.registerCustomEditor(double.class, new com.liuye.common.util.propertyeditors.DoubleEditor());
		binder.registerCustomEditor(float.class, new com.liuye.common.util.propertyeditors.FloatEditor());

		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
			//			@Override
			//			public String getAsText() {
			//				Object value = getValue();
			//				return value != null ? DateUtils.formatDateTime((Date)value) : "";
			//			}
		});

	}

	protected static Map<String, Object> renderMap(boolean success, String message, Object obj) {
		Map<String, Object> result = new HashMap<String, Object>(4);
		result.put("success", success);
		if (message!=null) {
			result.put("message", message);
		}
		if (obj!=null) {
			result.put("data", obj);
		}
		return result;
	}
	protected static Map<String, Object> renderSuccess() {
		return renderMap(true, null, null);
	}

	protected static Map<String, Object> renderSuccessMap(Object obj) {
		return renderMap(true, null, obj);
	}

}
