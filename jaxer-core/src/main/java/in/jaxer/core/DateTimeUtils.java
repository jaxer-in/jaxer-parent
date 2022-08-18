package in.jaxer.core;

import in.jaxer.core.dtos.TimeDifference;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

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
 * @author Shakir
 * @since 1.0.9-beta [2022-06-20]
 */
@Log4j2
public class DateTimeUtils
{
	public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Getter
	private final Date date;

	public DateTimeUtils()
	{
		this.date = new Date();
	}

	public DateTimeUtils(Date date)
	{
		this.date = date;
	}

	public DateTimeUtils(long millseconds)
	{
		this.date = new Date(millseconds);
	}

	/**
	 * @since 1.0.9-beta
	 */
	public DateTimeUtils addDateTime(int field, int amount)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.date);
		cal.add(field, amount); //minus number would decrement
		return new DateTimeUtils(cal.getTime());
	}

	/**
	 * @since 1.0.9-beta
	 */
	public DateTimeUtils addMilliSeconds(Date date, int milliSeconds)
	{
		return addDateTime(Calendar.MILLISECOND, milliSeconds);
	}

	/**
	 * @since 1.0.9-beta
	 */
	public DateTimeUtils addSeconds(Date date, int seconds)
	{
		return addDateTime(Calendar.SECOND, seconds);
	}

	/**
	 * @since 1.0.9-beta
	 */
	public DateTimeUtils addMinutes(Date date, int minutes)
	{
		return addDateTime(Calendar.MINUTE, minutes);
	}

	/**
	 * @since 1.0.9-beta
	 */
	public DateTimeUtils addHours(Date date, int hours)
	{
		return addDateTime(Calendar.HOUR, hours);
	}

	/**
	 * @since 1.0.9-beta
	 */
	public DateTimeUtils addDays(Date date, int days)
	{
		return addDateTime(Calendar.DATE, days);
	}

	/**
	 * @since 1.0.9-beta
	 */
	public DateTimeUtils addMonths(Date date, int months)
	{
		return addDateTime(Calendar.MONTH, months);
	}

	/**
	 * @since 1.0.9-beta
	 */
	public DateTimeUtils addYears(Date date, int years)
	{
		return addDateTime(Calendar.YEAR, years);
	}

	/**
	 * @since 1.1.0-beta
	 */
	public static TimeDifference getDifference(Date start)
	{
		log.debug("start: {}", start);

		return getDifference(start, new Date());
	}

	/**
	 * @since 1.1.0-beta
	 */
	public static TimeDifference getDifference(Date start, Date end)
	{
		log.debug("start: {}, end: {}", start, end);

		return getDifference(start.getTime(), end.getTime());
	}

	/**
	 * @since 1.1.0-beta
	 */
	public static TimeDifference getDifference(long start)
	{
		log.debug("start: {}", start);
		return getDifference(start, System.currentTimeMillis());
	}

	/**
	 * @since 1.1.0-beta
	 */
	public static TimeDifference getDifference(long start, long end)
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

	/**
	 * @since 1.0.9-beta
	 */
	@Override
	public String toString()
	{
		return new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT).format(date);
	}

	/**
	 * @since 1.0.9-beta
	 */
	public String toString(String pattern)
	{
		return new SimpleDateFormat(pattern).format(date);
	}
}
