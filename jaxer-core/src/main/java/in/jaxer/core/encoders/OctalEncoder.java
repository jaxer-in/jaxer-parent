
package in.jaxer.core.encoders;

import in.jaxer.core.utilities.JValidator;

/**
 *
 * @author Shakir Ansari
 */
public class OctalEncoder
{

	public static String convert(int x)
	{
		return Integer.toOctalString(x);
	}

	public static int convert(String string)
	{
		return Integer.parseInt(string, 8);
	}

	public static String encode(String message)
	{
		JValidator.requireNotEmpty(message);

		String encoded = "";

		for (int i = 0; i < message.length(); i++)
		{
			encoded += (i % 2 == 0) ? "8" : "9";
			encoded += convert((int) message.charAt(i));
		}

		return encoded;
	}

	public static String decode(String message)
	{
		JValidator.requireNotEmpty(message);

		String pattern = "^[0-9]*$";
		if (!message.matches(pattern))
		{
			throw new IllegalArgumentException("Invalid number format");
		}

		//spliting with two delimiters [8 or 9]
		String[] charInt = message.split("[89]");
//		System.out.println("OctalEncoder.decode() - " + Arrays.toString(charInt));

		String decoded = "";

		//i starting from 1, bc charInt[]'s first value will be empty
		for (int i = 1; i < charInt.length; i++)
		{
			decoded += (char) convert(charInt[i]);
		}

		return JValidator.isEmpty(decoded) ? null : decoded;
	}
}
