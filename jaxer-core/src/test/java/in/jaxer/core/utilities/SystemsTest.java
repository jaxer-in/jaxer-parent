
package in.jaxer.core.utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Shakir
 */
public class SystemsTest
{

	public SystemsTest()
	{
	}

	@Test
	public void testSetGetProperty()
	{
		System.out.println("setProperty - getProperty");
		String key = "some.dummy.key";
		String value = "some.dummy.value";
		Systems.setProperty(key, value);

		Assertions.assertEquals(value, Systems.getProperty(key));
	}

	@Test
	public void testGetOsName()
	{
		System.out.println("getOsName");
		String result = Systems.getOsName();
		System.out.println("result: " + result);
	}

	@Test
	public void testIsMac()
	{
		System.out.println("isMac");
		Assertions.assertEquals(false, Systems.isMac());
	}

	@Test
	public void testIsWindows()
	{
		System.out.println("isWindows");
		Assertions.assertEquals(true, Systems.isWindows());
	}

	@Test
	public void testIsSolaris()
	{
		System.out.println("isSolaris");
		Assertions.assertEquals(false, Systems.isSolaris());
	}

	@Test
	public void testIsUnix()
	{
		System.out.println("isUnix");
		Assertions.assertEquals(false, Systems.isUnix());
	}

	@Test
	public void testGetClasspathFromProperty()
	{
		System.out.println("getClasspathFromProperty");
		String result = Systems.getClasspathFromProperty();
		System.out.println("result: " + result);
	}

	@Test
	public void testGetUserHomeDirectory()
	{
		System.out.println("getUserHomeDirectory");
		String expResult = "C:\\Users\\Shakir";
		String result = Systems.getUserHomeDirectory();
		Assertions.assertEquals(expResult, result);
	}

	@Test
	public void testGetPresentWorkingDirectory()
	{
		System.out.println("getPresentWorkingDirectory");
		String result = Systems.getPresentWorkingDirectory();
		System.out.println("result: " + result);
	}

	@Test
	public void testGetTempDirectory()
	{
		System.out.println("getTempDirectory");
		String expResult = "C:\\Users\\Shakir\\AppData\\Local\\Temp\\";
		String result = Systems.getTempDirectory();
		Assertions.assertEquals(expResult, result);
	}

	@Test
	public void testPrintAllProperties()
	{
		System.out.println("printAllProperties");
		Systems.printAllProperties();
	}

}
