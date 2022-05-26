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

	public ClientInstructionDto clientInstructionDto;

	public static void main(String[] args)
	{
		ErrorDto errorDto = new ErrorDto();
		errorDto.clientInstructionDto = new ClientInstructionDto();
		errorDto.clientInstructionDto.addInstruction("abc");
		System.out.println(errorDto.clientInstructionDto.toString());
	}
}
