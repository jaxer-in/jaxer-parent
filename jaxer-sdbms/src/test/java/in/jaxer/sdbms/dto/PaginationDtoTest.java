package in.jaxer.sdbms.dto;

/**
 * @author Shakir
 */
public class PaginationDtoTest
{

	//	@Test
	public void testToString()
	{
		System.out.println("toString");
		PaginationDto instance = new PaginationDto();
		String result = instance.toString();
		System.out.println("PaginationDtoTest.testToString() - " + result);
	}
}
