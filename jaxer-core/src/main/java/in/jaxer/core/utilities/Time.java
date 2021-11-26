
package in.jaxer.core.utilities;

import in.jaxer.core.constants.Constants;
import in.jaxer.core.constants.Singletons;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Shakir Ansari
 */
public class Time
{

	/*
	 *** ************************************
	 *** Useful Keys
	 *** ************************************
	 *
	 * G Era designator :AD
	 * y Year :1996; 96
	 * Y Week year :2009; 09
	 * M Month in year :July; Jul; 07
	 * w Week in year :27
	 * W Week in month :2
	 * D Day in year :189
	 * d Day in month :10
	 * F Day of week in month :2
	 * E Day name in week :Tuesday; Tue
	 * u Day number of week :(1 = Monday, …, 7 = Sunday)	1
	 * a Am/pm marker	PM
	 * H Hour in day (0-23)	0
	 * k Hour in day (1-24)	24
	 * K Hour in am/pm (0-11)	0
	 * h Hour in am/pm (1-12)	12
	 * m Minute in hour	30
	 * s Second in minute	55
	 * S Millisecond	978
	 * z Time zone	Pacific Standard Time; PST; GMT-08:00
	 * Z Time zone	-0800
	 * X Time zone	-08; -0800; -08:00
	 *
	 *** *************************************
	 */
	private static Date _addTime(Date date, int field, int amount)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount); //minus number would decrement
		return cal.getTime();
	}

	public static Date addMilliSeconds(Date date, int milliSeconds)
	{
		return _addTime(date, Calendar.MILLISECOND, milliSeconds);
	}

	public static Date addSeconds(Date date, int seconds)
	{
		return _addTime(date, Calendar.SECOND, seconds);
	}

	public static Date addMinutes(Date date, int minutes)
	{
		return _addTime(date, Calendar.MINUTE, minutes);
	}

	public static Date addHours(Date date, int hours)
	{
		return _addTime(date, Calendar.HOUR, hours);
	}

	public static Date addDays(Date date, int days)
	{
		return _addTime(date, Calendar.DATE, days);
	}

	public static Date addMonths(Date date, int months)
	{
		return _addTime(date, Calendar.MONTH, months);
	}

	public static Date addYears(Date date, int years)
	{
		return _addTime(date, Calendar.YEAR, years);
	}

	public static String formatDate(String format, Date date)
	{
		Singletons.getSimpleDateFormat().applyPattern(format);
		return Singletons.getSimpleDateFormat().format(date);
	}

	public static String formatDate(String format, long miliseconds)
	{
		return formatDate(format, new Date(miliseconds));
	}

	public static Date parseDate(String format, String date)
	{
		try
		{
			Singletons.getSimpleDateFormat().applyPattern(format);
			return Singletons.getSimpleDateFormat().parse(date);
		} catch (ParseException ex)
		{
			throw new RuntimeException("Exception occured while parsing date:[" + date + "], in given format:[" + format + "]", ex);
		}
	}

	public static String formatSimpleDate(Date date)
	{
		return formatDate(Constants.SimpleDate, date);
	}

	public static String formatSimpleDate()
	{
		return formatSimpleDate(new Date());
	}

	public static String formatSimpleDate(long miliseconds)
	{
		return formatSimpleDate(new Date(miliseconds));
	}

	public static String formatMySqlDate(long miliseconds)
	{
		return formatMySqlDate(new Date(miliseconds));
	}

	public static String formatMySqlDate(Date date)
	{
		return formatDate(Constants.MySQLDate, date);
	}

	public static String formatMySqlDateTime()
	{
		return formatMySqlDateTime(new Date());
	}

	public static String formatMySqlDateTime(long miliseconds)
	{
		return formatMySqlDateTime(new Date(miliseconds));
	}

	public static String formatMySqlDateTime(Date date)
	{
		return formatDate(Constants.MySQLDateTime, date);
	}

	public static String timeDifference(Date start)
	{
		return timeDifference(start, new Date());
	}

	public static String timeDifference(Date start, Date end)
	{
		System.out.println("Time.timeDifference() - start: " + start);
		System.out.println("Time.timeDifference() - end: " + end);
		return timeDifference(start.getTime(), end.getTime());
	}

	public static String timeDifference(long start)
	{
		return timeDifference(start, System.currentTimeMillis());
	}

	public static String timeDifference(long start, long end)
	{
		long diff = end - start;

		System.out.println("Time.timeDifference() - start: " + start);
		System.out.println("Time.timeDifference() - end: " + end);
		System.out.println("Time.timeDifference() - diff: " + diff);

		long seconds = diff / 1000 % 60;
		long minutes = diff / (60 * 1000) % 60;
		long hours = diff / (60 * 60 * 1000) % 24;
		long days = diff / (24 * 60 * 60 * 1000);

		String response = "";

		if (days > 0)
		{
			response += days + " days, ";
		}

		if (hours > 0)
		{
			response += hours + " hours, ";
		}

		if (minutes > 0)
		{
			response += minutes + " minutes, ";
		}

		if (seconds > 0)
		{
			response += seconds + " seconds";
		}

		return response;
	}
}
