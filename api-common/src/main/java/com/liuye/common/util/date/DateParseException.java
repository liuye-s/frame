package com.liuye.common.util.date;

public class DateParseException extends RuntimeException
{
	private static final long serialVersionUID = 6816050813003273429L;
	public DateParseException(){
		super();
	}
	public DateParseException(Throwable t){
		super(t);
	}
	public DateParseException(String str){
		super("["+str+"] can't parse.");
	}
}
