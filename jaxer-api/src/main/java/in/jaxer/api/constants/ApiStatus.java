
package in.jaxer.api.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Shakir Ansari
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class ApiStatus
{

	private int code;

	private String message;

	public ApiStatus(int code)
	{
		this.code = code;
	}
}
