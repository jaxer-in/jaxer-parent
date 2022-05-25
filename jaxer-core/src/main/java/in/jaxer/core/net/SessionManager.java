package in.jaxer.core.net;

import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Shakir Ansari
 */
@Log4j2
public class SessionManager
{
	public static Object getObject(HttpServletRequest httpServletRequest, String key)
	{
		log.debug("key: {}", key);

		HttpSession httpSession = httpServletRequest.getSession(false);
		if (httpSession == null)
		{
			return null;
		}

		return httpSession.getAttribute(key);
	}

	public static <T> T getObject(HttpServletRequest httpServletRequest, String key, Class<T> clazz)
	{
		log.debug("key: {}, objectType: {}", key, clazz);

		HttpSession httpSession = httpServletRequest.getSession(false);
		if (httpSession == null)
		{
			return null;
		}

		return (T) httpSession.getAttribute(key);
	}

	public static void setObject(HttpServletRequest httpServletRequest, String key, Object object)
	{
		log.debug("key: {}, object: {}", key, object);

		if (key == null || object == null)
		{
			return;
		}

		HttpSession httpSession = httpServletRequest.getSession(true);

		httpSession.setAttribute(key, object);
	}

	public static void removeObject(HttpServletRequest httpServletRequest, String key)
	{
		log.debug("key: {}", key);

		HttpSession httpSession = httpServletRequest.getSession(false);
		if (httpSession == null)
		{
			return;
		}

		httpServletRequest.removeAttribute(key);
	}

	public static void invalidate(HttpServletRequest httpServletRequest)
	{
		HttpSession httpSession = httpServletRequest.getSession();
		if (httpSession != null)
		{
			httpSession.invalidate();
		}
	}
}
