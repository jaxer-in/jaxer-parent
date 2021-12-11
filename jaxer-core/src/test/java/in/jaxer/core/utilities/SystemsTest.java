
package in.jaxer.core.utilities;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Shakir
 */
public class SystemsTest
{

	public SystemsTest()
	{
	}

	/**
	 * Test of setProperty method, of class Systems.
	 */
	@Test
	public void testSetGetProperty()
	{
		System.out.println("setProperty - getProperty");
		String key = "some.dummy.key";
		String value = "some.dummy.value";
		Systems.setProperty(key, value);

		assertEquals(value, Systems.getProperty(key));
	}

	/**
	 * Test of getOsName method, of class Systems.
	 */
	@Test
	public void testGetOsName()
	{
		System.out.println("getOsName");
		String result = Systems.getOsName();
		System.out.println("result: " + result);
	}

	/**
	 * Test of isMac method, of class Systems.
	 */
	@Test
	public void testIsMac()
	{
		System.out.println("isMac");
		boolean expResult = false;
		boolean result = Systems.isMac();
		assertEquals(expResult, result);
	}

	/**
	 * Test of isWindows method, of class Systems.
	 */
	@Test
	public void testIsWindows()
	{
		System.out.println("isWindows");
		boolean expResult = true;
		boolean result = Systems.isWindows();
		assertEquals(expResult, result);
	}

	/**
	 * Test of isSolaris method, of class Systems.
	 */
	@Test
	public void testIsSolaris()
	{
		System.out.println("isSolaris");
		boolean expResult = false;
		boolean result = Systems.isSolaris();
		assertEquals(expResult, result);
	}

	/**
	 * Test of isUnix method, of class Systems.
	 */
	@Test
	public void testIsUnix()
	{
		System.out.println("isUnix");
		boolean expResult = false;
		boolean result = Systems.isUnix();
		assertEquals(expResult, result);
	}

	/**
	 * Test of getClasspathFromProperty method, of class Systems.
	 */
	@Test
	public void testGetClasspathFromProperty()
	{
		System.out.println("getClasspathFromProperty");
		String result = Systems.getClasspathFromProperty();
		System.out.println("result: " + result);
	}

	/**
	 * Test of getUserHomeDirectory method, of class Systems.
	 */
	@Test
	public void testGetUserHomeDirectory()
	{
		System.out.println("getUserHomeDirectory");
		String expResult = "C:\\Users\\Shakir";
		String result = Systems.getUserHomeDirectory();
		assertEquals(expResult, result);
	}

	/**
	 * Test of getPresentWorkingDirectory method, of class Systems.
	 */
	@Test
	public void testGetPresentWorkingDirectory()
	{
		System.out.println("getPresentWorkingDirectory");
		String result = Systems.getPresentWorkingDirectory();
		System.out.println("result: " + result);
	}

	/**
	 * Test of getTempDirectory method, of class Systems.
	 */
	@Test
	public void testGetTempDirectory()
	{
		System.out.println("getTempDirectory");
		String expResult = "C:\\Users\\Shakir\\AppData\\Local\\Temp\\";
		String result = Systems.getTempDirectory();
		assertEquals(expResult, result);
	}

	/**
	 * Test of printAllProperties method, of class Systems.
	 */
	@Test
	public void testPrintAllProperties()
	{
		System.out.println("printAllProperties");
		Systems.printAllProperties();
	}

}
