package in.jaxer.core.utilities;

import lombok.extern.log4j.Log4j2;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
class CollectionsTest
{
	@Nested
	class AppendTests
	{
		@Test
		void whenBothListsAreNull()
		{
			List<Object> objectList = Collections.append(null, null);
			log.info("objectList: {}", objectList);

			Assertions.assertNull(objectList);
		}

		@Test
		void whenBothListsAreEmpty()
		{
			int expectedSize = 0;
			List<Object> objectList = Collections.append(new ArrayList<>(), new ArrayList<>());
			log.info("expectedSize: {}, size: {}, objectList: {}", expectedSize, objectList.size(), objectList);

			Assertions.assertEquals(expectedSize, objectList.size());
		}

		@Test
		void whenFirstListIsEmpty()
		{
			int expectedSize = 2;
			List<String> list = Collections.append(new ArrayList<>(), Arrays.asList("hello", "world"));
			log.info("expectedSize: {}, size: {}, list: {}", expectedSize, list.size(), list);

			Assertions.assertEquals(expectedSize, list.size());
		}

		@Test
		void whenSecondListIsEmpty()
		{
			int expectedSize = 3;
			List<String> list = Collections.append(Arrays.asList("hello", "world", "foo"), new ArrayList<>());
			log.info("expectedSize: {}, size: {}, list: {}", expectedSize, list.size(), list);

			Assertions.assertEquals(expectedSize, list.size());
		}

		@Test
		void okTest()
		{
			int expectedSize = 4;
			List<String> list = Collections.append(
					Collections.asArrayList("hello", "world"),
					Collections.asArrayList("foo", "bar")
			);
			log.info("expectedSize: {}, size: {}, list: {}", expectedSize, list.size(), list);

			Assertions.assertEquals(expectedSize, list.size());
		}

	}

	@Nested
	class AsArrayListTest
	{
		@Test
		void okTest()
		{
			int expectedSize = 2;
			List<String> list = Collections.asArrayList("hello", "world");

			log.info("expectedSize: {}, size: {}, list: {}", expectedSize, list.size(), list);
			Assertions.assertEquals(expectedSize, list.size());
		}
	}

	@Nested
	class ReverseTest
	{
		@Test
		void okTest()
		{
			List<String> actualList = Collections.asArrayList("hello", "world", "foo", "bar");
			List<String> expectedList = Collections.asArrayList("bar", "foo", "world", "hello");
			List<String> result = Collections.reverse(actualList);

			log.info("actualList: {}", actualList);
			log.info("expectedList: {}", expectedList);
			log.info("result: {}", result);

			Assertions.assertLinesMatch(expectedList, result);
		}

		@Test
		void whenInputListIsNull()
		{
			List<String> actualList = null;
			List<String> result = Collections.reverse(actualList);

			log.info("actualList: {}", actualList);
			log.info("result: {}", result);

			Assertions.assertNull(result);
		}

		@Test
		void whenInputListIsEmpty()
		{
			List<String> actualList = new ArrayList<>();
			List<String> expectedList = new ArrayList<>();
			List<String> result = Collections.reverse(actualList);

			log.info("actualList: {}", actualList);
			log.info("expectedList: {}", expectedList);
			log.info("result: {}", result);

			Assertions.assertLinesMatch(expectedList, result);
		}
	}

	@Nested
	class GetNextIdListTests
	{
 		@Test
		void okTest01()
		{
			List<Integer> actualList = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
			int chunkSize = 8;
			List<Integer>  expectedList = Arrays.asList(3,4,5,6,7,8,9,10);
			List<Integer> result = Collections.getNextIdList(chunkSize, 3, actualList);

			log.info("actualList: {}", actualList);
			log.info("expectedList: {}", expectedList);
			log.info("result: {}", result);

			Assertions.assertTrue(result.equals(expectedList));
		}
	}

}