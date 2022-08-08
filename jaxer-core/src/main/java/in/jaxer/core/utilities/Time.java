package in.jaxer.core.utilities;

import in.jaxer.core.DateTimeUtils;
import in.jaxer.core.constants.Constants;
import in.jaxer.core.dtos.TimeDifference;
import lombok.extern.log4j.Log4j2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ** ************************************
 * ** Useful Keys
 * ** ************************************
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
 * ** *************************************
 *
 * @author Shakir Ansari
 * @since v0.0.1
 * @deprecated on v1.0.9-beta, please check {@link DateTimeUtils}
 */
@Log4j2
@Deprecated
public class Time
{
	/**
	 * @since v0.0.1
	 */
	@Deprecated
	private static Date _addTime(Date date, int field, int amount)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount); //minus number would decrement
		return cal.getTime();
	}

	/**
	 * @since v0.0.1
	 * @deprecated on v1.0.9-beta, please check {@link DateTimeUtils#addMilliSeconds(Date, int)}
	 */
	@Deprecated
	public static Date addMilliSeconds(Date date, int milliSeconds)
	{
		return _addTime(date, Calendar.MILLISECOND, milliSeconds);
	}

	/**
	 * @since v0.0.1
	 * @deprecated on v1.0.9-beta, please check {@link DateTimeUtils#addSeconds(Date, int)}
	 */
	@Deprecated
	public static Date addSeconds(Date date, int seconds)
	{
		return _addTime(date, Calendar.SECOND, seconds);
	}

	/**
	 * @since v0.0.1
	 * @deprecated on v1.0.9-beta, please check {@link DateTimeUtils#addMinutes(Date, int)}
	 */
	@Deprecated
	public static Date addMinutes(Date date, int minutes)
	{
		return _addTime(date, Calendar.MINUTE, minutes);
	}

	/**
	 * @since v0.0.1
	 * @deprecated on v1.0.9-beta, please check {@link DateTimeUtils#addHours(Date, int)}
	 */
	@Deprecated
	public static Date addHours(Date date, int hours)
	{
		return _addTime(date, Calendar.HOUR, hours);
	}

	/**
	 * @since v0.0.1
	 * @deprecated on v1.0.9-beta, please check {@link DateTimeUtils#addDays(Date, int)}
	 */
	@Deprecated
	public static Date addDays(Date date, int days)
	{
		return _addTime(date, Calendar.DATE, days);
	}

	/**
	 * @since v0.0.1
	 * @deprecated on v1.0.9-beta, please check {@link DateTimeUtils#addMonths(Date, int)}
	 */
	@Deprecated
	public static Date addMonths(Date date, int months)
	{
		return _addTime(date, Calendar.MONTH, months);
	}

	/**
	 * @since v0.0.1
	 * @deprecated on v1.0.9-beta, please check {@link DateTimeUtils#addYears(Date, int)}
	 */
	@Deprecated
	public static Date addYears(Date date, int years)
	{
		return _addTime(date, Calendar.YEAR, years);
	}

	public static String formatDate(String format, Date date)
	{
		return new SimpleDateFormat(format).format(date);
	}

	public static String formatDate(String format, long miliseconds)
	{
		return formatDate(format, new Date(miliseconds));
	}

	public static Date parseDate(String format, String date)
	{
		try
		{
			new SimpleDateFormat().applyPattern(format);
			return new SimpleDateFormat().parse(date);
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

	public static TimeDifference getTimeDifference(Date start)
	{
		log.debug("start: {}", start);

		return getTimeDifference(start, new Date());
	}

	public static TimeDifference getTimeDifference(Date start, Date end)
	{
		log.debug("start: {}, end: {}", start, end);

		return getTimeDifference(start.getTime(), end.getTime());
	}

	public static TimeDifference getTimeDifference(long start)
	{
		log.debug("start: {}", start);
		return getTimeDifference(start, System.currentTimeMillis());
	}

	public static TimeDifference getTimeDifference(long start, long end)
	{
		log.debug("start: {}, end: {}", start, end);

		long diff = end - start;
		log.debug("diff: {}", diff);

		boolean inverse = false;
		if (diff < 0)
		{
			inverse = true;
			diff = -diff;
		}

		log.debug("inverse: {}", inverse);

		TimeDifference timeDifference = new TimeDifference();

		if (diff == 0)
		{
			return timeDifference;
		}

		timeDifference.milliSeconds = diff % 1000;
		timeDifference.seconds = diff / 1000 % 60;
		timeDifference.minutes = diff / (60 * 1000) % 60;
		timeDifference.hours = diff / (60 * 60 * 1000) % 24;
		timeDifference.days = diff / (24 * 60 * 60 * 1000);

		if (inverse)
		{
			timeDifference.milliSeconds = -timeDifference.milliSeconds;
			timeDifference.seconds = -timeDifference.seconds;
			timeDifference.minutes = -timeDifference.minutes;
			timeDifference.hours = -timeDifference.hours;
			timeDifference.days = -timeDifference.days;
		}

		return timeDifference;
	}
}
