package in.jaxer.api.constants;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Shakir Ansari
 */
@Getter
@Setter
@ToString
public class ApiStatus
{

	private int code;

	private String message;

	public ApiStatus(int code)
	{
		this.code = code;
	}

	public ApiStatus(int code, String message)
	{
		this.code = code;
		this.message = message;
	}
}
