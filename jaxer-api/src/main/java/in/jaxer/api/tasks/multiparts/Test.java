
package in.jaxer.api.tasks.multiparts;

import in.jaxer.api.annotations.MultipartTask;
import in.jaxer.api.dtos.MultipartRequestDto;
import in.jaxer.api.request.AbstractMultipartTask;

/**
 *
 * @author Shakir Ansari
 */
@MultipartTask
public class Test extends AbstractMultipartTask
{
//	private static final Logger logger = Logger.getLogger(Test.class);

	private MultipartRequestDto multipartRequestDto = null;

//	@Override
//	public void doMultipartTask(Connection connection) throws Exception
//	{
//		List<Part> partList = getPartList();
//		logger.debug("partList: [" + partList + "]");
//
//		for (Part part : partList)
//		{
//			logger.debug("Uploaded file name is: [" + part.getSubmittedFileName() + "]");
//		}
//
//	}
}
