
package in.jaxer.core.net;

import in.jaxer.core.constants.ContentType;
import in.jaxer.core.constants.HttpConstants;
import in.jaxer.core.constants.Singletons;
import in.jaxer.core.utilities.Files;
import in.jaxer.core.utilities.Utilities;
import in.jaxer.core.utilities.Validator;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shakir Ansari
 */
public class Servlets
{

	static public String getRequestForwardURI(HttpServletRequest request)
	{
		return request.getAttribute("javax.servlet.forward.request_uri").toString();
	}

	public static String getIpAddress(HttpServletRequest httpServletRequest)
	{
		String ip = httpServletRequest.getHeader("X-Forwarded-For");
		if (Validator.isEmpty(ip) || "unknown".equalsIgnoreCase(ip))
		{
			ip = httpServletRequest.getHeader("Proxy-Client-IP");
		}

		if (Validator.isEmpty(ip) || "unknown".equalsIgnoreCase(ip))
		{
			ip = httpServletRequest.getHeader("WL-Proxy-Client-IP");
		}

		if (Validator.isEmpty(ip) || "unknown".equalsIgnoreCase(ip))
		{
			ip = httpServletRequest.getHeader("HTTP_CLIENT_IP");
		}

		if (Validator.isEmpty(ip) || "unknown".equalsIgnoreCase(ip))
		{
			ip = httpServletRequest.getHeader("HTTP_X_FORWARDED_FOR");
		}

		if (Validator.isEmpty(ip) || "unknown".equalsIgnoreCase(ip))
		{
			ip = httpServletRequest.getRemoteAddr();
		}

		return ip;
	}

	static public List<String> getRequestPathParams(HttpServletRequest httpServletRequest)
	{
		String paramString = httpServletRequest.getPathInfo();
		if (paramString == null)
		{
			return null;
		}

		List<String> list = new ArrayList();
		String params[] = paramString.split("/");
		for (String param : params)
		{
			if (Validator.isNotEmpty(param))
			{
				list.add(param);
			}
		}

		return list.isEmpty() ? null : list;
	}

	static public int getStatusCode(HttpServletRequest httpServletRequest)
	{
		return (int) httpServletRequest.getAttribute("javax.servlet.error.status_code");
	}

	static public Throwable getThrowable(HttpServletRequest httpServletRequest)
	{
		return (Throwable) httpServletRequest.getAttribute("javax.servlet.error.exception");
	}

	static public String getExceptionClassName(HttpServletRequest httpServletRequest)
	{
		return ((Throwable) httpServletRequest.getAttribute("javax.servlet.error.exception")).getClass().getName();
	}

	static public void setResponseTextHtml(HttpServletResponse httpServletResponse)
	{
		httpServletResponse.setContentType(ContentType.TEXT_HTML);
		httpServletResponse.setCharacterEncoding(ContentType.UTF_8);
	}

	static public void setResponseJson(HttpServletResponse httpServletResponse)
	{
		httpServletResponse.setContentType(ContentType.APPLICATION_JSON);
		httpServletResponse.setCharacterEncoding(ContentType.UTF_8);
	}

	static public void setResponseImageJPG(HttpServletResponse httpServletResponse)
	{
		httpServletResponse.setContentType(ContentType.IMAGE_JPG);
	}

	//<editor-fold defaultstate="collapsed" desc=" --- cache response --- ">
	static public void updateCacheHeaders(HttpServletResponse httpServletResponse, long milli)
	{
		httpServletResponse.setStatus(HttpServletResponse.SC_NOT_MODIFIED);

		final long expiry = System.currentTimeMillis() + milli;

		httpServletResponse.setDateHeader(HttpConstants.Expires, expiry);
		httpServletResponse.setHeader(HttpConstants.Cache_Control, "max-age=" + milli);
	}
	//</editor-fold>

