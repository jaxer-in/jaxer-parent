package in.jaxer.core.net;

import in.jaxer.core.constants.ContentType;
import in.jaxer.core.constants.HttpConstants;
import in.jaxer.core.dtos.TimeDifference;
import in.jaxer.core.utilities.Time;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author Shakir Ansari
 */
@Log4j2
@Getter
@Setter
public class HttpHandler implements Runnable
{
	@NonNull
	private String urlString;

	private String httpMethod = HttpConstants.GET;

	private String payload;

	private String payloadCharsets = ContentType.UTF_8;
	private String payloadContentType = ContentType.APPLICATION_JSON;
	private String requestContentType = ContentType.APPLICATION_JSON;

	private CallbackListener callbackListener;

	@Override
	public void run()
	{
		log.debug("httpMethod: {}, requestContentType: {}, payloadContentType: {}", httpMethod, requestContentType, payloadContentType);
		log.debug("urlString: {}", urlString);
		log.debug("payload: {}", payload);

		long startMilliSeconds = System.currentTimeMillis();
		int responseCode = 0;

		try
		{
			URL url = new URL(urlString);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

			httpURLConnection.setRequestProperty(HttpConstants.Accept, requestContentType);
			httpURLConnection.setRequestMethod(httpMethod);

			if (payload != null)
			{
				httpURLConnection.setDoOutput(true);
				httpURLConnection.setRequestProperty(HttpConstants.Content_Type, payloadContentType);

				try (OutputStream outputStream = httpURLConnection.getOutputStream())
				{
					byte[] input = payload.getBytes(payloadCharsets);
					outputStream.write(input, 0, input.length);
					outputStream.flush();
				}
			}

			responseCode = httpURLConnection.getResponseCode();
			try (InputStream inputStream = httpURLConnection.getInputStream();
				 InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
				 BufferedReader bufferedReader = new BufferedReader(inputStreamReader))
			{
				StringBuilder response = new StringBuilder();
				String responseLine;
				while ((responseLine = bufferedReader.readLine()) != null)
				{
					response.append(responseLine).append(System.lineSeparator());
				}

				if (callbackListener != null)
				{
					callbackListener.onSuccess(httpURLConnection.getResponseCode(), response.toString());
				}
			}
		} catch (Exception exception)
		{
			if (callbackListener != null)
			{
				callbackListener.onError(responseCode, exception);
			}
		} finally
		{
			if (callbackListener != null)
			{
				callbackListener.onComplete(responseCode, Time.getTimeDifference(startMilliSeconds, System.currentTimeMillis()));
			}
		}
	}

	public interface CallbackListener
	{
		void onSuccess(int responseCode, String response);

		void onError(int responseCode, Exception exception);

		void onComplete(int responseCode, TimeDifference timeDifference);
	}
}
