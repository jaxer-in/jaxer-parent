package in.jaxer.core.net;

import in.jaxer.core.HttpUtils;
import in.jaxer.core.constants.ContentType;
import in.jaxer.core.utilities.Files;
import in.jaxer.core.utilities.JUtilities;
import in.jaxer.core.utilities.JValidator;
import in.jaxer.core.utilities.JsonHandler;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author Shakir Ansari
 */
public class AbstractServlet extends GenericServlet
{
	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException
	{
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

		switch (httpServletRequest.getMethod().toUpperCase())
		{
			case HttpUtils.Method.GET:
				this.doGet(httpServletRequest, httpServletResponse);
				break;

			case HttpUtils.Method.POST:
				this.doPost(httpServletRequest, httpServletResponse);
				break;

			case HttpUtils.Method.DELETE:
				this.doDelete(httpServletRequest, httpServletResponse);
				break;

			case HttpUtils.Method.HEAD:
				this.doHead(httpServletRequest, httpServletResponse);
				break;

			case HttpUtils.Method.OPTIONS:
				this.doOptions(httpServletRequest, httpServletResponse);
				break;

			case HttpUtils.Method.PUT:
				this.doPut(httpServletRequest, httpServletResponse);
				break;

			case HttpUtils.Method.TRACE:
				this.doTrace(httpServletRequest, httpServletResponse);
				break;

			default:
				defaultService(httpServletRequest, httpServletResponse);
		}
	}

	protected String getRequestBody(HttpServletRequest httpServletRequest, boolean autoClose) throws IOException
	{
		int bytesRead = -1;
		char[] charBuffer = new char[128];

		//final String requestBody = this.httpServletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpServletRequest.getInputStream()));

		StringBuilder stringBuilder = new StringBuilder();
		while ((bytesRead = bufferedReader.read(charBuffer)) > 0)
		{
			stringBuilder.append(charBuffer, 0, bytesRead);
		}

		if (autoClose)
		{
			try
			{
				bufferedReader.close();
			} catch (Exception ex)
			{
			}
		}

		return stringBuilder.toString();
	}

	protected List<String> getPathParams(HttpServletRequest httpServletRequest)
	{
		String paramString = httpServletRequest.getPathInfo();
		return JValidator.isNullOrEmpty(paramString) ? null : Arrays.asList(paramString.split("/"));
	}

	private void updateCacheHeaders(HttpServletResponse httpServletResponse, long maxCacheAge, long expiry)
	{
		httpServletResponse.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
		httpServletResponse.setDateHeader("Expires", expiry);
		httpServletResponse.setHeader("Cache-Control", "max-age=" + maxCacheAge);
	}

	/**
	 * Use this method if you want cache your response for <b>30days</b>
	 */
	protected void cacheResponse(HttpServletResponse httpServletResponse)
	{
		//one month in seconds
		final long maxCacheAge = 60 * 60 * 24 * 30;
		final long expiry = System.currentTimeMillis() + maxCacheAge * 1000;

		updateCacheHeaders(httpServletResponse, maxCacheAge, expiry);
	}

	protected void cacheResponse(HttpServletResponse httpServletResponse, long maxCacheAge, long expiry)
	{
		updateCacheHeaders(httpServletResponse, maxCacheAge, expiry);
	}

	protected void setResponseTextHtml(HttpServletResponse response)
	{
		response.setContentType(ContentType.TEXT_HTML);
		response.setCharacterEncoding(ContentType.UTF_8);
	}

	protected void setResponseJson(HttpServletResponse response)
	{
		response.setContentType(ContentType.APPLICATION_JSON);
		response.setCharacterEncoding(ContentType.UTF_8);
	}

	protected void setResponseImageJPG(HttpServletResponse response)
	{
		response.setContentType(ContentType.IMAGE_JPG);
	}

	protected void setResponseImageJPEG(HttpServletResponse response)
	{
		response.setContentType(ContentType.IMAGE_JPEG);
	}

	private void writeResponse(HttpServletResponse httpServletResponse, Object object) throws IOException
	{
		try (PrintWriter printWriter = httpServletResponse.getWriter())
		{
			printWriter.write(object.toString());
			printWriter.flush();
		}
	}