	@Deprecated
	static public String getRequestBody(HttpServletRequest httpServletRequest, boolean autoClose) throws IOException
	{
		int bytesRead = -1;
		char[] charBuffer = new char[128];

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

	static public String getRequestBody(HttpServletRequest httpServletRequest) throws IOException
	{
		return httpServletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
	}

	static public void methodNotSupported(HttpServletRequest request, HttpServletResponse response, String method) throws IOException
	{
		if (method.equalsIgnoreCase(request.getMethod()))
		{
			String msg = "Method [" + request.getMethod().toUpperCase() + "] not supported by this URL.";

			if (request.getProtocol().endsWith("1.1"))
			{
				response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
			} else
			{
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
			}
		}
	}

	static public void printResponse(HttpServletResponse httpServletResponse, Object object) throws IOException
	{
		try (PrintWriter printWriter = httpServletResponse.getWriter();)
		{
			printWriter.write(object.toString());
			printWriter.flush();
		}
	}

	static public void printJsonResponse(HttpServletResponse httpServletResponse, Object obj, boolean isPrettyPrint) throws IOException
	{
		setResponseJson(httpServletResponse);
		printResponse(httpServletResponse, Singletons.getGson(isPrettyPrint).toJson(obj));
	}

	static public void printXlsResponse(HttpServletResponse httpServletResponse, String fileName, String data) throws IOException
	{
		httpServletResponse.setContentType(ContentType.SPREADSHEET_XLS);
		httpServletResponse.setCharacterEncoding(ContentType.UTF_8);
		httpServletResponse.setHeader("Content-Disposition", "inline; filename=" + fileName + ".xls");

		printResponse(httpServletResponse, data);
	}

	static public void printXlsxResponse(HttpServletResponse httpServletResponse, String fileName, String data) throws IOException
	{
		//httpServletResponse.setContentType("application/octet-stream;charset=UTF-8");
		httpServletResponse.setContentType(ContentType.SPREADSHEET_XLSX);
		httpServletResponse.setCharacterEncoding(ContentType.UTF_8);
		httpServletResponse.setHeader("Content-Disposition", "inline; filename=" + fileName + ".xlsx");

		printResponse(httpServletResponse, data);
	}

	static public void printFile(HttpServletResponse httpServletResponse, File file, String mimeType) throws FileNotFoundException, IOException
	{
		httpServletResponse.setContentType(mimeType);
		System.out.println("AbstractServlet.printFile() - mimeType: [" + mimeType + "]");

		try (FileInputStream fileInputStream = new FileInputStream(file);
			 OutputStream outputStream = new BufferedOutputStream(httpServletResponse.getOutputStream()))
		{
			Files.copyBytes(fileInputStream, outputStream);
		}
	}

	static public void printFile(HttpServletResponse httpServletResponse, File file) throws FileNotFoundException, IOException
	{
		printFile(httpServletResponse, file, Files.getDefaultMimeType(file));
	}

	static public void printImage(HttpServletResponse httpServletResponse, File imageFile) throws FileNotFoundException, IOException
	{
		printFile(httpServletResponse, imageFile, ContentType.IMAGE_JPG);
	}

	static public void downloadFile(HttpServletResponse httpServletResponse, File file, boolean renameWithTimeStamp) throws IOException
	{
		String filename = file.getName();
		String mimeType = Files.getDefaultMimeType(file);

		System.out.println("Servlets.downloadFile() - mimeType: [" + mimeType + "], filename: [" + filename + "]");

		if (renameWithTimeStamp)
		{
			String ext = Utilities.getExtensionWithDot(filename);
			filename = filename.replace(ext, "_" + System.currentTimeMillis() + ext);
		}

		System.out.println("skr.web.Servlets.downloadFile()");

		httpServletResponse.setContentType(mimeType);
		httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		httpServletResponse.setContentLengthLong(file.length());

		try (OutputStream outputStream = httpServletResponse.getOutputStream();
			 FileInputStream fileInputStream = new FileInputStream(file);)
		{
			Files.copyBytes(fileInputStream, outputStream);
		}
	}

	/**
	 * This method will check if the request's content type is multipart/form-data
	 * <br>
	 * In simple words, this method will let you know if the request contains attachments or not
	 *
	 * @param request
	 * @return boolean
	 */
	static public boolean isMultipartRequest(HttpServletRequest request)
	{
		return request.getContentType() != null && request.getContentType().toLowerCase().contains(ContentType.MULTIPART_FORM_DATA);
	}
}
