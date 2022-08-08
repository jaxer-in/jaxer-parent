package in.jaxer.core.encoders;

import in.jaxer.core.exceptions.ValidationException;
import in.jaxer.core.interfaces.Encoder;
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
 * @author Shakir
 */
@TestInstance(Lifecycle.PER_CLASS)
public class DesEncoderTest
{
	private Encoder encoder;

	public DesEncoderTest()
	{
	}

	@BeforeAll
	public void setUpClass()
	{
		encoder = new DesEncoder();
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
		Assertions.assertThrows(NullPointerException.class, () -> encoder.encode(""));
		Assertions.assertThrows(NullPointerException.class, () -> encoder.decode(""));
	}

	@Test
	public void whenMessageIsNull()
	{
		System.out.println("whenMessageIsNull");
		Assertions.assertThrows(NullPointerException.class, () -> encoder.encode(null));
		Assertions.assertThrows(NullPointerException.class, () -> encoder.decode(null));
	}

	@Test
	public void encodedMesssageShouldBeEqualsToDecodedMessage()
	{
		System.out.println("encodedMesssageShouldBeEqualsToDecodedMessage");

		String message = "HelloWorld!!";
		System.out.println("message: [" + message + "]");

		String encoded = encoder.encode(message);
		System.out.println("encoded: [" + encoded + "]");

		String decoded = encoder.decode(encoded);
		System.out.println("decoded: [" + decoded + "]");

		MatcherAssert.assertThat(message, CoreMatchers.equalTo(decoded));
	}
}
