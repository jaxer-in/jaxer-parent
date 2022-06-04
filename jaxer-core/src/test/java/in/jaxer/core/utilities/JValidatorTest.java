package in.jaxer.core.utilities;

import in.jaxer.core.exceptions.JaxerCoreException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class JValidatorTest
{
	@Nested
	class RethrowTests
	{
		@Test
		void whenThrowableIsNull()
		{
			Assertions.assertThrowsExactly(NullPointerException.class, () -> JValidator.rethrow(null));
		}

		@Test
		void whenThrowableIsRuntimeException()
		{
			Assertions.assertThrowsExactly(RuntimeException.class, () -> JValidator.rethrow(new RuntimeException()));
		}

		@Test
		void whenThrowableIsInstanceOfRuntimeException()
		{
			Assertions.assertThrowsExactly(NullPointerException.class, () -> JValidator.rethrow(new NullPointerException()));
		}
	}

	@Nested
	class ThrowWhenNull
	{
		@Test
		void whenOk()
		{
			JValidator.throwWhenNull("HelloWorld");
		}

		@Test
		void onNull()
		{
			Assertions.assertThrowsExactly(NullPointerException.class, () -> JValidator.throwWhenNull(null));
		}

		@Test
		void onNullWithCustomException()
		{
			Assertions.assertThrowsExactly(JaxerCoreException.class, () -> JValidator.throwWhenNull(null, new JaxerCoreException()));
		}
	}

	@Nested
	class ThrowWhenNullOrEmpty
	{
		@Nested
		class WithString
		{
			@Test
			void whenOk()
			{
				JValidator.throwWhenNullOrEmpty("HelloWorld");
			}

			@Test
			void onEmpty()
			{
				Assertions.assertThrowsExactly(NullPointerException.class, () -> JValidator.throwWhenNullOrEmpty(""));
			}

			@Test
			void onNull()
			{
				Assertions.assertThrowsExactly(NullPointerException.class, () -> JValidator.throwWhenNullOrEmpty((String) null));
			}

			@Test
			void onEmptyWithCustomException()
			{
				Assertions.assertThrowsExactly(JaxerCoreException.class, () -> JValidator.throwWhenNullOrEmpty("", new JaxerCoreException()));
			}

			@Test
			void onNullWithCustomException()
			{
				Assertions.assertThrowsExactly(JaxerCoreException.class, () -> JValidator.throwWhenNullOrEmpty((String) null, new JaxerCoreException()));
			}
		}

		@Nested
		class WithCollection
		{
			@Test
			void whenOk()
			{
				JValidator.throwWhenNullOrEmpty(Collections.singletonList("Hello World"));
			}

			@Test
			void onEmpty()
			{
				Assertions.assertThrowsExactly(NullPointerException.class, () -> JValidator.throwWhenNullOrEmpty(new ArrayList<>()));
			}

			@Test
			void onNull()
			{
				Assertions.assertThrowsExactly(NullPointerException.class, () -> JValidator.throwWhenNullOrEmpty((ArrayList<String>) null));
			}

			@Test
			void onEmptyWithCustomException()
			{
				Assertions.assertThrowsExactly(JaxerCoreException.class, () -> JValidator.throwWhenNullOrEmpty(new ArrayList<>(), new JaxerCoreException()));
			}

			@Test
			void onNullWithCustomException()
			{
				Assertions.assertThrowsExactly(JaxerCoreException.class, () -> JValidator.throwWhenNullOrEmpty((ArrayList<String>) null, new JaxerCoreException()));
			}
		}

		@Nested
		class WithMap
		{
			@Test
			void whenOk()
			{
				Map<String, String> map = new HashMap<>();

				map.put("hello", "world");
				JValidator.throwWhenNullOrEmpty(map);
			}

			@Test
			void onEmpty()
			{
				Assertions.assertThrowsExactly(NullPointerException.class, () -> JValidator.throwWhenNullOrEmpty(new HashMap<>()));
			}

			@Test
			void onNull()
			{
				Assertions.assertThrowsExactly(NullPointerException.class, () -> JValidator.throwWhenNullOrEmpty((Map<String, Object>) null));
			}

			@Test
			void onEmptyWithCustomException()
			{
				Assertions.assertThrowsExactly(JaxerCoreException.class, () -> JValidator.throwWhenNullOrEmpty(new HashMap<>(), new JaxerCoreException()));
			}

			@Test
			void onNullWithCustomException()
			{
				Assertions.assertThrowsExactly(JaxerCoreException.class, () -> JValidator.throwWhenNullOrEmpty((Map<String, Object>) null, new JaxerCoreException()));
			}
		}
	}

	@Nested
	class ThrowWhenTrueTest
	{
		@Test
		void throwWhenTrue()
		{
			Assertions.assertThrows(NullPointerException.class, () -> JValidator.throwWhenTrue(true));
		}

		@Test
		void throwWhenTrueWithCustomExceptionMessage()
		{
			Assertions.assertThrows(NullPointerException.class, () -> JValidator.throwWhenTrue(true, "Custom exception message"));
		}

		@Test
		void throwWhenTrueWithThrowable()
		{
			Assertions.assertThrows(IllegalArgumentException.class, () -> JValidator.throwWhenTrue(true, new IllegalArgumentException()));
		}
	}

	@Nested
	class ThrowWhenFalseTest
	{
		@Test
		void throwWhenTrue()
		{
			Assertions.assertThrows(NullPointerException.class, () -> JValidator.throwWhenFalse(false));
		}

		@Test
		void throwWhenTrueWithCustomExceptionMessage()
		{
			Assertions.assertThrows(NullPointerException.class, () -> JValidator.throwWhenFalse(false, "Custom exception message"));
		}

		@Test
		void throwWhenTrueWithThrowable()
		{
			Assertions.assertThrows(JaxerCoreException.class, () -> JValidator.throwWhenFalse(false, new JaxerCoreException()));
		}
	}
}