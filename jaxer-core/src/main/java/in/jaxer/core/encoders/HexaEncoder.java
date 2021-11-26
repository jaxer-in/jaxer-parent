
package in.jaxer.core.encoders;

import in.jaxer.core.utilities.Validator;
import java.util.Arrays;

/**
 *
 * @author Shakir Ansari
 */
public class HexaEncoder
{

	final private String digits = "0123456789ABCDEF";

	private int myHex2decimal(String s)
	{
		s = s.toUpperCase();

		int val = 0;
		for (int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			int d = digits.indexOf(c);
			val = 16 * val + d;
		}
		return val;
	}

	private String myDecimal2hex(int d)
	{
		if (d == 0)
		{
			return "0";
		}
		String hex = "";
		while (d > 0)
		{
			int digit = d % 16;                // rightmost digit
			hex = digits.charAt(digit) + hex;  // string concatenation
			d = d / 16;
		}
		return hex;
	}

	public static String convert(int x)
	{
		return Integer.toHexString(x);
	}

	public static int convert(String string)
	{
		return Integer.parseInt(string, 16);
	}

	public static String encode(String message)
	{
		Validator.requireNotEmpty(message);

		String encoded = "";

		for (int i = 0; i < message.length(); i++)
		{
			encoded += (i % 2 == 0) ? "g" : "h";
			encoded += convert((int) message.charAt(i));
		}

		return encoded;
	}

	public static String decode(String message)
	{
		Validator.requireNotEmpty(message);

		String messageLowerCase = message.toLowerCase();

		String pattern = "^[0-9a-h]*$";
		if (!messageLowerCase.matches(pattern))
		{
			throw new IllegalArgumentException("Invalid number format");
		}

		//spliting with two delimiters [g or h]
		String[] charInt = messageLowerCase.split("[gh]");
		System.out.println("HexaEncryptor.decode() - " + Arrays.toString(charInt));

		String decoded = "";

		//i starting from 1, bc charInt[]'s first value will be empty
		for (int i = 1; i < charInt.length; i++)
		{
			decoded += (char) convert(charInt[i]);
		}

		return Validator.isEmpty(decoded) ? null : decoded;
	}
}
