package in.jaxer.api.core.tasks;

import in.jaxer.core.utilities.JValidator;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.Part;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Shakir Ansari
 */
@Log4j2
public abstract class AbstractMultipartTask extends AbstractRestTask
{

	protected int uniqueMediaCount = 0;

	protected int duplicateMediaCount = 0;

	public abstract void doMultipartTask(Connection connection) throws Exception;

	@Override
	public void doTask(Connection connection) throws Exception
	{
		preMultipartTask(connection);

		doMultipartTask(connection);

		postMultipartTask(connection);

		setParameter("taskResponseCode", "101");
		setParameter("uniqueMediaCount", String.valueOf(uniqueMediaCount));
		setParameter("duplicateMediaCount", String.valueOf(duplicateMediaCount));
		setParameter("totalMediaCount", String.valueOf(uniqueMediaCount + duplicateMediaCount));
	}

	protected void preMultipartTask(Connection connection)
	{
		log.debug("preMultipartTask executed");
	}

	protected void postMultipartTask(Connection connection)
	{
		log.debug("preMultipartTask executed");
	}

	protected Part getSinglePart() throws Exception
	{
		List<Part> parts = getPartList();
		if (JValidator.isNullOrEmpty(parts))
		{
			return null;
		}
		return parts.get(0);
	}

	private List<Part> getPartList() throws Exception
	{
		List<Part> partList = new ArrayList<>();
		Collection<Part> parts = getRequestResponseDto().getHttpServletRequest().getParts();

		if (JValidator.isNullOrEmpty(parts))
		{
			return partList;
		}

		for (Part part : parts)
		{
			if (JValidator.isNullOrEmpty(part.getSubmittedFileName()))
			{
				continue;
			}
			partList.add(part);
		}

		return partList;
	}

//	@Deprecated
//	private String getPartChecksum(Part part) throws Exception
//	{
//		InputStream inputStream = new BufferedInputStream(part.getInputStream());
//		inputStream.mark((int) part.getSize());
//		return HashH.getFileChecksum(inputStream);
//	}
//	@Deprecated
//	private Dimension getImageDimension(Part part) throws Exception
//	{
//		InputStream inputStream = new BufferedInputStream(part.getInputStream());
//
//		Dimension dimension = Utilities.getImageDimension(inputStream);
//		if (dimension == null)
//		{
//			dimension = new Dimension(0, 0);
//		}
//
//		return dimension;
//	}
}
