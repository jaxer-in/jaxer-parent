
package in.jaxer.core.utilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import in.jaxer.core.constants.Singletons;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Shakir Ansari
 */
public class Collections
{

	public static boolean isEmpty(Collection collection)
	{
		return Validator.isEmpty(collection);
	}

	public static boolean isNotEmpty(Collection collection)
	{
		return Validator.isNotEmpty(collection);
	}

	public static boolean isEmpty(Map map)
	{
		return Validator.isEmpty(map);
	}

	public static boolean isNotEmpty(Map map)
	{
		return !isEmpty(map);
	}

	public static <T> List<T> append(List<T> list1, List<T> list2)
	{
		if (isEmpty(list1) && isEmpty(list2))
		{
			return null;
		}

		if (isEmpty(list1))
		{
			return list2;
		}

		if (isEmpty(list2))
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
		if (isNotEmpty(list))
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

		if (isNotEmpty(list))
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
		if (isEmpty(list))
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

	public static <T> List<T> toList(String text, Class<T> clazz)
	{
		Validator.requireNotEmpty(text);

		JsonArray array = JsonParser.parseString(text).getAsJsonArray();

		List<T> tList = new ArrayList<>();
		for (final JsonElement json : array)
		{
			tList.add(Singletons.getGson(false).fromJson(json, clazz));
		}
		return tList;
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
