package in.jaxer.core.encoders;

import in.jaxer.core.interfaces.Encoder;
import in.jaxer.core.utilities.JValidator;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;

/**
 * @author Shakir
 * @since 0.0.1
 */
@Log4j2
public class HexaEncoder implements Encoder
{
	private final String digits = "0123456789ABCDEF";

	public String convert(int x)
	{
		//return Integer.toHexString(x);

		if (x == 0)
		{
			return "0";
		}
		String hex = "";
		while (x > 0)
		{
			int digit = x % 16;                // rightmost digit
			hex = digits.charAt(digit) + hex;  // string concatenation
			x = x / 16;
		}
		return hex;
	}

	public int convert(String s)
	{
		//return Integer.parseInt(string, 16);

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

	@Override
	public String encode(String message)
	{
		JValidator.throwWhenBlank(message);

		String encoded = "";

		for (int i = 0; i < message.length(); i++)
		{
			encoded += (i % 2 == 0) ? "g" : "h";
			encoded += convert(message.charAt(i));
		}

		return encoded;
	}

	@Override
	public String decode(String message)
	{
		JValidator.throwWhenBlank(message);

		String messageLowerCase = message.toLowerCase();

		String pattern = "^[0-9a-h]*$";
		if (!messageLowerCase.matches(pattern))
		{
			throw new IllegalArgumentException("Invalid number format");
		}

		//spliting with two delimiters [g or h]
		String[] charInt = messageLowerCase.split("[gh]");
		log.debug(Arrays.toString(charInt));

		String decoded = "";

		//i starting from 1, bc charInt[]'s first value will be empty
		for (int i = 1; i < charInt.length; i++)
		{
			decoded += (char) convert(charInt[i]);
		}

		return JValidator.isBlank(decoded) ? null : decoded;
	}
}
