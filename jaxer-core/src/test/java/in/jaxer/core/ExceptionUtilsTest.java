package in.jaxer.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author Shakir
 * @date 08-08-2022
 */
public class ExceptionUtilsTest
{
	@Nested
	class RethrowTests
	{
		@Test
		void whenThrowableIsNull()
		{
			Assertions.assertThrowsExactly(NullPointerException.class, () -> ExceptionUtils.rethrow(null));
		}

		@Test
		void whenThrowableIsRuntimeException()
		{
			Assertions.assertThrowsExactly(RuntimeException.class, () -> ExceptionUtils.rethrow(new RuntimeException()));
		}

		@Test
		void whenThrowableIsInstanceOfRuntimeException()
		{
			Assertions.assertThrowsExactly(NullPointerException.class, () -> ExceptionUtils.rethrow(new NullPointerException()));
		}

		@Test
		void whenThrowableIsInstanceOfAssertionError()
		{
			Assertions.assertThrowsExactly(AssertionError.class, () -> ExceptionUtils.rethrow(new AssertionError()));
		}
	}

}
