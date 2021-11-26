
package in.jaxer.core.net;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Shakir Ansari
 */
public class SessionManager
{

	public static Object getObject(HttpServletRequest httpServletRequest, String key)
	{
		HttpSession httpSession = httpServletRequest.getSession(false);
		if (httpSession == null)
		{
			return null;
		}

		return httpSession.getAttribute(key);
	}

	public static <T> T getObject(HttpServletRequest httpServletRequest, String key, Class<T> clazz)
	{
		HttpSession httpSession = httpServletRequest.getSession(false);
		if (httpSession == null)
		{
			return null;
		}

		return (T) httpSession.getAttribute(key);
	}

	public static void setObject(HttpServletRequest httpServletRequest, String key, Object object)
	{
		if (object == null)
		{
			return;
		}

		HttpSession httpSession = httpServletRequest.getSession(true);

		httpSession.setAttribute(key, object);
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
