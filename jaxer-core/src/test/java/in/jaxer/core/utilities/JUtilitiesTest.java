package in.jaxer.core.utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

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
	public class ReverseArrayTest
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
	public class IsHtmlStringTest
	{
		@Test
		void whenHtmlStringSingleLine()
		{
			String html = "<h1>Hello world</h1>";
			Assertions.assertTrue(JUtilities.isHtmlString(html));
		}

		@Test
		void whenHtmlStringMultiline()
		{
			String html = "" +
					"<!doctype>" +
					"\n" +
					"<body>" +
					"\n" +
					"<h1>Hello world</h1>" +
					"\n" +
					"<body>" +
					"";
			Assertions.assertTrue(JUtilities.isHtmlString(html));
		}

		@Test
		void whenPlainString()
		{
			String plain = "Hello world";
			Assertions.assertEquals(false, JUtilities.isHtmlString(plain));
		}
	}

	@Nested
	public class GetExtensionTest
	{
		@Test
		void whenImageFile()
		{
			Assertions.assertEquals("jpg", JUtilities.getExtension("c:\\users\\john doe\\images\\IMG_00987.jpg"));
		}

		@Test
		void whenBinanyFile()
		{
			Assertions.assertEquals("exe", JUtilities.getExtension("c:\\users\\john doe\\softwares\\netbeatns.v13.001.patch.exe"));
		}

		@Test
		void whenNoExtensionFile()
		{
			Assertions.assertNull(JUtilities.getExtension("c:\\users\\john doe\\etc\\simple-text"));
		}
	}

	@Nested
	public class GetExtensionWithDotTest
	{
		@Test
		void whenImageFile()
		{
			Assertions.assertEquals(".jpg", JUtilities.getExtensionWithDot("c:\\users\\john doe\\images\\IMG_00987.jpg"));
		}

		@Test
		void whenBinanyFile()
		{
			Assertions.assertEquals(".exe", JUtilities.getExtensionWithDot("c:\\users\\john doe\\softwares\\netbeatns.v13.001.patch.exe"));
		}

		@Test
		void whenNoExtensionFile()
		{
			Assertions.assertNull(JUtilities.getExtensionWithDot("c:\\users\\john doe\\etc\\simple-text"));
		}
	}

	@Nested
	public class GetImageDimensionTest
	{
		@Test
		void whenArgIsString()
		{
			URL url = JUtilities.class.getResource("/statics/images/lorem-picsum-536x354.jpg");

			Dimension dimension = JUtilities.getImageDimension(url.getPath());
			Assertions.assertEquals(dimension.width, 536);
			Assertions.assertEquals(dimension.height, 354);
		}

		@Test
		void whenArgIsInvalidString()
		{
			URL url = JUtilities.class.getResource("/statics/images/file-does-not-exist.jpg");

			Assertions.assertThrows(NullPointerException.class, () -> JUtilities.getImageDimension(url.getPath()));
		}

		@Test
		void whenArgIsFile()
		{
			URL url = JUtilities.class.getResource("/statics/images/lorem-picsum-536x354.jpg");

			Dimension dimension = JUtilities.getImageDimension(new File(url.getPath()));
			Assertions.assertEquals(dimension.width, 536);
			Assertions.assertEquals(dimension.height, 354);
		}

		@Test
		void whenArgIsInvalidFile()
		{
			URL url = JUtilities.class.getResource("/statics/images/file-does-not-exist.jpg");

			Assertions.assertThrows(NullPointerException.class, () -> JUtilities.getImageDimension(new File(url.getPath())));
		}

		@Test
		void whenArgIsInputStream()
		{
			URL url = JUtilities.class.getResource("/statics/images/lorem-picsum-536x354.jpg");

			try
			{
				Dimension dimension = JUtilities.getImageDimension(url.openStream());
				Assertions.assertEquals(dimension.width, 536);
				Assertions.assertEquals(dimension.height, 354);
			} catch (IOException e)
			{
				throw new RuntimeException(e);
			}

		}

		@Test
		void whenArgIsInvalidInputStream()
		{
			URL url = JUtilities.class.getResource("/statics/images/file-does-not-exist.jpg");

			Assertions.assertThrows(NullPointerException.class, () ->
			{
				Dimension dimension = JUtilities.getImageDimension(url.openStream());
				Assertions.assertEquals(dimension.width, 536);
				Assertions.assertEquals(dimension.height, 354);
			});
		}

		@Test
		void whenArgIsBufferedImage()
		{
			URL url = JUtilities.class.getResource("/statics/images/lorem-picsum-536x354.jpg");

			try
			{
				Dimension dimension = JUtilities.getImageDimension(ImageIO.read(url));
				Assertions.assertEquals(dimension.width, 536);
				Assertions.assertEquals(dimension.height, 354);
			} catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}

		@Test
		void whenArgIsInvalidBufferedImage()
		{
			URL url = JUtilities.class.getResource("/statics/images/file-does-not-exist.jpg");

			Assertions.assertThrows(IllegalArgumentException.class, () -> JUtilities.getImageDimension(ImageIO.read(url)));
		}
	}

	@Nested
	public class GetScreenDimensionTest
	{
		@Test
		void shouldBeGreaterThanZero()
		{
			Dimension dimension = JUtilities.getScreenDimension();
			System.out.printf("dimension: %s\n", dimension);

			Assertions.assertTrue(dimension.width > 0);
			Assertions.assertTrue(dimension.height > 0);
		}
	}

	@Nested
	public class IsUrlTest
	{
		@Test
		void whenInvalidUrl()
		{
			Assertions.assertEquals(false, JUtilities.isUrl("hello-world.com"));
		}

		@Test
		void whenValidUrl()
		{
			Assertions.assertEquals(true, JUtilities.isUrl("https://www.some-website.com"));
		}
	}
}