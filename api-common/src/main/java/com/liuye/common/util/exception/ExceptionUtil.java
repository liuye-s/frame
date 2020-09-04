package com.liuye.common.util.exception;

import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

	/**
	 * 获取原始异常
	 * 
	 * @param e
	 * @return
	 */
	public static String createStackTrackMessage(Exception e) {
		StringBuffer messsage = new StringBuffer();
		if (e != null) {
			messsage.append(e.getClass()).append(": ").append(e.getMessage())
					.append("\n");
			StackTraceElement[] elements = e.getStackTrace();
			for (StackTraceElement stackTraceElement : elements) {
				messsage.append("\t").append(stackTraceElement.toString())
						.append("\n");
			}
		}
		return messsage.toString();
	}
	
	/**
	 * 把异常（error）信息写入到日志中。
	 * @param e Exception对象
	 * @param logger Logger对象
	 * @param msg 信息
	 */
	public static void writerErrorLog(Exception e,Logger logger,String msg){
		StringWriter stringWriter=new StringWriter();
		PrintWriter  printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		String errorStr=stringWriter.getBuffer().toString();
		logger.error(msg+"\n"+errorStr);
	}
}
