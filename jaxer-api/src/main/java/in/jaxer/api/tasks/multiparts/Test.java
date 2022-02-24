
package in.jaxer.api.tasks.multiparts;

import in.jaxer.api.annotations.MultipartTask;
import in.jaxer.api.dtos.MultipartRequestDto;
import in.jaxer.api.core.tasks.AbstractMultipartTask;
import java.sql.Connection;
import javax.servlet.http.Part;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author Shakir Ansari
 */
@Log4j2
@MultipartTask
public class Test extends AbstractMultipartTask
{
	private MultipartRequestDto multipartRequestDto = null;

	@Override
	public void doMultipartTask(Connection connection) throws Exception
	{
		Part part = getSinglePart();

		log.debug("part.getSubmittedFileName: {}", part.getSubmittedFileName());
	}

}
