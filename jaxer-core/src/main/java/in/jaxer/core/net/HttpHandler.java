
package in.jaxer.core.net;

import in.jaxer.core.constants.ContentType;
import in.jaxer.core.constants.HttpConstants;
import in.jaxer.core.utilities.Time;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Shakir Ansari
 */
public class HttpHandler
{

	public static String doHttpRequest(String httpMethod, String urlString, String payload)
	{
		System.out.println("HttpHandler.doHttpRequest() - httpMethod: [" + httpMethod + "]");
		System.out.println("HttpHandler.doHttpRequest() - urlString: [" + urlString + "]");
		System.out.println("HttpHandler.doHttpRequest() - payload: [" + payload + "]");

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

				System.out.println("HttpHandler.doHttpRequest() - time took: [" + Time.timeDifference(startMiliSeconds) + "]");

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
