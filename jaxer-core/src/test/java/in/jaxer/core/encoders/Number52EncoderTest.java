
package in.jaxer.core.encoders;

import in.jaxer.core.exceptions.ValidationException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 *
 * @author Shakir
 */
@TestInstance(Lifecycle.PER_CLASS)
public class Number52EncoderTest
{

	private Encoder encoder;

	private final String encodedMessage = "4K1K8K8K1L2D9L1L4L8K0K";

	public Number52EncoderTest()
	{
	}

	@BeforeAll
	public void setUpClass()
	{
		encoder = new Number52Encoder();
	}

	@AfterAll
	public void tearDownClass()
	{
	}

	@BeforeEach
	public void setUp()
	{
	}

	@AfterEach
	public void tearDown()
	{
	}

	@Test
	public void whenMessageIsEmpty()
	{
		System.out.println("whenMessageIsEmpty");
		Assertions.assertThrows(ValidationException.class, () -> encoder.encode(""));
		Assertions.assertThrows(ValidationException.class, () -> encoder.decode(""));
	}

	@Test
	public void whenMessageIsNull()
	{
		System.out.println("whenMessageIsNull");
		Assertions.assertThrows(ValidationException.class, () -> encoder.encode(null));
		Assertions.assertThrows(ValidationException.class, () -> encoder.decode(null));
	}

	@Test
	public void encodedMesssageShouldBeEqualsTo()
	{
		System.out.println("encodedMesssageShouldBeEqualsTo");

		String response = encoder.encode("hello world");
//		System.out.println("response: [" + response + "]");

		MatcherAssert.assertThat(response, CoreMatchers.equalTo(encodedMessage));
	}

	@Test
	public void decodedMesssageShouldBeEqualsTo()
	{
		System.out.println("encodedMesssageShouldBeEqualsTo");

		String decodedMessage = "hello world";
		String response = encoder.decode(encodedMessage);
//		System.out.println("response: [" + response + "]");

		MatcherAssert.assertThat(response, CoreMatchers.equalTo(decodedMessage));
	}

	@Test
	public void handleIfEncodedMessageIsInvalid()
	{
		System.out.println("handleIfEncodedMessageIsInvalid");

		String message = "hello world";
		String encoded = "(123)";

		Assertions.assertThrows(ValidationException.class, () -> encoder.decode(encoded));

	}
}
