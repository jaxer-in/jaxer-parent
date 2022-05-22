package in.jaxer.core.encoders;

import in.jaxer.core.utilities.JValidator;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class OctalEncoder implements Encoder
{
	public String convert(int x)
	{
		return Integer.toOctalString(x);
	}

	public int convert(String string)
	{
		return Integer.parseInt(string, 8);
	}

	@Override
	public String encode(String message)
	{
		JValidator.throwWhenNullOrEmpty(message);

		String encoded = "";

		for (int i = 0; i < message.length(); i++)
		{
			encoded += (i % 2 == 0) ? "8" : "9";
			encoded += convert(message.charAt(i));
		}

		return encoded;
	}

	@Override
	public String decode(String message)
	{
		JValidator.throwWhenNullOrEmpty(message);

		String pattern = "^[0-9]*$";
		if (!message.matches(pattern))
		{
			throw new IllegalArgumentException("Invalid number format");
		}

		//spliting with two delimiters [8 or 9]
		String[] charInt = message.split("[89]");
		log.debug(Arrays.toString(charInt));

		String decoded = "";

		//i starting from 1, bc charInt[]'s first value will be empty
		for (int i = 1; i < charInt.length; i++)
		{
			decoded += (char) convert(charInt[i]);
		}

		return JValidator.isNullOrEmpty(decoded) ? null : decoded;
	}
}
