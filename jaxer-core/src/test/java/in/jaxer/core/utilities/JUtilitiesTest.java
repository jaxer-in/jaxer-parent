package in.jaxer.core.utilities;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Log4j2
class JUtilitiesTest
{
	@Nested
	public class MinTest
	{
		@Test
		void whenMinValueIsNegative()
		{
			int result = JUtilities.min(1, 45, 67, 89, 23, 64, 46, -9);
			int expected = -9;

			log.info("result: {}, expected: {}", result, expected);
			Assertions.assertEquals(expected, result);
		}

		@Test
		void whenMinValueIsPositive()
		{
			int result = JUtilities.min(100, 45, 67, 89, 23, 64, 46);
			int expected = 23;

			log.info("result: {}, expected: {}", result, expected);
			Assertions.assertEquals(expected, result);
		}

		@Test
		void whenMinValueIsZero()
		{
			int result = JUtilities.min(100, 45, 0, 617, 890, 64, 46);
			int expected = 0;

			log.info("result: {}, expected: {}", result, expected);
			Assertions.assertEquals(expected, result);
		}
	}

	@Nested
	public class MaxTest
	{
		@Test
		void whenMaxValueIsNegative()
		{
			int result = JUtilities.max(-1, -45, -67, -89, -23, -64, -46, -9);
			int expected = -1;

			log.info("result: {}, expected: {}", result, expected);
			Assertions.assertEquals(expected, result);
		}

		@Test
		void whenMaxValueIsPositive()
		{
			int result = JUtilities.max(100, 45, 67, 987, 89, 23, 64, 46);
			int expected = 987;

			log.info("result: {}, expected: {}", result, expected);
			Assertions.assertEquals(expected, result);
		}

		@Test
		void whenMaxValueIsZero()
		{
			int result = JUtilities.max(-100, -45, 0, -617, -890, -64, -46);
			int expected = 0;

			log.info("result: {}, expected: {}", result, expected);
			Assertions.assertEquals(expected, result);
		}
	}

	@Nested
	public class ReverseArrayTest
	{
		@Test
		public void withPositiveValues()
		{
			byte[] sourceArray = {1, 5, 4, 8, 9};
			byte[] expectedArray = {9, 8, 4, 5, 1};

			log.info("sourceArray: {}, expectedArray: {}", sourceArray, expectedArray);
			JUtilities.reverse(sourceArray, sourceArray.length);
			log.info("sourceArray: {}, expectedArray: {}", sourceArray, expectedArray);

			Assertions.assertArrayEquals(expectedArray, sourceArray);
		}

		@Test
		public void withNegativeValues()
		{
			byte[] sourceArray = {-1, -5, -4, -8, -9};
			byte[] expectedArray = {-9, -8, -4, -5, -1};

			log.info("sourceArray: {}, expectedArray: {}", sourceArray, expectedArray);
			JUtilities.reverse(sourceArray, sourceArray.length);
			log.info("sourceArray: {}, expectedArray: {}", sourceArray, expectedArray);

			Assertions.assertArrayEquals(expectedArray, sourceArray);
		}
	}

	@Nested
	public class IsValidUrlTest
	{
		@Test
		void whenUrlsIsInvalid()
		{
			String invalidUrl = "https://some-dummy-url.com";
			log.info("invalidUrl: {}", invalidUrl);

			Assertions.assertFalse(JUtilities.isValidUrl(invalidUrl));
		}

		@Test
		void whenUrlsIsValid()
		{
			String validUrl = "https://www.fb.com";
			log.info("validUrl: {}", validUrl);

			Assertions.assertTrue(JUtilities.isValidUrl(validUrl));
		}

		@Test
		void whenUrlsIsNull()
		{
			String invalidUrl = null;
			log.info("invalidUrl: {}", invalidUrl);

			Assertions.assertFalse(JUtilities.isValidUrl(invalidUrl));
		}
	}