	protected void writeJsonResponse(HttpServletResponse httpServletResponse, Object obj) throws IOException
	{
		setResponseJson(httpServletResponse);
		writeResponse(httpServletResponse, JsonHandler.getGson().toJson(obj));
	}

	protected void writeXlsResponse(HttpServletResponse httpServletResponse, String fileName, String data) throws IOException
	{
		httpServletResponse.setContentType(ContentType.SPREADSHEET_XLS);
		httpServletResponse.setCharacterEncoding(ContentType.UTF_8);
		httpServletResponse.setHeader("Content-Disposition", "inline; filename=" + fileName + ".xls");

		writeResponse(httpServletResponse, data);
	}

	protected void writeXlsxResponse(HttpServletResponse httpServletResponse, String fileName, String data) throws IOException
	{
		//this.httpServletResponse.setContentType("application/octet-stream;charset=UTF-8");
		httpServletResponse.setContentType(ContentType.SPREADSHEET_XLSX);
		httpServletResponse.setCharacterEncoding(ContentType.UTF_8);
		httpServletResponse.setHeader("Content-Disposition", "inline; filename=" + fileName + ".xlsx");

		writeResponse(httpServletResponse, data);
	}

	protected void printFile(HttpServletResponse httpServletResponse, File file, String mimeType) throws IOException
	{
		httpServletResponse.setContentType(mimeType);
		System.out.println("AbstractServlet.printFile() - mimeType: [" + mimeType + "]");

		try (FileInputStream fileInputStream = new FileInputStream(file);
			 OutputStream outputStream = new BufferedOutputStream(httpServletResponse.getOutputStream()))
		{
			Files.copyBytes(fileInputStream, outputStream);
		}
	}

	protected void printFile(HttpServletResponse httpServletResponse, File file) throws IOException
	{
		String mimeType = getServletContext().getMimeType(file.getName());
		if (JValidator.isNullOrEmpty(mimeType))
		{
			mimeType = ContentType.APPLICATION_OCTET_STREAM;
		}

		printFile(httpServletResponse, file, mimeType);
	}

	protected void printImage(HttpServletResponse httpServletResponse, File imageFile) throws IOException
	{
		printFile(httpServletResponse, imageFile, ContentType.IMAGE_JPG);
	}

	protected void downloadFile(HttpServletResponse httpServletResponse, File file, boolean renameWithTimeStamp) throws IOException
	{
		String filename = file.getName();
		String mimeType = getServletContext().getMimeType(filename);
		if (JValidator.isNullOrEmpty(mimeType))
		{
			mimeType = ContentType.APPLICATION_OCTET_STREAM;
		}

		System.out.println("AbstractServlet.downloadFile() - mimeType: [" + mimeType + "], filename: [" + filename + "]");

		if (renameWithTimeStamp)
		{
			String ext = JUtilities.getExtensionWithDot(filename);
			filename = filename.replace(ext, "_" + System.currentTimeMillis() + ext);
		}

		httpServletResponse.setContentType(mimeType);
		httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		httpServletResponse.setContentLengthLong(file.length());

		try (OutputStream outputStream = httpServletResponse.getOutputStream();
			 FileInputStream fileInputStream = new FileInputStream(file))
		{
			Files.copyBytes(fileInputStream, outputStream);
		}
	}

	private void defaultService(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException
	{
		String msg = "Method [" + httpServletRequest.getMethod().toUpperCase() + "] not supported by this URL.";

		httpServletResponse.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
	}

	protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
	{
		defaultService(httpServletRequest, httpServletResponse);
	}

	protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
	{
		defaultService(httpServletRequest, httpServletResponse);
	}

	protected void doHead(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
	{
		defaultService(httpServletRequest, httpServletResponse);
	}

	protected void doPut(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
	{
		defaultService(httpServletRequest, httpServletResponse);
	}

	protected void doDelete(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
	{
		defaultService(httpServletRequest, httpServletResponse);
	}

	protected void doOptions(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
	{
		defaultService(httpServletRequest, httpServletResponse);
	}

	protected void doTrace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
	{
		defaultService(httpServletRequest, httpServletResponse);
	}
}
