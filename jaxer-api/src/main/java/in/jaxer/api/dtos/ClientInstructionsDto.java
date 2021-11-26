
package in.jaxer.api.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shakir Ansari
 */
public class ClientInstructionsDto
{

	private List<String> clientInstructions;

	public List<String> getClientInstructions()
	{
		return clientInstructions;
	}

	public void addClientInstruction(String instruction)
	{
		if (this.clientInstructions == null)
		{
			this.clientInstructions = new ArrayList<>();
		}

		this.clientInstructions.add(instruction);
	}
}
