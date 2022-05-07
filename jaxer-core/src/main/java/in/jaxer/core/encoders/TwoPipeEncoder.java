
package in.jaxer.core.encoders;

import in.jaxer.core.exceptions.ValidationException;
import in.jaxer.core.utilities.JValidator;

/**
 *
 * @author Shakir Ansari
 */
public class TwoPipeEncoder implements Encoder
{

	private static final String EXTENSION = ".tp";

	@Override
	public String encode(String message)
	{
		JValidator.requireNotEmpty(message);

		String firstHalf = "", secondHalf = "";
		for (int i = 0; i < message.length(); i++)
		{
			if (i % 2 == 0)
			{
				//-- 0,2,4,6.....
				firstHalf += message.charAt(i);
			} else
			{
				//-- 1,3,5,7.....
				secondHalf += message.charAt(i);
			}
		}
		return firstHalf + secondHalf + EXTENSION;
	}

	@Override
	public String decode(String msg)
	{
		JValidator.requireNotEmpty(msg);

		int len = msg.length();

		if (!msg.endsWith(EXTENSION))
		{
			throw new ValidationException(INVALID_ENCRYPTION_FORMAT);
		}

		//-- removing extension
		msg = msg.substring(0, len - EXTENSION.length());

		//-- new lenght
		len = msg.length();

		int point = (len + 1) / 2;
		String firstHalf = msg.substring(0, point);
		String secondHalf = msg.substring(point);

		String deMsg = "";
		int lenF = firstHalf.length();
		int lenH = secondHalf.length();
		for (int i = 0; i < lenF || i < lenH; i++)
		{
			if (i < lenF)
			{
				deMsg += firstHalf.charAt(i);
			}
			if (i < lenH)
			{
				deMsg += secondHalf.charAt(i);
			}
		}

		return deMsg;
	}

}
