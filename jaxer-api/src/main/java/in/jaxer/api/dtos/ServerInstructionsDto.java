
package in.jaxer.api.dtos;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 *
 * @author Shakir Ansari
 */
@Getter
public class ServerInstructionsDto
{

	private List<String> serverInstructions;

	public void addServerInstruction(String instruction)
	{
		if (this.serverInstructions == null)
		{
			this.serverInstructions = new ArrayList<>();
		}

		this.serverInstructions.add(instruction);
	}
}
