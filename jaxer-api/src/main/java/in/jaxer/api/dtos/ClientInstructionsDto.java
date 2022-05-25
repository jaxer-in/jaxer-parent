package in.jaxer.api.dtos;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shakir Ansari
 */
@Getter
public class ClientInstructionsDto
{

	private List<String> clientInstructions;

	public void addClientInstruction(String instruction)
	{
		if (this.clientInstructions == null)
		{
			this.clientInstructions = new ArrayList<>();
		}

		this.clientInstructions.add(instruction);
	}
}
