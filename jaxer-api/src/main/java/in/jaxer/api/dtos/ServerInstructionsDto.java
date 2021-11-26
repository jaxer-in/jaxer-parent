
package in.jaxer.api.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shakir Ansari
 */
public class ServerInstructionsDto
{

	private List<String> serverInstructions;

	public List<String> getServerInstructions()
	{
		return serverInstructions;
	}

	public void addServerInstruction(String instruction)
	{
		if (this.serverInstructions == null)
		{
			this.serverInstructions = new ArrayList<>();
		}

		this.serverInstructions.add(instruction);
	}
}
