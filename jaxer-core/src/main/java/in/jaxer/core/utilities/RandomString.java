package in.jaxer.core.utilities;

import lombok.extern.log4j.Log4j2;

import java.util.Random;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class RandomString
{
	private String characterString = "abcdefghijklmnopqrstuvwxyz0123456789";
	private int length = 8;
	private Random randomGenerator = new Random();

	public RandomString()
	{
	}

	public RandomString(String characterString, int length)
	{
		this.randomGenerator = new Random();
		this.characterString = characterString;
	}

	public RandomString(String characterString)
	{
		this.characterString = characterString;
	}

	public RandomString(int length)
	{
		this.length = length;
	}

	private int getRandomNumber(String characterString)
	{
		int randomInt = this.randomGenerator.nextInt(characterString.length());
		return (randomInt - 1 == -1) ? randomInt : randomInt - 1;
	}

	public String generateRandomString()
	{
		StringBuilder randStr = new StringBuilder();
		for (int i = 0; i < length; i++)
		{
			int number = getRandomNumber(characterString);
			char ch = characterString.charAt(number);
			randStr.append(ch);
		}
		return randStr.toString();
	}
}
