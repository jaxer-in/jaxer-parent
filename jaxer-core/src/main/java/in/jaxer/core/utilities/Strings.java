package in.jaxer.core.utilities;

import in.jaxer.core.constants.RegexConstants;
import lombok.extern.log4j.Log4j2;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class Strings
{
	public static boolean isNullOrEmpty(String str)
	{
		return str == null || str.isEmpty();
	}

	public static boolean isNotNullAndNotEmpty(String str)
	{
		return str != null && !str.isEmpty();
	}

	public static List<String> getListOfStackTraces(Throwable throwable, String packageFilter)
	{
		if (throwable == null)
		{
			return null;
		}

		List<String> traces = new ArrayList<>();

		if (Strings.isNotNullAndNotEmpty(throwable.getMessage()))
		{
			traces.add(throwable.getMessage());
		}

		StackTraceElement[] stackTraceElements = throwable.getStackTrace();
		for (StackTraceElement stackTraceElement : stackTraceElements)
		{
			if (JValidator.isNullOrEmpty(packageFilter))
			{
				traces.add(stackTraceElement.toString());
			} else
			{
				if (stackTraceElement.toString().startsWith(packageFilter))
				{
					traces.add(stackTraceElement.toString());
				}
			}
		}

		return traces;
	}

	public static HashMap<String, List<String>> getStackTraces(Exception exception, String packageFilter)
	{
		if (exception == null)
		{
			return null;
		}

		HashMap<String, List<String>> stacktraceHashMap = new HashMap<>();

		stacktraceHashMap.put("exception", getListOfStackTraces(exception, packageFilter));
		stacktraceHashMap.put("causedby", getListOfStackTraces(exception.getCause(), packageFilter));

		return stacktraceHashMap;
	}

	@Deprecated
	public static String getStackTraces(Throwable throwable, String separator, String packageFilter)
	{
		List<String> traces = getListOfStackTraces(throwable, packageFilter);

		if (JValidator.isNullOrEmpty(traces))
		{
			return null;
		}

		StringBuilder builder = new StringBuilder();

		for (String trace : traces)
		{
			if (packageFilter == null)
			{
				builder.append(trace).append(separator);
			} else
			{
				if (trace.startsWith(packageFilter))
				{
					builder.append(trace).append(separator);
				}
			}
		}

		return builder.toString();
	}

	@Deprecated
	public static String getStackTraces(Throwable throwable, String separator)
	{
		return getStackTraces(throwable, separator, null);
	}

	@Deprecated
	public static String getStackTraces(Throwable throwable)
	{
		return getStackTraces(throwable, System.lineSeparator(), null);
	}

	public static boolean isHashtag(String str)
	{
		log.debug("str: {}", str);

		JValidator.throwWhenNullOrEmpty(str);

		return str.matches(RegexConstants.HASHTAG);
	}

	public static boolean isAadharCardNumber(String str)
	{
		log.debug("str: {}", str);

		JValidator.throwWhenNullOrEmpty(str);

		return str.matches(RegexConstants.AADHAR_CARD_NUMBER);
	}

	public static boolean isPanCardNumber(String str)
	{
		log.debug("str: {}", str);

		JValidator.throwWhenNullOrEmpty(str);

		return str.matches(RegexConstants.PANCARD_NUMBER);
	}

	public static boolean isAlphaNumeric(String str)
	{
		log.debug("str: {}", str);

		JValidator.throwWhenNullOrEmpty(str);

		return str.matches(RegexConstants.ALPHA_NUMERIC);
	}

	@Deprecated
	public static boolean isAlpha(String str)
	{
		log.debug("str: {}", str);

		JValidator.throwWhenNullOrEmpty(str);

		return str.matches(RegexConstants.ALPHA);
	}

	@Deprecated
	public static boolean isCharactersOlny(String str)
	{
		log.debug("str: {}", str);

		JValidator.throwWhenNullOrEmpty(str);

		return str.matches(RegexConstants.CHARACTERS_ONLY);
	}

	@Deprecated
	public static boolean isInt(String str)
	{
		log.debug("str: {}", str);

		JValidator.throwWhenNullOrEmpty(str);

		return str.matches(RegexConstants.NUMERIC);
	}

	@Deprecated
	public static boolean isNumberOnly(String str)
	{
		log.debug("str: {}", str);

		JValidator.throwWhenNullOrEmpty(str);

		return str.matches(RegexConstants.NUMBER_ONLY);
	}

	public static boolean isFloat(String floatString)
	{
		log.debug("floatString: {}", floatString);
		try
		{
			Float.parseFloat(floatString);
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

	public boolean isEmail(String email)
	{
		log.debug("email: {}", email);
		JValidator.throwWhenNullOrEmpty(email);

		int len = email.length();
		if (len < 5)
		{
			return false;
		}
		int last_dot = email.lastIndexOf('.'), at = email.indexOf('@'), dt = email.indexOf('.');
		boolean flag = false;

		if (last_dot > 0 && at > 0)
		{
			String after_at = email.substring(at);
			char ch;
			int temp_len = after_at.length();
			for (int i = 0; i < temp_len; i++)
			{
				ch = after_at.charAt(i);
				if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
				{
					flag = true;
				}
			}

			if (!flag)
			{
				return false;
			}

			if (at != email.lastIndexOf('@'))
			{
				return false;
			}

			//if '@' and '.' are just after one position ie: shakir@.com
			if (at + 1 == dt)
			{
				return false;
			}

			//for first character
			if (dt == 0 || at == 0 || last_dot == 0)
			{
				return false;
			}

			//if '@' if after last'.' ie: shakir.gmail@com
			if (at > last_dot)
			{
				return false;
			}

			//for last char
			if (last_dot + 1 >= len)
			{
				return false;
			}

			//atleast one char required b/w '@' and '.' ie: a@b.c (here b)
			if (last_dot - at < 1)
			{
				return false;
			}
		}
		return flag;
	}

	public String getLimitedString(String str, int limit)
	{
		log.debug("str: {}, limit: {}", str, limit);
		JValidator.throwWhenNullOrEmpty(str);

		int len = str.length();
		log.debug("len: {}", len);

		return str.substring(0, len < limit ? len : limit);
	}

	public static String htmlString(String str)
	{
		str = str.trim();
		int len = str.length();
		String temp = "";
		char ch = 'x';

		for (int i = 0; i < len; i++)
		{
			ch = str.charAt(i);
			if (ch == ' ' && str.charAt(i - 1) == ' ')
			{
				temp += "&nbsp;&nbsp;";
			} else if (ch == '\t')
			{
				temp += "&nbsp;&nbsp;&nbsp;";
			} else if (ch == '\n')
			{
				temp += "<br/>";
			} else
			{
				temp += "" + ch;
			}
		}
		return temp;
	}

	public static String replaceAll(String str, String sourceString, String targetString)
	{
		log.debug("str: {}, sourceString: {}, targetString: {}", str, sourceString, targetString);

		JValidator.throwWhenNullOrEmpty(str);
		JValidator.throwWhenNullOrEmpty(sourceString);
		JValidator.throwWhenNullOrEmpty(targetString);

		while (str.indexOf(sourceString) > 0)
		{
			str = replaceOnce(str, sourceString, targetString);
		}

		return str;
	}

	public static String replaceOnce(String str, String sourceString, String targetString)
	{
		log.debug("str: {}, sourceString: {}, targetString: {}", str, sourceString, targetString);

		JValidator.throwWhenNullOrEmpty(str);
		JValidator.throwWhenNullOrEmpty(sourceString);
		JValidator.throwWhenNullOrEmpty(targetString);

		int len = str.length(), sub_len = sourceString.length(), pos = str.indexOf(sourceString);

		if (pos > 0 && len > 2)
		{
			return str.substring(0, pos) + targetString + str.substring(pos + sub_len);
		} else
		{
			return str;
		}
	}

	public static String reverse(String str)
	{
		log.debug("str: {}", str);
		JValidator.throwWhenNullOrEmpty(str);

		int len = str.length();
		char[] temp = new char[len];
		for (int i = 0; i < len; i++)
		{
			temp[len - 1 - i] = str.charAt(i);
		}
		return new String(temp);
	}

	public static String setName(String name)
	{
		log.debug("name: {}", name);
		JValidator.throwWhenNullOrEmpty(name);

		char[] temp = name.toLowerCase().toCharArray();
		char ch;
		ch = name.charAt(0);
		if (ch >= 'a' && ch <= 'z')
		{
			ch -= 32;
		}

		temp[0] = ch;
		return new String(temp);
	}

	public static String shuffle(String str)
	{
		log.debug("str: {}", str);
		JValidator.throwWhenNullOrEmpty(str);

		int len = str.length();
		if (len == 1)
		{
			return str;
		}

		int mid = len / 2;
		String temp1 = shuffle(str.substring(0, mid));
		String temp2 = shuffle(str.substring(mid));

//		if (Randoms.getRandomIntLowerThan(100) > 50)
		if (new Random().nextInt() % 2 == 0)
		{
			return temp1 + temp2;
		} else
		{
			return temp2 + temp1;
		}
	}

	public static String padRight(String s, int n)
	{
		JValidator.throwWhenNullOrEmpty(s);

		return String.format("%-" + n + "s", s);
	}

	public static String padLeft(String s, int n)
	{
		JValidator.throwWhenNullOrEmpty(s);

		return String.format("%" + n + "s", s);
	}

	public static String leadingChars(int number, char chars, int leadingZeros)
	{
		//String formatted = String.format("%03d", num);
		return String.format("%" + chars + leadingZeros + "d", number);
	}

	public static String padLeft(String inputString, int length, String leadingChar)
	{
		if (inputString.length() >= length)
		{
			return inputString;
		}

		StringBuilder sb = new StringBuilder();
		while (sb.length() < length - inputString.length())
		{
			sb.append(leadingChar);
		}
		sb.append(inputString);

		return sb.toString();
	}

	public static String formatFloat(float number, int digitSize)
	{
		//String.format("%.2f", i2)
		return new DecimalFormat("##.##").format(number);
//		return String.format("%." + digitSize + "f", number);
	}

	public static String getUUID()
	{
		return UUID.randomUUID().toString();
	}

	public static String getUUID(String str)
	{
		return UUID.nameUUIDFromBytes(str.getBytes()).toString();
	}

	public static String removeStartsWith(String str, String startsWith)
	{
		log.debug("str: {}, startsWith: {}", str, startsWith);
		JValidator.throwWhenNullOrEmpty(str, "String cannot be null");
		JValidator.throwWhenNullOrEmpty(startsWith, "startsWith cannot be null");

		if (str.startsWith(startsWith))
		{
			return str.substring(startsWith.length());
		}
		return str;
	}

	public static String removeEndsWith(String str, String endsWith)
	{
		log.debug("str: {}, endsWith: {}", str, endsWith);
		JValidator.throwWhenNullOrEmpty(str, "String cannot be null");
		JValidator.throwWhenNullOrEmpty(endsWith, "endsWith cannot be null");

		if (str.endsWith(endsWith))
		{
			return str.substring(0, str.length() - endsWith.length());
		}
		return str;
	}

	public static String valueOf(Object object)
	{
		return object == null ? null : object.toString();
	}

	public static String valueOf(Object object, String defaultValue)
	{
		return object == null ? defaultValue : object.toString();
	}

	/**
	 * This method will return milliseconds from a date in string datatype
	 *
	 * @param date
	 *
	 * @return
	 */
	public static String valueOf(Date date)
	{
		return date == null ? null : String.valueOf(date.getTime());
	}

	public static String valueOf(Timestamp timestamp)
	{
		return timestamp == null ? null : String.valueOf(timestamp.getTime());
	}

	public static int charCount(String str, char ch)
	{
		log.debug("str: {}, ch: {}", str, ch);
		int count = 0;

		for (char chatAt : str.toCharArray())
		{
			if (chatAt == ch)
			{
				count++;
			}
		}

		return count;
	}

	public static String readableNumber(long value)
	{
		//Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
		if (value == Long.MIN_VALUE)
		{
			return readableNumber(Long.MIN_VALUE + 1);
		}

		if (value < 0)
		{
			return "-" + readableNumber(-value);
		}

		if (value < 1000)
		{
			return Long.toString(value); //deal with easy case
		}

		final NavigableMap<Long, String> suffixes = new TreeMap<Long, String>()
		{
			{
				put(1_000L, "k");
				put(1_000_000L, "M");
				put(1_000_000_000L, "G");
				put(1_000_000_000_000L, "T");
				put(1_000_000_000_000_000L, "P");
				put(1_000_000_000_000_000_000L, "E");
			}
		};


		Entry<Long, String> e = suffixes.floorEntry(value);
		Long divideBy = e.getKey();
		String suffix = e.getValue();

		long truncated = value / (divideBy / 10); //the number part of the output times 10
		boolean hasDecimal = truncated < 100 && (truncated / 100d) != (truncated / 100);
		return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
	}
}
