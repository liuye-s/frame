package com.liuye.common.util.date;
/**
 * 此枚举中的类型所对应的int与Calendar中相应的常量的值一致
 * @author Landy
 *
 */
public enum Trunc
{
	YEAR(6),DAY(4),MONTH(5),HOUR(3),MINUTE(2),SECOND(1),huu(2);
	private int i;
	private Trunc(int i){
		this.i=i;
	}
	public int getInt(){
		return i;
	}
	public static void main(String args[]) { 
		System.out.println(Trunc.DAY.getInt());
	}
}
