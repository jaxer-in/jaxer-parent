
package in.jaxer.core.utilities;

import java.util.Random;

/**
 *
 * @author Shakir Ansari
 */
public class Randoms
{

	public static int getRandomInt()
	{
		return new Random().nextInt();
	}

	public static int getRandomIntLowerThan(int lowerValue)
	{
		return new Random().nextInt(lowerValue - 1);
	}

	public static int getRandomIntHigherThan(int highValue)
	{
		return getRandomIntInRange(highValue + 1, Integer.MAX_VALUE);
	}

	public static int getRandomIntInRange(int min, int max)
	{
		return new Random().nextInt((max - min) + 1) + min;
	}

	public static char getRandomChar(char[] array)
	{
		JValidator.requireNotEmpty(array);

		return array[getRandomIntInRange(0, array.length - 1)];
	}

	//Random Lower Character Values
	public static char getRandomChar()
	{
		return (char) Randoms.getRandomIntInRange((int) 'a', (int) ('z'));
	}

	public static char getRandomCharInRange(char minChar, char maxChar)
	{
		return (char) Randoms.getRandomIntInRange((int) minChar, (int) maxChar);
	}

	//Random Upper Character Values
	public static char getRandomCharUpperCase()
	{
		return (char) Randoms.getRandomIntInRange((int) 'A', (int) ('Z'));
	}

	public static char getRandomCharInRangeUpperCase(char min_char, char max_char)
	{
		return (char) Randoms.getRandomIntInRange((int) min_char, (int) max_char);
	}
}
