package in.jaxer.core.utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JUtilitiesTest
{
	@Nested
	public class MinTest
	{
		@Test
		void whenMinValueIsNegative()
		{
			int result = JUtilities.min(1, 45, 67, 89, 23, 64, 46, -9);
			Assertions.assertEquals(result, -9);
		}

		@Test
		void whenMinValueIsPositive()
		{
			int result = JUtilities.min(100, 45, 67, 89, 23, 64, 46);
			Assertions.assertEquals(result, 23);
		}

		@Test
		void whenMinValueIsZero()
		{
			int result = JUtilities.min(100, 45, 0, 617, 890, 64, 46);
			Assertions.assertEquals(result, 0);
		}
	}

	@Nested
	public class MaxTest
	{
		@Test
		void whenMaxValueIsNegative()
		{
			int result = JUtilities.max(-1, -45, -67, -89, -23, -64, -46, -9);
			Assertions.assertEquals(result, -1);
		}

		@Test
		void whenMaxValueIsPositive()
		{
			int result = JUtilities.max(100, 45, 67, 987, 89, 23, 64, 46);
			Assertions.assertEquals(result, 987);
		}

		@Test
		void whenMaxValueIsZero()
		{
			int result = JUtilities.max(-100, -45, 0, -617, -890, -64, -46);
			Assertions.assertEquals(result, 0);
		}
	}

	@Nested
	public class IsValidUrlTest
	{
		@Test
		void whenUrlsIsInvalid()
		{
			String invalidUrl = "https://some-dummy-url.com";
			Assertions.assertEquals(JUtilities.isValidUrl(invalidUrl), false);
		}

		@Test
		void whenUrlsIsValid()
		{
			String invalidUrl = "https://www.fb.com";
			Assertions.assertEquals(JUtilities.isValidUrl(invalidUrl), true);
		}
	}

	@Nested
	public class ReverseTest
	{
		@Test
		public void withPositiveValues()
		{
			byte[] sourceArray = {1, 5, 4, 8, 9};
			byte[] expectedArray = {9, 8, 4, 5, 1};
			JUtilities.reverse(sourceArray, sourceArray.length);
			Assertions.assertArrayEquals(expectedArray, sourceArray);
		}

		@Test
		public void withNegativeValues()
		{
			byte[] sourceArray = {-1, -5, -4, -8, -9};
			byte[] expectedArray = {-9, -8, -4, -5, -1};
			JUtilities.reverse(sourceArray, sourceArray.length);
			Assertions.assertArrayEquals(expectedArray, sourceArray);
		}
	}
}