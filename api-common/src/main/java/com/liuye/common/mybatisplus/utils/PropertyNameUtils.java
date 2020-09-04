package com.liuye.common.mybatisplus.utils;

public class PropertyNameUtils {

	/**
	 * Convert a name in camelCase to an underscored name in lower case.
	 * Any upper case letters are converted to lower case with a preceding underscore.
	 * @param name the string containing original name
	 * @return the converted name
	 */
	public static String underscoreName(String name) {
		StringBuilder result = new StringBuilder();
		if (name != null && name.length() > 0) {
			result.append(name.substring(0, 1).toLowerCase());
			for (int i = 1; i < name.length(); i++) {
				String s = name.substring(i, i + 1);
				if (s.matches("[a-zA-Z]") && s.equals(s.toUpperCase()) ) { //数字表单不用_下划线隔开
					result.append("_");
					result.append(s.toLowerCase());
				}
				else {
					result.append(s);
				}
			}
		}
		return result.toString();
	}
	
	/**
	 * 下划线后面的字母转成大写
	 * @param name
	 * @return
	 */
	public static String propertyName(String name) {
		StringBuilder result = new StringBuilder();
		if (name != null && name.length() > 0) {
			name = name.toLowerCase();
			for (int i = 0; i < name.length(); i++) {
				String s = name.substring(i, i + 1);
				if (s.equals("_")) { //数字不用_下划线隔开
					i++;
					result.append(name.substring(i, i + 1).toUpperCase());
				} else {
					result.append(s);
				}
			}
		}
		return result.toString();
	}

}
