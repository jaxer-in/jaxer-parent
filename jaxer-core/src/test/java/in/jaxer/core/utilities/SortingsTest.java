package in.jaxer.core.utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Shakir
 * date 2022-05-23 16:46
 */
class SortingsTest
{
	@Test
	void bubbleSortTest1()
	{
		System.out.println("bubbleSortTest1");

		int[] sourceArray = {10, 2, 25, 5, 7, 8};
		int[] expectedArray = {2, 5, 7, 8, 10, 25};

		Sortings.bubbleSort(sourceArray);

		Assertions.assertArrayEquals(expectedArray, sourceArray);
	}

	@Test
	void selectionSortTest1()
	{
		System.out.println("selectionSortTest1");
		int[] sourceArray = {10, 2, 333, 25, 5, 7, 8, 99};
		int[] expectedArray = {2, 5, 7, 8, 10, 25, 99, 333};

		Sortings.selectionSort(sourceArray);

		Assertions.assertArrayEquals(expectedArray, sourceArray);
	}

	@Test
	void reverseArrayTest1()
	{
		System.out.println("reverseArrayTest1");
		int[] sourceArray = {10, 2, 333, 25, 5, 7, 8, 99};
		int[] expectedArray = {99, 8, 7, 5, 25, 333, 2, 10};

		Sortings.reverseArray(sourceArray);

		Assertions.assertArrayEquals(expectedArray, sourceArray);
	}
}