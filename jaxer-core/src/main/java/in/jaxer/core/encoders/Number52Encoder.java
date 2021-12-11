
package in.jaxer.core.encoders;

import in.jaxer.core.utilities.JValidator;
import in.jaxer.core.utilities.Strings;

/**
 *
 * @author Shakir Ansari
 */
public class Number52Encoder
{

	private final static String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private final static String lower = upper.toLowerCase();

	private final static String number = "0123456789";

	private static final String alfa52 = upper + number + lower;

	public static String convert(int x)
	{
		if (x == 0)
		{
			return String.valueOf(alfa52.charAt(0));
		}

		String num52 = "";
		int temp;

		while (x > 0)
		{
			temp = x % alfa52.length();
			num52 = alfa52.charAt(temp) + num52;
			x /= alfa52.length();
		}
		return num52;
	}

	public static int convert(String string)
	{
		int val = 0;

		for (int i = 0; i < string.length(); i++)
		{
			int d = alfa52.indexOf(string.charAt(i));
			if (d == -1)
			{
				throw new IllegalArgumentException("Invalid number format");
			}
			val = alfa52.length() * val + d;
		}
		return val;
	}

	public static String encode(String message)
	{
		JValidator.requireNotEmpty(message);

		int ch;
		int length = message.length();

		String encoded = "";

		for (int i = 0; i < length; i++)
		{
			ch = (int) message.charAt(i);
			int first = ch % 10;
			int second = ch / 10;
			encoded += first + convert(second);
		}

		return encoded;
	}

	public static String decode(String message)
	{
		JValidator.requireNotEmpty(message);

		if (!Strings.isAlphaNumeric(message))
		{
			throw new RuntimeException("Invalid number format");
		}

		/**
		 * ASKII
		 * A-Z = 65...90
		 * a-z = 97...122
		 * 0-9 = 48...57
		 */
		String decoded = "";
		int length = message.length();
		for (int i = 0; i < length; i++)
		{
			char ch = message.charAt(i);

			if (ch >= '0' && ch <= '9')
			{
				int nextI = i;
				char nextChar = message.charAt(nextI++);
				while ((nextChar >= 'a' && nextChar <= 'z')
						|| (nextChar >= 'A' && nextChar <= 'Z'))
				{
					nextChar = message.charAt(nextI++);
				}

				String temp = "";
				while (nextI > i && nextI < length)
				{
					temp += message.charAt(nextI);
					nextI--;
				}
				decoded += (char) ((convert(temp) * 10) + (int) (ch - 48));
			}
		}

		return decoded;
	}
}
