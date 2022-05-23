package in.jaxer.core.utilities;

/**
 * @author Shakir Ansari
 */
public class Sortings
{
	public static void bubbleSort(int[] x)
	{
		boolean doMore = true;
		while (doMore)
		{
			doMore = false;
			for (int i = 0; i < x.length - 1; i++)
			{
				if (x[i] > x[i + 1])
				{
					int temp = x[i];
					x[i] = x[i + 1];
					x[i + 1] = temp;
					doMore = true;
				}
			}
		}
	}

	public static void selectionSort(int[] x)
	{
		for (int i = 0; i < x.length - 1; i++)
		{
			// Index of smallest remaining value.
			int minIndex = i;
			for (int j = i + 1; j < x.length; j++)
			{
				if (x[minIndex] > x[j])
				{
					minIndex = j;
				}
			}

			if (minIndex != i)
			{
				// Exchange current element with smallest remaining.
				int temp = x[i];
				x[i] = x[minIndex];
				x[minIndex] = temp;
			}
		}
	}

	public static void reverseArray(int[] b)
	{
		int left = 0;
		int right = b.length - 1;

		while (left < right)
		{
			int temp = b[left];
			b[left] = b[right];
			b[right] = temp;

			left++;
			right--;
		}
	}
}
