
package in.jaxer.sdbms.exceptions;

import in.jaxer.core.exceptions.JaxerCoreException;
import lombok.Getter;

/**
 *
 * @author Shakir
 * Date 25 Oct, 2021 - 11:28:56 PM
 */
@Getter
public class JaxerSDBMSException extends JaxerCoreException
{

	public JaxerSDBMSException()
	{
	}

	public JaxerSDBMSException(String message)
	{
		super(message);
	}

	public JaxerSDBMSException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public JaxerSDBMSException(Throwable cause)
	{
		super(cause);
	}
}