	@Nested
	public class IsHtmlStringTest
	{
		@Test
		void whenHtmlStringSingleLine()
		{
			String singleLineHtml = "<h1>Hello world</h1>";
			log.info("singleLineHtml: {}", singleLineHtml);

			Assertions.assertTrue(JUtilities.isHtmlString(singleLineHtml));
		}

		@Test
		void whenHtmlTagPresentInNetweenSingleLine()
		{
			String singleLineHtml = "Hello <hr/> world<";
			log.info("singleLineHtml: {}", singleLineHtml);

			Assertions.assertTrue(JUtilities.isHtmlString(singleLineHtml));
		}

		@Test
		void whenHtmlStringMultiline()
		{
			String multiLineHtml = "" +
					"<!doc type>" +
					"\n" +
					"<body>" +
					"\n" +
					"<h1>Hello world</h1>" +
					"\n" +
					"<body>" +
					"";
			log.info("multiLineHtml: {}", multiLineHtml);

			Assertions.assertTrue(JUtilities.isHtmlString(multiLineHtml));
		}

		@Test
		void whenPlainString()
		{
			String plainText = "Hello world";
			log.info("plainText: {}", plainText);

			Assertions.assertFalse(JUtilities.isHtmlString(plainText));
		}
	}

	@Nested
	public class GetExtensionTest
	{
		@Test
		void whenImageFile()
		{
			String fileName = "c:\\users\\john doe\\images\\IMG_00987.jpg";
			String expectedExtension = "jpg";
			String result = JUtilities.getExtension(fileName);

			log.info("fileName: {}, expectedExtension: {}, result: {}", fileName, expectedExtension, result);
			Assertions.assertEquals(expectedExtension, result);
		}

		@Test
		void whenBinaryFile()
		{
			String fileName = "c:\\users\\john doe\\softwares\\NetBeans.v13.001.patch.exe";
			String expectedExtension = "exe";
			String result = JUtilities.getExtension(fileName);

			log.info("fileName: {}, expectedExtension: {}, result: {}", fileName, expectedExtension, result);
			Assertions.assertEquals(expectedExtension, result);
		}

		@Test
		void whenNoExtensionFile()
		{
			String fileName = "c:\\users\\john doe\\etc\\simple-text";
			String expectedExtension = null;
			String result = JUtilities.getExtension(fileName);

			log.info("fileName: {}, expectedExtension: {}, result: {}", fileName, expectedExtension, result);
			Assertions.assertEquals(expectedExtension, result);
		}
	}

	@Nested
	public class GetExtensionWithDotTest
	{
		@Test
		void whenImageFile()
		{
			String fileName = "c:\\users\\john doe\\images\\IMG_00987.jpg";
			String expectedExtension = ".jpg";
			String result = JUtilities.getExtensionWithDot(fileName);

			log.info("fileName: {}, expectedExtension: {}, result: {}", fileName, expectedExtension, result);
			Assertions.assertEquals(expectedExtension, result);
		}

		@Test
		void whenBinaryFile()
		{
			String fileName = "c:\\users\\john doe\\softwares\\NetBeans.v13.001.patch.exe";
			String expectedExtension = ".exe";
			String result = JUtilities.getExtensionWithDot(fileName);

			log.info("fileName: {}, expectedExtension: {}, result: {}", fileName, expectedExtension, result);
			Assertions.assertEquals(expectedExtension, result);
		}

		@Test
		void whenNoExtensionFile()
		{
			String fileName = "c:\\users\\john doe\\etc\\simple-text";
			String expectedExtension = null;
			String result = JUtilities.getExtensionWithDot(fileName);

			log.info("fileName: {}, expectedExtension: {}, result: {}", fileName, expectedExtension, result);
			Assertions.assertEquals(expectedExtension, result);
		}
	}

	@Nested
	public class GetImageDimensionTest
	{
		@Test
		void whenArgIsString()
		{
			URL url = JUtilities.class.getResource("/statics/images/lorem-picsum-536x354.jpg");

			Dimension expected = new Dimension(536, 354);
			Dimension result = JUtilities.getImageDimension(url.getPath());

			log.info("expected: {}, result: {}", expected, result);

			Assertions.assertEquals(expected, result);
		}

