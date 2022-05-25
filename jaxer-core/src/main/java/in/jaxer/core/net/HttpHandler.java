package in.jaxer.core.net;

import in.jaxer.core.constants.ContentType;
import in.jaxer.core.constants.HttpConstants;
import in.jaxer.core.utilities.Time;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class HttpHandler
{

	public static String doHttpRequest(String httpMethod, String urlString, String payload)
	{
		log.debug("httpMethod: {}", httpMethod);
		log.debug("urlString: {}", urlString);
		log.debug("payload: {}", payload);

		try
		{
			long startMiliSeconds = System.currentTimeMillis();

			URL url = new URL(urlString);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

			httpURLConnection.setRequestProperty(HttpConstants.Accept, ContentType.APPLICATION_JSON);
			httpURLConnection.setRequestMethod(httpMethod);

			if (payload != null)
			{
				httpURLConnection.setDoOutput(true);

				httpURLConnection.setRequestProperty(HttpConstants.Content_Type, ContentType.APPLICATION_JSON);

				try (OutputStream outputStream = httpURLConnection.getOutputStream())
				{
					byte[] input = payload.getBytes(ContentType.UTF_8);
					outputStream.write(input, 0, input.length);
					outputStream.flush();
				}
			}

			try (InputStream inputStream = httpURLConnection.getInputStream();
				 InputStreamReader inputStreamReader = new InputStreamReader(inputStream, ContentType.UTF_8);
				 BufferedReader bufferedReader = new BufferedReader(inputStreamReader);)
			{
				StringBuilder response = new StringBuilder();
				String responseLine = null;

				while ((responseLine = bufferedReader.readLine()) != null)
				{
					response.append(responseLine).append(System.lineSeparator());
				}

				log.debug("time took: {}", Time.getTimeDifference(startMiliSeconds));

				return response.toString();
			}
		} catch (Exception exception)
		{
			throw new RuntimeException("Exception occured", exception);
		}
	}

	public static String doHttpRequest(String httpMethod, String urlString)
	{
		return doHttpRequest(httpMethod, urlString, null);
	}

	public static String doGetRequest(String url)
	{
		return doGetRequest(url, null);
	}

	public static String doGetRequest(String url, String payload)
	{
		return doHttpRequest(HttpConstants.GET, url, payload);
	}

	public static String doPostRequest(String url)
	{
		return doPostRequest(url, null);
	}

	public static String doPostRequest(String url, String payload)
	{
		return doHttpRequest(HttpConstants.POST, url, payload);
	}
}
