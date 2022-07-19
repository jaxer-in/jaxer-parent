package in.jaxer.core;

import in.jaxer.core.utilities.Time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Shakir
 * @date 20-06-2022
 */
public class DateTime
{
	public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

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
