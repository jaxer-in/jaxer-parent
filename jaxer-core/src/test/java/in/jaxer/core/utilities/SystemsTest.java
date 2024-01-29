package in.jaxer.core.utilities;

import lombok.extern.log4j.Log4j2;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

/**
 * @author Shakir
 */
@Log4j2
public class SystemsTest
{
	@Test
	public void testSetGetProperty()
	{
		String key = "some.dummy.key";
		String value = "some.dummy.value";

		log.info("key: {}, value: {}", key, value);
		Systems.setProperty(key, value);

		Assertions.assertEquals(value, Systems.getProperty(key));

		// clearing key
		Systems.clearProperty(key);
		Assertions.assertNull(Systems.getProperty(key));
	}

	@Test
	@EnabledOnOs(OS.WINDOWS)
	public void testGetOsName()
	{
		String result = Systems.getOsName();
		String expected = "windows";

		log.info("result: {}, expected: {}", result, expected);
		MatcherAssert.assertThat(result.toLowerCase(), StringContains.containsString(expected));
	}

	@Test
	@EnabledOnOs(OS.MAC)
	public void testIsMac()
	{
		boolean result = Systems.isMac();
		log.info("result: {}", result);

		Assertions.assertTrue(result);
	}

	@Test
	@EnabledOnOs(OS.WINDOWS)
	public void testIsWindows()
	{
		boolean result = Systems.isWindows();
		log.info("result: {}", result);

		Assertions.assertTrue(result);
	}

	@Test
	@EnabledOnOs(OS.SOLARIS)
	public void testIsSolaris()
	{
		boolean result = Systems.isSolaris();
		log.info("result: {}", result);

		Assertions.assertTrue(result);
	}

	@Test
	@EnabledOnOs(OS.AIX)
	public void testIsUnix()
	{
		boolean result = Systems.isUnix();
		log.info("result: {}", result);

		Assertions.assertTrue(result);
	}

	@Test
	public void testGetClasspathFromProperty()
	{
		String result = Systems.getClasspathFromProperty();
		log.info("result: {}", result);

		Assertions.assertNotNull(result);
	}

	@Test
	public void testGetUserHomeDirectory()
	{
		String result = Systems.getUserHomeDirectory();
		log.info("result: {}", result);

		Assertions.assertNotNull(result);
	}

	@Test
	public void testGetPresentWorkingDirectory()
	{
		String result = Systems.getPresentWorkingDirectory();
		log.info("result: {}", result);

		Assertions.assertNotNull(result);
	}

	@Test
	@EnabledOnOs(OS.WINDOWS)
	public void testGetTempDirectory()
	{
		String result = Systems.getTempDirectory();
		String expected = "C:\\Users\\Shakir\\AppData\\Local\\Temp\\";
		log.info("result: {}, expected: {}", result, expected);

		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testPrintAllProperties()
	{
		Systems.printAllProperties();
	}
}
