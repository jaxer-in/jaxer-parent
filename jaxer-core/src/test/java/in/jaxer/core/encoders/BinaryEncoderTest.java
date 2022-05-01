
package in.jaxer.core.encoders;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 *
 * @author Shakir
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BinaryEncoderTest
{

	private Encoder encoder;

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
	public void encodedMessageShouldNotBeNullWhenTextIsEmpty()
	{
		System.out.println("encodedMessageShouldNotBeNullWhenTextIsEmpty");
		String msg = "";
		Assertions.assertNull(encoder.encode(msg));
	}
}
