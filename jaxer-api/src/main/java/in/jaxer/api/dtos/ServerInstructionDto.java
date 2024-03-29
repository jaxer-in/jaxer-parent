package in.jaxer.api.dtos;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shakir
 * @since 0.0.1
 */
@ToString
public class ServerInstructionDto
{
	@Getter
	private List<String> instructionList;

	public void addInstruction(String instruction)
	{
		if (this.instructionList == null)
		{
			this.instructionList = new ArrayList<>();
		}

		this.instructionList.add(instruction);
	}
}
