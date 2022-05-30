package in.jaxer.core.utilities;

import lombok.extern.log4j.Log4j2;

import java.util.Random;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class Randoms
{
	public static int getRandomInt()
	{
		return new Random().nextInt();
	}

	@Deprecated
	public static int getRandomIntInRange(int min, int max)
	{
		return new Random().nextInt((max - min) + 1) + min;
	}

	public static int getRandomInt(int min, int max)
	{
		return new Random().nextInt((max - min) + 1) + min;
	}

	@Deprecated
	public static int getRandomIntLowerThan(int lowerValue)
	{
		return new Random().nextInt(lowerValue - 1);
	}

	public static int getRandomIntLessThan(int lowerValue)
	{
		return new Random().nextInt(lowerValue - 1);
	}

	@Deprecated
	public static int getRandomIntHigherThan(int highValue)
	{
		return getRandomIntInRange(highValue + 1, Integer.MAX_VALUE);
	}

	public static int getRandomIntGreaterThan(int highValue)
	{
		return getRandomIntInRange(highValue + 1, Integer.MAX_VALUE);
	}

	public static char getRandomChar()
	{
		return (char) Randoms.getRandomIntInRange('a', 'z');
	}

	public static char getRandomChar(char[] array)
	{
		return array[getRandomIntInRange(0, array.length - 1)];
	}

	public static char getRandomChar(char minChar, char maxChar)
	{
		return (char) Randoms.getRandomIntInRange(minChar, maxChar);
	}

	public static char getRandomCharLowercase()
	{
		return (char) Randoms.getRandomInt('a', 'z');
	}

	public static char getRandomCharUppercase()
	{
		return (char) Randoms.getRandomIntInRange('A', 'Z');
	}
}
