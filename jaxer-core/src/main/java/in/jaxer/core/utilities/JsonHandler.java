package in.jaxer.core.utilities;

import com.google.gson.*;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shakir
 * date 2022-05-25 18:57
 */
@Log4j2
public class JsonHandler
{
	private static Gson gsonPretty;

	private static Gson gson;

	public static Gson getGson()
	{
		return gson == null ? new Gson() : gson;
	}

	public static Gson getGsonPrettyPrinting()
	{
		return gsonPretty == null ? new GsonBuilder().setPrettyPrinting().create() : gsonPretty;
	}

	public static String getPrettyJson(String uglyJson)
	{
		log.debug("uglyJson: {}", uglyJson);
		JValidator.throwWhenNullOrEmpty(uglyJson, "Json string cannot be empty");

		JsonElement jsonElement = JsonParser.parseString(uglyJson);
		return getGsonPrettyPrinting().toJson(jsonElement);
	}

	public static String toJsonString(Object object)
	{
		return object == null ? null : getGson().toJson(object);
	}

	public static <T> T toObject(String jsonString, Class<T> clazz)
	{
		log.debug("jsonString: {}, class: {}", jsonString, clazz);
		JValidator.throwWhenNull(clazz, "Type cannot be null");

		return JValidator.isNullOrEmpty(jsonString) ? null : getGson().fromJson(jsonString, clazz);
	}

	public static <T> List<T> toObjectList(String jsonString, Class<T> clazz)
	{
		log.debug("jsonString: {}, class: {}", jsonString, clazz);
		JValidator.throwWhenNull(clazz, "Type cannot be null");

		JsonArray array = JsonParser.parseString(jsonString).getAsJsonArray();

		List<T> tList = new ArrayList<>();
		for (final JsonElement json : array)
		{
			tList.add(getGson().fromJson(json, clazz));
		}
		return tList;
	}

	public static JsonObject getElementAsJsonObject(String jsonString, String key)
	{
		log.debug("key: {}, jsonString: {}", key, jsonString);

		JValidator.throwWhenNullOrEmpty(jsonString);
		JValidator.throwWhenNullOrEmpty(key);

		JsonElement jelement = JsonParser.parseString(jsonString);
		JsonObject jObject = jelement.getAsJsonObject();
		return jObject.getAsJsonObject(key);
	}

	public static JsonArray getElementAsJsonArray(String jsonString, String key)
	{
		log.debug("key: {}, jsonString: {}", key, jsonString);

		JValidator.throwWhenNullOrEmpty(jsonString);
		JValidator.throwWhenNullOrEmpty(key);

		JsonElement jelement = JsonParser.parseString(jsonString);
		JsonObject jObject = jelement.getAsJsonObject();

		return jObject.getAsJsonArray(key);
	}

	public static <T> T getElementAsType(String jsonString, Class<T> clazz, String key)
	{
		log.debug("key: {}, clazz: {}, jsonString: {}", key, clazz, jsonString);

		JValidator.throwWhenNullOrEmpty(jsonString);
		JValidator.throwWhenNull(clazz);
		JValidator.throwWhenNullOrEmpty(key);

		return getGson().fromJson(getElementAsJsonObject(jsonString, key), clazz);
	}

	public static String getElementAsString(String jsonString, String key)
	{
		log.debug("key: {}, jsonString: {}", key, jsonString);

		JValidator.throwWhenNullOrEmpty(jsonString);
		JValidator.throwWhenNullOrEmpty(key);

		return getElementAsType(jsonString, String.class, key);
	}

	public static <T> List<T> getElementAsTypeList(String jsonString, Class<T> clazz, String key)
	{
		log.debug("key: {}, clazz: {}, jsonString: {}", key, clazz, jsonString);

		JValidator.throwWhenNullOrEmpty(jsonString);
		JValidator.throwWhenNull(clazz);
		JValidator.throwWhenNullOrEmpty(key);

		List<T> tList = new ArrayList<>();
		for (final JsonElement json : getElementAsJsonArray(jsonString, key))
		{
			tList.add(getGson().fromJson(json, clazz));
		}
		return tList;
	}

}
