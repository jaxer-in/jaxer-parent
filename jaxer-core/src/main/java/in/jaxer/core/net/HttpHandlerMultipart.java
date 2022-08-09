package in.jaxer.core.net;

import in.jaxer.core.FileUtils;
import in.jaxer.core.constants.ContentType;
import in.jaxer.core.utilities.JValidator;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shakir Ansari
 */
public class HttpHandlerMultipart
{
	private final String boundary;
	//	private HttpURLConnection httpURLConnection;
	private final String charset;
	private final String requestURL;
	//	private OutputStream outputStream;
//	private PrintWriter printWriter;
	private Map<String, String> headers;
	private Map<String, String> parameters;
	private Map<String, File> fileParts;

	public HttpHandlerMultipart(String requestURL) throws IOException
	{
		this(requestURL, ContentType.UTF_8);
	}

	public HttpHandlerMultipart(String requestURL, String charset) throws IOException
	{
		this.charset = charset;

		// creates a unique boundary based on time stamp
		boundary = "===" + System.currentTimeMillis() + "===";
		this.requestURL = requestURL;

//		httpURLConnection.setRequestProperty("Test", "Bonjour");
//		outputStream = httpURLConnection.getOutputStream();
//		printWriter = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);
	}

	private void setParameter(PrintWriter printWriter, String name, String value)
	{
		printWriter.append("--" + boundary).append(System.lineSeparator());
		printWriter.append("Content-Disposition: form-data; name=\"" + name + "\"").append(System.lineSeparator());
		printWriter.append("Content-Type: text/plain; charset=" + charset).append(System.lineSeparator());
		printWriter.append(System.lineSeparator());
		printWriter.append(value).append(System.lineSeparator());
		printWriter.flush();
	}

	private void setParameter(PrintWriter printWriter, OutputStream outputStream, String fieldName, File uploadFile) throws IOException
	{
		String fileName = uploadFile.getName();

		printWriter.append("--" + boundary).append(System.lineSeparator());
		printWriter.append("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + fileName + "\"").append(System.lineSeparator());
		printWriter.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName)).append(System.lineSeparator());
		printWriter.append("Content-Transfer-Encoding: binary").append(System.lineSeparator());
		printWriter.append(System.lineSeparator());
		printWriter.flush();

		try (FileInputStream fileInputStream = new FileInputStream(uploadFile))
		{
			byte[] buffer = new byte[FileUtils.BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = fileInputStream.read(buffer)) != -1)
			{
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.flush();
		}

		printWriter.append(System.lineSeparator());
		printWriter.flush();
	}

	private void setHeader(PrintWriter printWriter, String name, String value)
	{
		printWriter.append(name + ": " + value).append(System.lineSeparator());
		printWriter.flush();
	}

	public void setParameter(String name, String value)
	{
		if (parameters == null)
		{
			parameters = new HashMap<>();
		}

		parameters.put(name, value);
	}

	public void setParameter(String fieldName, File uploadFile)
	{
		if (fileParts == null)
		{
			fileParts = new HashMap<>();
		}

		fileParts.put(fieldName, uploadFile);
	}

	public void setHeaders(String name, String value)
	{
		if (headers == null)
		{
			headers = new HashMap<>();
		}

		headers.put(name, value);
	}

	// <editor-fold defaultstate="collapsed" desc=" --- Commented code --- ">
//	public String finish() throws IOException
//	{
//		StringBuilder stringBuilder = new StringBuilder();
//		List<String> response = new ArrayList<>();
//
//		printWriter.append(System.lineSeparator()).flush();
//		printWriter.append("--" + boundary + "--").append(System.lineSeparator());
//		printWriter.close();
//
//		// checks server's status code first
//		int status = httpURLConnection.getResponseCode();
//		if (status == HttpURLConnection.HTTP_OK)
//		{
//			try (InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
//				 BufferedReader reader = new BufferedReader(inputStreamReader))
//			{
//				String line = null;
//				while ((line = reader.readLine()) != null)
//				{
//					stringBuilder.append(line).append(System.lineSeparator());
//				}
//			}
//			httpURLConnection.disconnect();
//		} else
//		{
//			throw new IOException("Server returned non-OK status: " + status);
//		}
//
//		return stringBuilder.toString();
//	}
	// </editor-fold>
	public String execute()
	{
		StringBuilder stringBuilder = new StringBuilder();

		try
		{
			URL url = new URL(requestURL);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setDoOutput(true); // indicates POST method
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			httpURLConnection.setRequestProperty("User-Agent", "in.jaxer-agent");

			try (OutputStream outputStream = httpURLConnection.getOutputStream();
				 PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream, charset), true))
			{
				//Headers
				if (JValidator.isNotNullAndNotEmpty(headers))
				{
					for (Map.Entry<String, String> entry : headers.entrySet())
					{
						setHeader(printWriter, entry.getKey(), entry.getValue());
					}
				}

				//Parameters
				if (JValidator.isNotNullAndNotEmpty(parameters))
				{
					for (Map.Entry<String, String> entry : parameters.entrySet())
					{
						setParameter(printWriter, entry.getKey(), entry.getValue());
					}
				}

				//Files
				if (JValidator.isNotNullAndNotEmpty(fileParts))
				{
					for (Map.Entry<String, File> entry : fileParts.entrySet())
					{
						setParameter(printWriter, outputStream, entry.getKey(), entry.getValue());
					}
				}

				printWriter.append(System.lineSeparator()).flush();
				printWriter.append("--" + boundary + "--").append(System.lineSeparator());
				printWriter.flush();

				// checks server's status code first
				int status = httpURLConnection.getResponseCode();
				if (status == HttpURLConnection.HTTP_OK)
				{
					try (InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
						 BufferedReader reader = new BufferedReader(inputStreamReader))
					{
						String line = null;
						while ((line = reader.readLine()) != null)
						{
							stringBuilder.append(line).append(System.lineSeparator());
						}
					}
					httpURLConnection.disconnect();
				} else
				{
					throw new IOException("Server returned non-OK status: " + status);
				}

				httpURLConnection.disconnect();

				return stringBuilder.toString();
			}
		} catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}
}
