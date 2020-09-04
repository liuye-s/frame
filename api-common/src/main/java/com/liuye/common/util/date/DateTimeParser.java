package com.liuye.common.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


class DateTimeParser
{
	private String datetime;

	private Date result;

	public DateTimeParser(String datetime)
	{
		this.datetime = datetime;
		if (datetime == null)
		{
			throw new NullPointerException();
		}
		parser();
	}

	private void parser()
	{
		String[] cuts = datetime.split(" ");
		if (cuts.length == 1)
		{
			onePart(cuts[0]);
		}
		else if (cuts.length == 2)
		{
			twoPart(datetime);
		}
		else
		{
			throw new DateParseException(datetime);
		}
	}

	private void twoPart(String datetime2)
	{
		SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			result = yyyyMMddHHmmss.parse(datetime2);
		}
		catch (ParseException e)
		{
			throw new DateParseException(datetime);
		}
	}

	private void onePart(String string)
	{
		SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat HHmmss = new SimpleDateFormat("HH:mm:ss");
		if (string.split("-").length > 1)
		{
			try
			{
				result = yyyyMMdd.parse(string);
			}
			catch (ParseException e)
			{
				throw new DateParseException(datetime);
			}
		}
		else if (string.split(":").length > 1)
		{
			try
			{
				result = HHmmss.parse(string);
			}
			catch (ParseException e1)
			{
				throw new DateParseException(datetime);
			}
		}
		else
		{
			throw new DateParseException(datetime);
		}
	}

	public Date toDate()
	{
		return result;
	}

}
