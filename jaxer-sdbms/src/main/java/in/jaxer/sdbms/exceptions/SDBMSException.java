
package in.jaxer.sdbms.exceptions;

import lombok.Getter;

/**
 *
 * @author Shakir
 * Date 25 Oct, 2021 - 11:28:56 PM
 */
@Getter
public class SDBMSException extends RuntimeException
{

	private Exception exception;

	public SDBMSException()
	{
	}

	public SDBMSException(String msg)
	{
		super(msg);
	}

	public SDBMSException(Exception exception)
	{
		this.exception = exception;
	}
}
