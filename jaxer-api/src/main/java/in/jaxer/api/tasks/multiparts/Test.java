package in.jaxer.api.tasks.multiparts;

import in.jaxer.api.annotations.MultipartTask;
import in.jaxer.api.core.tasks.AbstractMultipartTask;
import in.jaxer.api.dtos.MultipartRequestDto;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.Part;
import java.sql.Connection;

/**
 * @author Shakir
 */
@Log4j2
@MultipartTask
public class Test extends AbstractMultipartTask
{
	private final MultipartRequestDto multipartRequestDto = null;

	@Override
	public void doMultipartTask(Connection connection) throws Exception
	{
		Part part = getSinglePart();

		log.debug("part.getSubmittedFileName: {}", part.getSubmittedFileName());
	}

}
