package in.jaxer.core.net;

import in.jaxer.core.constants.ContentType;
import in.jaxer.core.utilities.JValidator;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class ServletDownloadDemo extends HttpServlet
{

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String filename = request.getParameter("filename");
		String filepath = request.getParameter("filepath");
		String fullFile = filepath + File.separator + filename;

		try (OutputStream outStream = response.getOutputStream();
			 FileInputStream fileInputStream = new java.io.FileInputStream(fullFile);)
		{
			String mimeType = getServletContext().getMimeType(fullFile);
			log.debug("before mimeType: {}", mimeType);

			if (JValidator.isNullOrEmpty(mimeType))
			{
				mimeType = ContentType.APPLICATION_OCTET_STREAM;
			}

			log.debug("after mimeType: {}", mimeType);

			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
			response.setContentLength((int) new File(fullFile).length());

			int i;
			final byte[] bytes = new byte[1024];
			while ((i = fileInputStream.read(bytes)) != -1)
			{
				outStream.write(bytes, 0, i);
			}
		}
	}

}
