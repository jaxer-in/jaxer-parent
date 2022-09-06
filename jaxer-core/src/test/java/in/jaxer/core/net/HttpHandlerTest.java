package in.jaxer.core.net;

import in.jaxer.core.HttpUtils;
import in.jaxer.core.dtos.TimeDifference;
import in.jaxer.core.utilities.JUtilities;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpHandlerTest
{
	private static final String baseUrl = "https://jsonplaceholder.typicode.com";
	private HttpHandler httpHandler;

	@BeforeEach
	void beforEach()
	{
		httpHandler = new HttpHandler();
		httpHandler.setCallbackListener(new HttpHandler.CallbackListener()
		{
			@Override
			public void onSuccess(int responseCode, String response)
			{
				log.info("httpStatus: {}, response: {}", responseCode, response);
			}

			@Override
			public void onError(int responseCode, Exception exception)
			{
				log.info("httpStatus: {}, exception: {}", responseCode, exception.getMessage());
			}

			@Override
			public void onComplete(int responseCode, TimeDifference timeDifference)
			{
				log.info("httpStatus: {}, {}", responseCode, timeDifference);
			}
		});
	}

	@AfterEach
	void afterEach()
	{
		Thread t = new Thread(httpHandler);
		t.start();
	}

	@AfterAll
	void afterAll()
	{
		//This method will wait for n seconds to complete all threads
		JUtilities.sleep(5 * 1000);
	}

	@Test
	void httpHandlerGetTest()
	{
		httpHandler.setUrlString(baseUrl + "/todos/1");
	}

	@Test
	void httpHandlerPostTest()
	{
		httpHandler.setHttpMethod(HttpUtils.Method.POST);
		httpHandler.setUrlString(baseUrl + "/todos");

		String payload = "{" +
				"\"userId\": 100," +
				"\"id\": 100," +
				"\"title\": \"Created by jaxer\"," +
				"\"completed\": false" +
				"}";
		httpHandler.setPayload(payload);

	}

	@Test
	void httpHandlerPutTest()
	{
		httpHandler.setHttpMethod(HttpUtils.Method.PUT);
		httpHandler.setUrlString(baseUrl + "/todos/1");

		String payload = "{" +
				"\"userId\": 1," +
				"\"id\": 1," +
				"\"title\": \"Edit by jaxer\"," +
				"\"completed\": false" +
				"}";
		httpHandler.setPayload(payload);
	}

	@Test
	void httpHandlerPatchTest()
	{
		httpHandler.setHttpMethod(HttpUtils.Method.PUT);
		httpHandler.setUrlString(baseUrl + "/todos/1");

		String payload = "{\"userId\": 21}";
		httpHandler.setPayload(payload);
	}

	@Test
	void httpHandlerDeleteTest()
	{
		httpHandler.setHttpMethod(HttpUtils.Method.DELETE);
		httpHandler.setUrlString(baseUrl + "/todos/1");
	}
}