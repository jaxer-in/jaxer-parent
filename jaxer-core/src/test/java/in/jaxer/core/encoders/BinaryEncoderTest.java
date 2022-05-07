
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
public class BinaryEncoderTest
{

	private Encoder encoder;

	private final String encodedMessage = "211010003110010121101100311011002110111131000002111011131101111211100103110110021100100";

	public BinaryEncoderTest()
	{
	}

	@BeforeAll
	public void setUpClass()
	{
		encoder = new BinaryEncoder();
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
}