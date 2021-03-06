package in.jaxer.core.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Shakir Ansari
 */
public class Collections
{
	@Deprecated
	public static boolean isEmpty(Collection collection)
	{
		return JValidator.isEmpty(collection);
	}

	public static boolean isNullOrEmpty(Collection collection)
	{
		return collection == null || collection.isEmpty();
	}

	@Deprecated
	public static boolean isNotEmpty(Collection collection)
	{
		return JValidator.isNotEmpty(collection);
	}

	public static boolean isNotNullAndNotEmpty(Collection collection)
	{
		return collection != null && !collection.isEmpty();
	}

	@Deprecated
	public static boolean isEmpty(Map map)
	{
		return JValidator.isEmpty(map);
	}

	public static boolean isNullOrEmpty(Map map)
	{
		return map == null || map.isEmpty();
	}

	@Deprecated
	public static boolean isNotEmpty(Map map)
	{
		return !isEmpty(map);
	}

	public static boolean isNotNullAndNotEmpty(Map map)
	{
		return map != null && !map.isEmpty();
	}

	public static <T> List<T> append(List<T> list1, List<T> list2)
	{
		if (isNullOrEmpty(list1) && isNullOrEmpty(list2))
		{
			return null;
		}

		if (isNullOrEmpty(list1))
		{
			return list2;
		}

		if (isNullOrEmpty(list2))
		{
			return list1;
		}

		for (T t : list2)
		{
			list1.add(t);
		}

		return list1;
	}

	public static <T> List<T> subList(List<T> list, int limit)
	{
		if (limit < 1)
		{
			throw new IllegalArgumentException("limit cannot be less than or equals to zero");
		}

		List<T> responseList = new ArrayList<>();
		if (isNotNullAndNotEmpty(list))
		{
			for (int i = 0; i < list.size(); i++)
			{
				responseList.add(list.get(i));

				if (i + 1 == limit)
				{
					break;
				}
			}
		}

		return responseList;
	}

	public static <T> List<T> subList(List<T> list, int start, int end)
	{
		List<T> responseList = new ArrayList<>();

		if (isNotNullAndNotEmpty(list))
		{
			for (int i = start - 1; i < end; i++)
			{
				responseList.add(list.get(i));
			}
		}

		return responseList;
	}

	public static <T> List<T> reverse(List<T> list)
	{
		if (isNullOrEmpty(list))
		{
			return null;
		}

		int left = 0;
		int right = list.size() - 1;
		T temp = null;

		while (left < right)
		{
			temp = list.get(left);
			list.set(left, list.get(right));
			list.set(right, temp);

			left++;
			right--;
		}

		return list;
	}

	public static <T> List<T> getNextIdList(int chunk, int currentIndex, List<T> idList)
	{
		List<T> temp = new ArrayList<>();

		for (int i = currentIndex; i < chunk + currentIndex; i++)
		{
			if (i < idList.size())
			{
				temp.add(idList.get(i));
			}
		}

		return temp;
	}
}
