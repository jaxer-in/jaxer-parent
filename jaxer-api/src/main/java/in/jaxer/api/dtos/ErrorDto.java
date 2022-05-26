package in.jaxer.api.dtos;

import lombok.ToString;

import java.util.List;

/**
 * @author Shakir Ansari
 */
@ToString
public class ErrorDto
{
	public String error;
	public int errorCode;
	public String errorMessage;
	public List<String> stacktraceList;
	public int httpStatus;
}
