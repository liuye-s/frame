package com.liuye.common.util.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateTools
{
	public static final long ZERO=-28800000L;
	public static final String DATE_TIME="yyyy-MM-dd HH:mm:ss";
	public static final String DATE="yyyy-MM-dd";
	public static final String TIME="HH:mm:ss";
	public static String format(Date datetime,String pattern){
		return new SimpleDateFormat(pattern).format(datetime);
	}
	public static String format(Calendar datetime,String pattern){
		return new SimpleDateFormat(pattern).format(datetime);
	}
	/**
	 * 将字符串转换为Date.<br>
	 * 传入格式为"yyyy-MM-dd HH:mm:ss"或"yyyy-MM-dd"或"HH:mm:ss"的字符串
	 * @param datetime 
	 * @return
	 */
	public static Date toDate(String datetime)
	{
		if(datetime==null){
			throw new NullPointerException();
		}
		datetime=datetime.trim();
		return new DateTimeParser(datetime).toDate();
	}
	/**
	 * 获取一个月的最大时间
	 * @param date
	 * @return
	 */
	public static Calendar getMaxDateOfMonth(Calendar date){
		Date statMonth= DateTools.truncate(date.getTime(), Trunc.MONTH);
		Calendar temEndDate = Calendar.getInstance();
		temEndDate.setTime(statMonth);
		int maxDay =date.getActualMaximum(Calendar.DAY_OF_MONTH);
		temEndDate.add(Calendar.DAY_OF_MONTH, maxDay-1);
		return temEndDate;
		
	}
	/**
	 * 获取某一时间在一年的最大日期
	 * @param date
	 * @return
	 */
	public static Calendar getMaxDateOfYear(Calendar date){
		Date statYear= DateTools.truncate(date.getTime(), Trunc.YEAR);
		Calendar temEndDate = Calendar.getInstance();
		temEndDate.setTime(statYear);
		int maxDay =date.getActualMaximum(Calendar.DAY_OF_YEAR);
		System.out.println("maxDay=="+maxDay);
		temEndDate.add(Calendar.DAY_OF_YEAR, maxDay-1);
		return temEndDate;
		
	}
	/**
	 * 判断开始时间和结束时间是否在同一个月
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static boolean isSameMonth(Calendar beginDate, Calendar endDate){
		boolean isSame=true;
		Date startMonth= DateTools.truncate(beginDate.getTime(), Trunc.MONTH);
		Date endMonth= DateTools.truncate(endDate.getTime(), Trunc.MONTH);
		Calendar temStart = Calendar.getInstance();
		temStart.setTime(startMonth);
		Calendar temEnd = Calendar.getInstance();
		temEnd.setTime(endMonth);
		isSame=temStart.equals(temEnd);
		return isSame;
		
	}
	
	/**
	 * 判断开始时间和结束时间是否在同一年
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static boolean isSameYear(Calendar beginDate, Calendar endDate){
		boolean isSame=true;
		Date startYear= DateTools.truncate(beginDate.getTime(), Trunc.YEAR);
		Date endYear= DateTools.truncate(endDate.getTime(), Trunc.YEAR);
		Calendar temStart = Calendar.getInstance();
		temStart.setTime(startYear);
		Calendar temEnd = Calendar.getInstance();
		temEnd.setTime(endYear);
		isSame=temStart.equals(temEnd);
		return isSame;
		
	}
	/**
	 * 开始日期和结束日期所在月之间的间隔。
	 * 例如1月和三月的间隔是2，1月和2月的间隔是1
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int getSubMonth(Calendar beginDate, Calendar endDate){
		int month=0;
		month =endDate.get(Calendar.MONTH)-beginDate.get(Calendar.MONTH);
		return month;		
	}
	/**
	 * 返回开始时间和结束时间所在年份的间隔
	 * 例如2010-03-02 和2010-04-23 返回0
	 * 例如2010-03-02 和2011-04-23 返回1
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int getSubYear(Calendar beginDate, Calendar endDate){
		int year=0;
		year =endDate.get(Calendar.YEAR)-beginDate.get(Calendar.YEAR);
		return year;		
	}
	/**
	 * 将传入的long值转为时间,返回其格式化显示的字符串
	 * @param time
	 * @return
	 */
	public static String toDateTimeString(Long time){
		return new SimpleDateFormat(DATE_TIME).format(new Date(time));
	}
	/**
	 * 将传入的时间,返回其格式化显示的字符串(yyyy-MM-dd HH:mm:ss)
	 * @param time
	 * @return
	 */
	public static String toDateTimeString(Date time){
		return new SimpleDateFormat(DATE_TIME).format(time);
	}
	/**
	 * 将传入的时间,返回其格式化显示的字符串(HH:mm:ss)
	 * @param time
	 * @return
	 */
	public static String toTimeString(Date time){
		return new SimpleDateFormat(TIME).format(time);
	}
	/**
	 * 将传入的时间,返回其格式化显示的字符串(yyyy-MM-dd)
	 * @param time
	 * @return
	 */
	public static String toDateString(Date time){
		return new SimpleDateFormat(DATE).format(time);
	}
	/**
	 * 将字符串转换为Calendar.<br>
	 * 传入格式为"yyyy-MM-dd HH:mm:ss"或"yyyy-MM-dd"或"HH:mm:ss"的字符串
	 * @param datetime
	 * @return
	 */
	public static Calendar toCalendar(String datetime){
		Calendar cal=Calendar.getInstance();
		cal.setTime(toDate(datetime));
		return cal;
	}
	/**
	 * 获取时间点:<br>
	 * 与date同一天的,之前的, 距离date最近的, minute的整数倍的时间点
	 * @param date	时间
	 * @param minute	分钟数
	 * @return
	 */
	public static Date trim(Date date,Integer minute){
		long dayCut=truncate(date, Trunc.DAY).getTime();
		long now=date.getTime();
		long today=now-dayCut;
		long trunc=today - today%(minute*60000);
		return	new Date(dayCut+trunc);
	}
	/**
	 * 获取时间点:<br>
	 * 与date同一天的,之前的, 距离date最近的, minute的整数倍的时间点
	 * @param date	时间
	 * @param minute	分钟数
	 * @return
	 */
	public static Calendar trim(Calendar date,Integer minute){
		long dayCut=truncate(date.getTime(), Trunc.DAY).getTime();
		long now=date.getTime().getTime();
		long today=now-dayCut;
		long trunc=today - today%(minute*60000);
		Calendar tmp=Calendar.getInstance();
		tmp.setTimeInMillis(dayCut+trunc);
		return	tmp;
	}
	/**
	 * 截断时间.<br>
	 * 被截断的位置根据实际情况设为0或1
	 * @param date
	 * @param trunc
	 * @return
	 */
	public static Date truncate(Date date,Trunc trunc){
		Calendar result=Calendar.getInstance();
		result.setTime(date);
		if(trunc.getInt()>0){
			result.set(Calendar.MILLISECOND, 0);
		}
		if(trunc.getInt()>1){
			result.set(Calendar.SECOND, 0);
		}
		if(trunc.getInt()>2){
			result.set(Calendar.MINUTE, 0);
		}
		if(trunc.getInt()>3){
			result.set(Calendar.HOUR_OF_DAY, 0);
		}
		if(trunc.getInt()>4){
			result.set(Calendar.DATE, 1);
		}
		if(trunc.getInt()>5){
			result.set(Calendar.MONTH, 0);
		}
		return result.getTime();
	}
	/**
	 * 截断时间<br>
	 * 被截断的位置根据实际情况设为0或1
	 * @param calendar
	 * @param trunc
	 * @return
	 */
	public static Calendar truncate(Calendar calendar,Trunc trunc){
		Calendar result=Calendar.getInstance();
		result.setTime(truncate(calendar.getTime(), trunc));
		return result;
	}
}
