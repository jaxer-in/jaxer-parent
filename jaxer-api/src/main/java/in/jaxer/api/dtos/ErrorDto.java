
package in.jaxer.api.dtos;

import java.util.HashMap;
import java.util.List;
import lombok.ToString;

/**
 *
 * @author Shakir Ansari
 */
@ToString
public class ErrorDto
{

	public String error;

	public int errorCode;

	public String errorMessage;

	public HashMap<String, List<String>> stacktraceHashMap;

	public int httpStatus;

}