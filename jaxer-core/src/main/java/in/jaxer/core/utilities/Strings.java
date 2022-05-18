
package in.jaxer.core.utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import in.jaxer.core.constants.RegexConstants;
import in.jaxer.core.constants.Singletons;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;

/**
 *
 * @author Shakir Ansari
 */
public class Strings
{

	private static final NavigableMap<Long, String> suffixes = new TreeMap<>();

	static
	{
		suffixes.put(1_000L, "k");
		suffixes.put(1_000_000L, "M");
		suffixes.put(1_000_000_000L, "G");
		suffixes.put(1_000_000_000_000L, "T");
		suffixes.put(1_000_000_000_000_000L, "P");
		suffixes.put(1_000_000_000_000_000_000L, "E");
	}

	public static List<String> getListOfStackTraces(Throwable throwable, String packageFilter)
	{
		if (throwable == null)
		{
			return null;
		}

		List<String> traces = new ArrayList<>();

		if (JValidator.isNotEmpty(throwable.getMessage()))
		{
			traces.add(throwable.getMessage());
		}

		StackTraceElement[] stackTraceElements = throwable.getStackTrace();
		for (StackTraceElement stackTraceElement : stackTraceElements)
		{
			if (packageFilter == null)
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

	public static String getStackTraces(Throwable throwable, String separator, String packageFilter)
	{
		List<String> traces = getListOfStackTraces(throwable, packageFilter);

		if (JValidator.isEmpty(traces))
		{
			return null;
		}

		StringBuilder builder = new StringBuilder("");

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

	public static String getStackTraces(Throwable throwable, String separator)
	{
		return getStackTraces(throwable, separator, null);
	}

	public static String getStackTraces(Throwable throwable)
	{
		return getStackTraces(throwable, System.lineSeparator(), null);
	}

	public static boolean matches(String regex, String string)
	{
		return string.matches(regex);
	}

	public static boolean isHashtag(String string)
	{
		return matches(RegexConstants.HASHTAG, string);
	}

	public static boolean isAadharCardNumber(String string)
	{
		return matches(RegexConstants.AADHAR_CARD_NUMBER, string);
	}

	public static boolean isPanCardNumber(String string)
	{
		return matches(RegexConstants.PANCARD_NUMBER, string);
	}

	public static boolean isAlphaNumeric(String string)
	{
		return matches(RegexConstants.ALPHA_NUMERIC, string);
	}

	public static boolean isAlpha(String string)
	{
		return matches(RegexConstants.ALPHA, string);
	}

	public static boolean isInt(String string)
	{
		return matches(RegexConstants.NUMERIC, string);
	}

	public static boolean isFloat(String floatValue)
	{
		try
		{
			Float.parseFloat(floatValue);
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

	public boolean isEmail(String email)
	{
		int len = email.length();
		if (len < 5)
		{
			return false;
		}
		int last_dot = email.lastIndexOf('.'),
				at = email.indexOf('@'),
				dt = email.indexOf('.');
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
		JValidator.requireNotNull(str);

		int len = str.length();
		if (len < limit)
		{
			limit = len;
		}
		return str.substring(0, limit);
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
		JValidator.requireNotEmpty(str);
		JValidator.requireNotEmpty(sourceString);
		JValidator.requireNotEmpty(targetString);

		while (str.indexOf(sourceString) > 0)
		{
			str = replaceOnce(str, sourceString, targetString);
		}
		return str;
	}

	public static String replaceOnce(String str, String sourceString, String targetString)
	{
		int len = str.length(),
				sub_len = sourceString.length(),
				pos = str.indexOf(sourceString);

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
		JValidator.requireNotNull(str);

		int len = str.length();
		char temp[] = new char[len];
		for (int i = 0; i < len; i++)
		{
			temp[len - 1 - i] = str.charAt(i);
		}
		return new String(temp);
	}

	public static String setName(String name)
	{
		JValidator.requireNotNull(name);

		char temp[] = name.toLowerCase().toCharArray(), ch;
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
		JValidator.requireNotNull(str);

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
		JValidator.requireNotNull(s);

		return String.format("%-" + n + "s", s);
	}

	public static String padLeft(String s, int n)
	{
		JValidator.requireNotNull(s);

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

	public static String getPrettyJson(String uglyJson)
	{
		JValidator.requireNotEmpty(uglyJson, "Json string cannot be empty");

		JsonElement jsonElement = JsonParser.parseString(uglyJson);
		return Singletons.getGson(true).toJson(jsonElement);
	}

	public static String removeStartsWith(String string, String startsWith)
	{
		JValidator.requireNotNull(string, "String cannot be null");
		JValidator.requireNotNull(startsWith, "startsWith cannot be null");

		if (string.startsWith(startsWith))
		{
			return string.substring(startsWith.length());
		}
		return string;
	}

	public static String removeEndsWith(String string, String endsWith)
	{
		JValidator.requireNotNull(string, "String cannot be null");
		JValidator.requireNotNull(endsWith, "endsWith cannot be null");

		if (string.endsWith(endsWith))
		{
			return string.substring(0, string.length() - endsWith.length());
		}
		return string;
	}

	public static String valueOf(Object object)
	{
		return object == null ? null : object.toString();
	}

	public static String valueOf(Date date)
	{
		return date == null ? null : String.valueOf(date.getTime());
	}

	public static String valueOf(Timestamp timestamp)
	{
		return timestamp == null ? null : String.valueOf(timestamp.getTime());
	}

	public static int charCount(String str, char needle)
	{
		int count = 0;
		for (int i = 0; i < str.length(); i++)
		{
			if (str.charAt(i) == needle)
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

		Entry<Long, String> e = suffixes.floorEntry(value);
		Long divideBy = e.getKey();
		String suffix = e.getValue();

		long truncated = value / (divideBy / 10); //the number part of the output times 10
		boolean hasDecimal = truncated < 100 && (truncated / 100d) != (truncated / 100);
		return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
	}
}
