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
	}

	@Nested
	class ThrowWhenBlank
	{
		@Nested
		class WithString
		{
			@Test
			void whenOk()
			{
				JValidator.throwWhenBlank("HelloWorld");
			}

			@Test
			void onEmpty()
			{
				Assertions.assertThrowsExactly(NullPointerException.class, () -> JValidator.throwWhenBlank(""));
			}

			@Test
			void onNull()
			{
				Assertions.assertThrowsExactly(NullPointerException.class, () -> JValidator.throwWhenBlank((String) null));
			}

			@Test
			void onEmptyWithCustomException()
			{
				Assertions.assertThrowsExactly(JaxerCoreException.class, () -> JValidator.throwWhenBlank("", new JaxerCoreException()));
			}

			@Test
			void onNullWithCustomException()
			{
				Assertions.assertThrowsExactly(JaxerCoreException.class, () -> JValidator.throwWhenBlank((String) null, new JaxerCoreException()));
			}
		}

		@Nested
		class WithCollection
		{
			@Test
			void whenOk()
			{
				JValidator.throwWhenBlank(Collections.singletonList("Hello World"));
			}

			@Test
			void onEmpty()
			{
				Assertions.assertThrowsExactly(NullPointerException.class, () -> JValidator.throwWhenBlank(new ArrayList<>()));
			}

			@Test
			void onNull()
			{
				Assertions.assertThrowsExactly(NullPointerException.class, () -> JValidator.throwWhenBlank((ArrayList<String>) null));
			}

			@Test
			void onEmptyWithCustomException()
			{
				Assertions.assertThrowsExactly(JaxerCoreException.class, () -> JValidator.throwWhenBlank(new ArrayList<>(), new JaxerCoreException()));
			}

			@Test
			void onNullWithCustomException()
			{
				Assertions.assertThrowsExactly(JaxerCoreException.class, () -> JValidator.throwWhenBlank((ArrayList<String>) null, new JaxerCoreException()));
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
				JValidator.throwWhenBlank(map);
			}

			@Test
			void onEmpty()
			{
				Assertions.assertThrowsExactly(NullPointerException.class, () -> JValidator.throwWhenBlank(new HashMap<>()));
			}

			@Test
			void onNull()
			{
				Assertions.assertThrowsExactly(NullPointerException.class, () -> JValidator.throwWhenBlank((Map<String, Object>) null));
			}

			@Test
			void onEmptyWithCustomException()
			{
				Assertions.assertThrowsExactly(JaxerCoreException.class, () -> JValidator.throwWhenBlank(new HashMap<>(), new JaxerCoreException()));
			}

			@Test
			void onNullWithCustomException()
			{
				Assertions.assertThrowsExactly(JaxerCoreException.class, () -> JValidator.throwWhenBlank((Map<String, Object>) null, new JaxerCoreException()));
			}
		}
	}

	@Nested
	class ThrowWhenTrueTest
	{
		@Test
		void throwWhenTrue()
		{
			Assertions.assertThrows(AssertionError.class, () -> JValidator.throwWhenTrue(true));
		}

		@Test
		void throwWhenTrueWithCustomExceptionMessage()
		{
			Assertions.assertThrows(AssertionError.class, () -> JValidator.throwWhenTrue(true, "Custom exception message"));
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
			Assertions.assertThrows(AssertionError.class, () -> JValidator.throwWhenFalse(false));
		}

		@Test
		void throwWhenTrueWithCustomExceptionMessage()
		{
			Assertions.assertThrows(AssertionError.class, () -> JValidator.throwWhenFalse(false, "Custom exception message"));
		}

		@Test
		void throwWhenTrueWithThrowable()
		{
			Assertions.assertThrows(JaxerCoreException.class, () -> JValidator.throwWhenFalse(false, new JaxerCoreException()));
		}
	}
}