		@Test
		void whenArgIsInvalidString()
		{
			URL url = JUtilities.class.getResource("/statics/images/file-does-not-exist.jpg");

			log.info("url: {}", url);

			Assertions.assertThrows(NullPointerException.class, () -> JUtilities.getImageDimension(url.getPath()));
		}

		@Test
		void whenArgIsFile()
		{
			URL url = JUtilities.class.getResource("/statics/images/lorem-picsum-536x354.jpg");

			Dimension expected = new Dimension(536, 354);
			Dimension result = JUtilities.getImageDimension(new File(url.getPath()));

			log.info("expected: {}, result: {}", expected, result);

			Assertions.assertEquals(expected, result);
		}

		@Test
		void whenArgIsInvalidFile()
		{
			URL url = JUtilities.class.getResource("/statics/images/file-does-not-exist.jpg");

			log.info("url: {}", url);

			Assertions.assertThrows(NullPointerException.class, () -> JUtilities.getImageDimension(new File(url.getPath())));
		}

		@Test
		void whenArgIsInputStream() throws IOException
		{
			URL url = JUtilities.class.getResource("/statics/images/lorem-picsum-536x354.jpg");

			Dimension result = JUtilities.getImageDimension(url.openStream());
			Dimension expected = new Dimension(536, 354);
			log.info("expected: {}, result: {}", expected, result);

			Assertions.assertEquals(expected, result);
		}

		@Test
		void whenArgIsInvalidInputStream()
		{
			URL url = JUtilities.class.getResource("/statics/images/file-does-not-exist.jpg");
			log.info("url: {}", url);

			Assertions.assertThrows(NullPointerException.class, () -> JUtilities.getImageDimension(url.openStream()));
		}

		@Test
		void whenArgIsBufferedImage()
		{
			URL url = JUtilities.class.getResource("/statics/images/lorem-picsum-536x354.jpg");

			try
			{
				Dimension result = JUtilities.getImageDimension(ImageIO.read(url));
				Dimension expected = new Dimension(536, 354);
				log.info("expected: {}, result: {}", expected, result);

				Assertions.assertEquals(expected, result);
			} catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}

		@Test
		void whenArgIsInvalidBufferedImage()
		{
			URL url = JUtilities.class.getResource("/statics/images/file-does-not-exist.jpg");
			log.info("url: {}", url);

			Assertions.assertThrows(IllegalArgumentException.class, () -> JUtilities.getImageDimension(ImageIO.read(url)));
		}
	}

	@Nested
	public class GetScreenDimensionTest
	{
		@Test
		@EnabledOnOs(OS.WINDOWS)
		void shouldBeGreaterThanZero()
		{
			Dimension result = JUtilities.getScreenDimension();
			log.info("result: {}", result);

			Assertions.assertTrue(result.width > 0);
			Assertions.assertTrue(result.height > 0);
		}
	}

	@Nested
	public class IsUrlTest
	{
		@Test
		void whenInvalidUrl()
		{
			String url = "hello-world.com";
			boolean result = JUtilities.isUrl(url);

			log.info("result: {}, url: {}", result, url);

			Assertions.assertFalse(result);
		}

		@Test
		void whenValidUrl()
		{
			String url = "https://www.some-website.com";
			boolean result = JUtilities.isUrl(url);

			log.info("result: {}, url: {}", result, url);

			Assertions.assertTrue(result);
		}
	}

