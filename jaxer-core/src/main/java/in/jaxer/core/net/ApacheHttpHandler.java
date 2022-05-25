package in.jaxer.core.net;

import in.jaxer.core.constants.ContentType;
import in.jaxer.core.constants.HttpConstants;
import in.jaxer.core.utilities.JValidator;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author Shakir Ansari
 */
public class ApacheHttpHandler
{
	private final HttpClient httpClient;

	public ApacheHttpHandler(HttpClient httpClient)
	{
		this.httpClient = httpClient;
	}

	public String doGet(String url) throws IOException
	{
		HttpGet httpGet = new HttpGet(url);
		return getResponse(httpClient.execute(httpGet));
	}
	
	public String doPost(String url, List nameValuePairs) throws IOException
	{
		HttpPost httpPost = new HttpPost(url);

		if (JValidator.isNotNullAndNotEmpty(nameValuePairs))
		{
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		}

		HttpResponse httpResponse = httpClient.execute(httpPost);
		return getResponse(httpResponse);
	}

	public String doPost(String url, String jsonString) throws IOException
	{
		HttpPost httpPost = new HttpPost(url);

		if (JValidator.isNotNullAndNotEmpty(jsonString))
		{
			StringEntity stringEntity = new StringEntity(jsonString);
			stringEntity.setContentType(ContentType.APPLICATION_JSON);

			httpPost.setEntity(stringEntity);
			httpPost.setHeader(HttpConstants.Content_Type, ContentType.APPLICATION_JSON);
		}

		HttpResponse httpResponse = httpClient.execute(httpPost);
		return getResponse(httpResponse);
	}

	public String doPut(String url, String jsonString) throws IOException
	{
		HttpPut httpPut = new HttpPut(url);

		if (JValidator.isNotNullAndNotEmpty(jsonString))
		{
			StringEntity stringEntity = new StringEntity(jsonString);
			stringEntity.setContentType(ContentType.APPLICATION_JSON);

			httpPut.setEntity(stringEntity);
			httpPut.setHeader(HttpConstants.Content_Type, ContentType.APPLICATION_JSON);
		}

		HttpResponse httpResponse = httpClient.execute(httpPut);
		return getResponse(httpResponse);
	}

	public String doDelete(String url) throws IOException
	{
		HttpDelete httpDelete = new HttpDelete(url);

//		httpDelete.setHeader(HttpConstants.Content_Type, "application/json");
		HttpResponse httpResponse = httpClient.execute(httpDelete);

		return getResponse(httpResponse);
	}

	public String toString(HttpResponse response) throws IOException
	{
		return EntityUtils.toString(response.getEntity());
	}

	public String getResponse(HttpResponse response) throws IOException
	{
		try (InputStreamReader inputStreamReader = new InputStreamReader(response.getEntity().getContent());
			 BufferedReader rd = new BufferedReader(inputStreamReader))
		{
			String line = null;
			StringBuilder builder = new StringBuilder();

			while ((line = rd.readLine()) != null)
			{
				builder.append(line).append(System.lineSeparator());
			}

			return builder.toString();
		}
	}
}
