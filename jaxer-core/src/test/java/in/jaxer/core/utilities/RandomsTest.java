package in.jaxer.core.utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class RandomsTest
{
	@Order(1)
	@RepeatedTest(value = 5)
	void getRandomInt()
	{
		int val = Randoms.getRandomInt();
		System.out.println("val: [" + val + "]");

		Assertions.assertTrue(val >= Integer.MIN_VALUE);
		Assertions.assertTrue(val <= Integer.MAX_VALUE);
	}

	@RepeatedTest(value = 5)
	void getRandomIntInRange()
	{
		int val = Randoms.getRandomInt(10, 100);
		System.out.println("val: [" + val + "]");

		Assertions.assertTrue(val >= 10);
		Assertions.assertTrue(val <= 100);
	}

	@RepeatedTest(value = 5)
	void getRandomIntLessThan()
	{
		int val = Randoms.getRandomIntLessThan(100);
		System.out.println("val: [" + val + "]");

		Assertions.assertTrue(val < 100);
	}

	@RepeatedTest(value = 5)
	void getRandomIntGreaterThan()
	{
		int val = Randoms.getRandomIntGreaterThan(100);
		System.out.println("val: [" + val + "]");

		Assertions.assertTrue(val > 100);
	}

	@RepeatedTest(value = 5)
	void getRandomChar()
	{
		char randomChar = Randoms.getRandomChar();
		System.out.println("randomChar: [" + randomChar + "]");

		Assertions.assertTrue(randomChar >= 'a');
		Assertions.assertTrue(randomChar <= 'z');
	}

	@RepeatedTest(value = 5)
	void getRandomCharLowercase()
	{
		char randomChar = Randoms.getRandomCharLowercase();
		System.out.println("randomChar: [" + randomChar + "]");

		Assertions.assertTrue(randomChar >= 'a');
		Assertions.assertTrue(randomChar <= 'z');
	}

	@RepeatedTest(value = 5)
	void getRandomCharUppercase()
	{
		char randomChar = Randoms.getRandomCharUppercase();
		System.out.println("randomChar: [" + randomChar + "]");

		Assertions.assertTrue(randomChar >= 'A');
		Assertions.assertTrue(randomChar <= 'Z');
	}

	@RepeatedTest(value = 5)
	void getRandomCharInArrayRange()
	{
		char[] array = {'j', 'a', 'x', 'e', 'r'};
		char randomChar = Randoms.getRandomChar(array);
		System.out.println("randomChar: [" + randomChar + "]");

		boolean result = false;

		for (int i = 0; i < array.length; i++)
		{
			if (randomChar == array[i])
			{
				result = true;
				break;
			}
		}

		Assertions.assertTrue(result);
	}

	@Test
	void getRandomCharInArrayRangeNegativeCase()
	{
		Assertions.assertThrows(NullPointerException.class, () -> Randoms.getRandomChar(null));
	}

	@RepeatedTest(value = 5)
	void getRandomCharInRange()
	{
		char randomChar = Randoms.getRandomChar('d', 'm');
		System.out.println("randomChar: [" + randomChar + "]");

		boolean result = false;

		Assertions.assertTrue(randomChar >= 'd');
		Assertions.assertTrue(randomChar <= 'm');
	}
}