	@Nested
	class parseTest
	{
		@Nested
		class ParseByteTests
		{
			@Test
			@DisplayName("When number is positive")
			void whenNumberIsPositive()
			{
				byte expected = 10;
				byte result = JUtilities.parseByte("10", expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}

			@Test
			@DisplayName("When number is negative")
			void whenNumberIsNegative()
			{
				byte expected = -9;
				byte result = JUtilities.parseByte("-9", expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}

			@Test
			@DisplayName("When number is zero")
			void whenNumberIsZero()
			{
				byte expected = 0;
				byte result = JUtilities.parseByte("0", expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}

			@Test
			@DisplayName("When number is invalid String")
			void whenNumberIsInvalidString()
			{
				byte expected = 78;
				byte result = JUtilities.parseByte("hello-world", expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}

			@Test
			@DisplayName("When number is empty string")
			void whenNumberIsEmptyString()
			{
				byte expected = 9;
				byte result = JUtilities.parseByte("", expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}

			@Test
			@DisplayName("When number is null string")
			void whenNumberIsNullString()
			{
				byte expected = 65;
				byte result = JUtilities.parseByte(null, expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}
		}

		@Nested
		class ParseShortTests
		{
			@Test
			@DisplayName("When number is positive")
			void whenNumberIsPositive()
			{
				short expected = 10001;
				short result = JUtilities.parseShort("10001", expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}

			@Test
			@DisplayName("When number is negative")
			void whenNumberIsNegative()
			{
				short expected = -9876;
				short result = JUtilities.parseShort("-9876", expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}

			@Test
			@DisplayName("When number is zero")
			void whenNumberIsZero()
			{
				short expected = 0;
				short result = JUtilities.parseShort("0", expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}

			@Test
			@DisplayName("When number is invalid String")
			void whenNumberIsInvalidString()
			{
				short expected = 7809;
				short result = JUtilities.parseShort("hello-world", expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}

			@Test
			@DisplayName("When number is empty string")
			void whenNumberIsEmptyString()
			{
				short expected = 90;
				short result = JUtilities.parseShort("", expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}

			@Test
			@DisplayName("When number is null string")
			void whenNumberIsNullString()
			{
				short expected = 650;
				short result = JUtilities.parseShort(null, expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}
		}

		@Nested
		class ParseIntTests
		{
			@Test
			@DisplayName("When number is positive")
			void whenNumberIsPositive()
			{
				int expected = 1099001;
				int result = JUtilities.parseInt("1099001", expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}

			@Test
			@DisplayName("When number is negative")
			void whenNumberIsNegative()
			{
				int expected = -980076;
				int result = JUtilities.parseInt("-980076", expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}

			@Test
			@DisplayName("When number is zero")
			void whenNumberIsZero()
			{
				int expected = 0;
				int result = JUtilities.parseInt("0", expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}

			@Test
			@DisplayName("When number is invalid String")
			void whenNumberIsInvalidString()
			{
				int expected = 78999909;
				int result = JUtilities.parseInt("hello-world", expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}

			@Test
			@DisplayName("When number is empty string")
			void whenNumberIsEmptyString()
			{
				int expected = 9948750;
				int result = JUtilities.parseInt("", expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}

			@Test
			@DisplayName("When number is null string")
			void whenNumberIsNullString()
			{
				int expected = 650;
				int result = JUtilities.parseInt(null, expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}
		}

		@Nested
		class ParseLongTests
		{
			@Test
			@DisplayName("When number is positive")
			void whenNumberIsPositive()
			{
				long expected = 1099001L;
				long result = JUtilities.parseLong("1099001", expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}

			@Test
			@DisplayName("When number is negative")
			void whenNumberIsNegative()
			{
				long expected = -980076L;
				long result = JUtilities.parseLong("-980076", expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}

			@Test
			@DisplayName("When number is zero")
			void whenNumberIsZero()
			{
				long expected = 0L;
				long result = JUtilities.parseLong("0", expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}

			@Test
			@DisplayName("When number is invalid String")
			void whenNumberIsInvalidString()
			{
				long expected = 78999909L;
				long result = JUtilities.parseLong("hello-world", expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}

			@Test
			@DisplayName("When number is empty string")
			void whenNumberIsEmptyString()
			{
				long expected = 9948750L;
				long result = JUtilities.parseLong("", expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}

			@Test
			@DisplayName("When number is null string")
			void whenNumberIsNullString()
			{
				long expected = 650L;
				long result = JUtilities.parseLong(null, expected);
				log.info("result: {}, expected: {}", result, expected);

				Assertions.assertEquals(expected, result);
			}
		}

		@Nested
		class ParseFloatTests
		{
			@Nested
			class IntegerNumber
			{
				@Test
				@DisplayName("When string is small whole number")
				void whenStringIsSmallWholeNumber()
				{
					float expected = 78F;
					float result = JUtilities.parseFloat("78", expected);
					float delta = 0.001F;
					log.info("result: {}, expected: {}, delta: {}", result, expected, delta);

					Assertions.assertEquals(result, expected, delta);
				}

				@Test
				@DisplayName("When string is small negative whole number")
				void whenStringIsSmallNegativeWholeNumber()
				{
					float expected = -9758F;
					float result = JUtilities.parseFloat("-9758", expected);
					float delta = 0.001F;
					log.info("result: {}, expected: {}, delta: {}", result, expected, delta);

					Assertions.assertEquals(result, expected, delta);
				}

				@Test
				@DisplayName("When string is large whole number")
				void whenStringIsLargeWholeNumber()
				{
					float expected = 98765432109876543210F;
					float result = JUtilities.parseFloat("98765432109876543210", expected);
					float delta = 0.001F;
					log.info("result: {}, expected: {}, delta: {}", result, expected, delta);

					Assertions.assertEquals(result, expected, delta);
				}

				@Test
				@DisplayName("When string is large negative whole number")
				void whenStringIsLargeNegativeWholeNumber()
				{
					float expected = -98765432109876543210F;
					float result = JUtilities.parseFloat("-98765432109876543210", expected);
					float delta = 0.001F;
					log.info("result: {}, expected: {}, delta: {}", result, expected, delta);

					Assertions.assertEquals(result, expected, delta);
				}
			}

			@Nested
			class FloatNumber
			{
				@Test
				@DisplayName("When string is small float number")
				void whenStringIsSmallFloatNumber()
				{
					float expected = 10.1F;
					float result = JUtilities.parseFloat("10.1", expected);
					float delta = 0.001F;
					log.info("result: {}, expected: {}, delta: {}", result, expected, delta);

					Assertions.assertEquals(result, expected, delta);
				}

				@Test
				@DisplayName("When string is small negative float number")
				void whenStringIsSmallNegativeFloatNumber()
				{
					float expected = -0.24F;
					float result = JUtilities.parseFloat("-0.24", expected);
					float delta = 0.001F;
					log.info("result: {}, expected: {}, delta: {}", result, expected, delta);

					Assertions.assertEquals(result, expected, delta);
				}

				@Test
				@DisplayName("When string is large float number")
				void whenStringIsLargeFloatNumber()
				{
					float expected = 98765432109876543210.98765432109876543210F;
					float result = JUtilities.parseFloat("98765432109876543210.98765432109876543210", expected);
					float delta = 0.001F;
					log.info("result: {}, expected: {}, delta: {}", result, expected, delta);

					Assertions.assertEquals(result, expected, delta);
				}

				@Test
				@DisplayName("When string is large negative float number")
				void whenStringIsLargeNegativeFloatNumber()
				{
					float expected = 98765432109876543210.98765432109876543210F;
					float result = JUtilities.parseFloat("98765432109876543210.98765432109876543210", expected);
					float delta = 0.001F;
					log.info("result: {}, expected: {}, delta: {}", result, expected, delta);

					Assertions.assertEquals(result, expected, delta);
				}
			}

			@Test
			@DisplayName("When String is Empty")
			void parseDoubleTestWhenStringIsEmpty()
			{
				float expected = 10.89F;
				float result = JUtilities.parseFloat("", expected);
				float delta = 0.001F;
				log.info("result: {}, expected: {}, delta: {}", result, expected, delta);

				Assertions.assertEquals(result, expected, delta);
			}

			@Test
			@DisplayName("When String is Null")
			void parseDoubleTestWhenStringIsNUll()
			{
				float expected = 75.899F;
				float result = JUtilities.parseFloat(null, expected);
				float delta = 0.001F;
				log.info("result: {}, expected: {}, delta: {}", result, expected, delta);

				Assertions.assertEquals(result, expected, delta);
			}
		}

		@Nested
		class ParseDoubleTests
		{
			@Nested
			class IntegerNumber
			{
				@Test
				@DisplayName("When string is small whole number")
				void whenStringIsSmallWholeNumber()
				{
					double expected = 78D;
					double result = JUtilities.parseDouble("78", expected);
					double delta = 0.001D;
					log.info("result: {}, expected: {}, delta: {}", result, expected, delta);

					Assertions.assertEquals(result, expected, delta);
				}

				@Test
				@DisplayName("When string is small negative whole number")
				void whenStringIsSmallNegativeWholeNumber()
				{
					double expected = -9758D;
					double result = JUtilities.parseDouble("-9758", expected);
					double delta = 0.001D;
					log.info("result: {}, expected: {}, delta: {}", result, expected, delta);

					Assertions.assertEquals(result, expected, delta);
				}

				@Test
				@DisplayName("When string is large whole number")
				void whenStringIsLargeWholeNumber()
				{
					double expected = 98765432109876543210D;
					double result = JUtilities.parseDouble("98765432109876543210", expected);
					double delta = 0.001D;
					log.info("result: {}, expected: {}, delta: {}", result, expected, delta);

					Assertions.assertEquals(result, expected, delta);
				}

				@Test
				@DisplayName("When string is large negative whole number")
				void whenStringIsLargeNegativeWholeNumber()
				{
					double expected = -98765432109876543210D;
					double result = JUtilities.parseDouble("-98765432109876543210", expected);
					double delta = 0.001D;
					log.info("result: {}, expected: {}, delta: {}", result, expected, delta);

					Assertions.assertEquals(result, expected, delta);
				}
			}

			@Nested
			class DoubleNumber
			{
				@Test
				@DisplayName("When string is small double number")
				void whenStringIsSmallDoubleNumber()
				{
					double expected = 10.1D;
					double result = JUtilities.parseDouble("10.1", expected);
					double delta = 0.001D;
					log.info("result: {}, expected: {}, delta: {}", result, expected, delta);

					Assertions.assertEquals(result, expected, delta);
				}

				@Test
				@DisplayName("When string is small negative double number")
				void whenStringIsSmallNegativeDoubleNumber()
				{
					double expected = -0.24D;
					double result = JUtilities.parseDouble("-0.24", expected);
					double delta = 0.001D;
					log.info("result: {}, expected: {}, delta: {}", result, expected, delta);

					Assertions.assertEquals(result, expected, delta);
				}

				@Test
				@DisplayName("When string is large double number")
				void whenStringIsLargeDoubleNumber()
				{
					double expected = 98765432109876543210.98765432109876543210D;
					double result = JUtilities.parseDouble("98765432109876543210.98765432109876543210", expected);
					double delta = 0.001D;
					log.info("result: {}, expected: {}, delta: {}", result, expected, delta);

					Assertions.assertEquals(result, expected, delta);
				}

				@Test
				@DisplayName("When string is large negative double number")
				void whenStringIsLargeNegativeDoubleNumber()
				{
					double expected = 98765432109876543210.98765432109876543210D;
					double result = JUtilities.parseDouble("98765432109876543210.98765432109876543210", expected);
					double delta = 0.001D;
					log.info("result: {}, expected: {}, delta: {}", result, expected, delta);

					Assertions.assertEquals(result, expected, delta);
				}
			}

			@Test
			@DisplayName("When String is Empty")
			void parseDoubleTestWhenStringIsEmpty()
			{
				double expected = 10.89D;
				double result = JUtilities.parseDouble("", expected);
				double delta = 0.001D;
				log.info("result: {}, expected: {}, delta: {}", result, expected, delta);

				Assertions.assertEquals(result, expected, delta);
			}

			@Test
			@DisplayName("When String is Null")
			void parseDoubleTestWhenStringIsNUll()
			{
				double expected = 75.899D;
				double result = JUtilities.parseDouble(null, expected);
				double delta = 0.001D;
				log.info("result: {}, expected: {}, delta: {}", result, expected, delta);

				Assertions.assertEquals(result, expected, delta);
			}
		}
	}
}