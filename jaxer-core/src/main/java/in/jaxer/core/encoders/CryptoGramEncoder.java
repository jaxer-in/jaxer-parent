package in.jaxer.core.encoders;

import java.util.Random;

/**
 * @author Shakir
 * @since 0.0.1
 */
public class CryptoGramEncoder
{
	private static char[] getAlphabet()
	{
		char[] a = new char[26];
		for (int i = 0; i < 26; i++)
		{
			a[i] = (char) ('A' + i);
		}
		return a;
	}

	private static void permute(char[] a)
	{
		Random generator = new Random();
		for (int i = 0; i < a.length - 1; i++)
		{
			int position = generator.nextInt(a.length - i) + i;
			char temp = a[i];
			a[i] = a[position];
			a[position] = temp;
		}
	}

	private static String scramble(char[] key, String phrase)
	{
		String puzzle = "";
		int len = phrase.length(), position;
		for (int i = 0; i < len; i++)
		{
			char ch = phrase.charAt(i);
			if (ch >= 'A' && ch <= 'Z')
			{
				position = ch - 'A';
				puzzle += key[position];
			} else
			{
				puzzle += ch;
			}
		}
		return puzzle;
	}

	public static String encode(String msg)
	{
		char[] key = getAlphabet();
		permute(key);
		return scramble(key, msg);
	}
}
