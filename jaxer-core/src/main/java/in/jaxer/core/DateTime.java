package in.jaxer.core;

import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Shakir
 * @date 20-06-2022
 */
public class DateTime
{
	public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Getter
	private final Date date;

	public DateTime()
	{
		this.date = new Date();
	}

	public DateTime(Date date)
	{
		this.date = date;
	}

	public DateTime(long millseconds)
	{
		this.date = new Date(millseconds);
	}

	public DateTime addDateTime(int field, int amount)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.date);
		cal.add(field, amount); //minus number would decrement
		return new DateTime(cal.getTime());
	}

	public DateTime addMilliSeconds(Date date, int milliSeconds)
	{
		return addDateTime(Calendar.MILLISECOND, milliSeconds);
	}

	public DateTime addSeconds(Date date, int seconds)
	{
		return addDateTime(Calendar.SECOND, seconds);
	}

	public DateTime addMinutes(Date date, int minutes)
	{
		return addDateTime(Calendar.MINUTE, minutes);
	}

	public DateTime addHours(Date date, int hours)
	{
		return addDateTime(Calendar.HOUR, hours);
	}

	public DateTime addDays(Date date, int days)
	{
		return addDateTime(Calendar.DATE, days);
	}

	public DateTime addMonths(Date date, int months)
	{
		return addDateTime(Calendar.MONTH, months);
	}

	public DateTime addYears(Date date, int years)
	{
		return addDateTime(Calendar.YEAR, years);
	}

	@Override
	public String toString()
	{
		return new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT).format(date);
	}

	public String toString(String pattern)
	{
		return new SimpleDateFormat(pattern).format(date);
	}
}
