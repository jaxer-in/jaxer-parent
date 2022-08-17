package in.jaxer.core;

/**
 * @author Shakir
 * @date 17-08-2022
 * @since v1.1.0-beta
 */
public class HttpUtils
{
	/**
	 * Standard Http Headers
	 *
	 * @since v1.1.0-beta
	 */
	public class Header
	{
		public static final String ACCEPT = "Accept";
		public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
		public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
		public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
		public static final String CONTENT_TYPE = "Content-type";
		public static final String CACHE_CONTROL = "Cache-Control";
		public static final String EXPIRES = "Expires";
	}

	/**
	 * Standard Http Status codes
	 *
	 * @since v1.1.0-beta
	 */
	public static class StatusCode
	{
		/**
		 * 2XX: generally "OK"
		 * HTTP Status-Code 200: OK.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int OK = 200;

		/**
		 * 2XX: generally "OK"
		 * HTTP Status-Code 201: Created.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int CREATED = 201;

		/**
		 * 2XX: generally "OK"
		 * HTTP Status-Code 202: Accepted.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int ACCEPTED = 202;

		/**
		 * 2XX: generally "OK"
		 * HTTP Status-Code 203: Non-Authoritative Information.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int NOT_AUTHORITATIVE = 203;

		/**
		 * 2XX: generally "OK"
		 * HTTP Status-Code 204: No Content.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int NO_CONTENT = 204;

		/**
		 * 2XX: generally "OK"
		 * HTTP Status-Code 205: Reset Content.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int RESET = 205;

		/**
		 * 2XX: generally "OK"
		 * HTTP Status-Code 206: Partial Content.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int PARTIAL = 206;

		/**
		 * 3XX: relocation/redirect
		 * HTTP Status-Code 300: Multiple Choices.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int MULT_CHOICE = 300;

		/**
		 * 3XX: relocation/redirect
		 * HTTP Status-Code 301: Moved Permanently.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int MOVED_PERM = 301;

		/**
		 * 3XX: relocation/redirect
		 * HTTP Status-Code 302: Temporary Redirect.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int MOVED_TEMP = 302;

		/**
		 * 3XX: relocation/redirect
		 * HTTP Status-Code 303: See Other.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int SEE_OTHER = 303;

		/**
		 * 3XX: relocation/redirect
		 * HTTP Status-Code 304: Not Modified.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int NOT_MODIFIED = 304;

		/**
		 * 3XX: relocation/redirect
		 * HTTP Status-Code 305: Use Proxy.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int USE_PROXY = 305;

		/**
		 * 4XX: client error
		 * HTTP Status-Code 400: Bad Request.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int BAD_REQUEST = 400;

		/**
		 * 4XX: client error
		 * HTTP Status-Code 401: Unauthorized.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int UNAUTHORIZED = 401;

		/**
		 * 4XX: client error
		 * HTTP Status-Code 402: Payment Required.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int PAYMENT_REQUIRED = 402;

		/**
		 * 4XX: client error
		 * HTTP Status-Code 403: Forbidden.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int FORBIDDEN = 403;

		/**
		 * 4XX: client error
		 * HTTP Status-Code 404: Not Found.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int NOT_FOUND = 404;

		/**
		 * 4XX: client error
		 * HTTP Status-Code 405: Method Not Allowed.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int BAD_METHOD = 405;

		/**
		 * 4XX: client error
		 * HTTP Status-Code 406: Not Acceptable.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int NOT_ACCEPTABLE = 406;

		/**
		 * 4XX: client error
		 * HTTP Status-Code 407: Proxy Authentication Required.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int PROXY_AUTH = 407;

		/**
		 * 4XX: client error
		 * HTTP Status-Code 408: Request Time-Out.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int CLIENT_TIMEOUT = 408;

		/**
		 * 4XX: client error
		 * HTTP Status-Code 409: Conflict.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int CONFLICT = 409;

		/**
		 * 4XX: client error
		 * HTTP Status-Code 410: Gone.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int GONE = 410;

		/**
		 * 4XX: client error
		 * HTTP Status-Code 411: Length Required.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int LENGTH_REQUIRED = 411;

		/**
		 * 4XX: client error
		 * HTTP Status-Code 412: Precondition Failed.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int PRECON_FAILED = 412;

		/**
		 * 4XX: client error
		 * HTTP Status-Code 413: Request Entity Too Large.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int ENTITY_TOO_LARGE = 413;

		/**
		 * 4XX: client error
		 * HTTP Status-Code 414: Request-URI Too Large.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int REQ_TOO_LONG = 414;

		/**
		 * 4XX: client error
		 * HTTP Status-Code 415: Unsupported Media Type.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int UNSUPPORTED_TYPE = 415;

		/**
		 * 5XX: server error
		 * HTTP Status-Code 500: Internal Server Error.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int INTERNAL_ERROR = 500;

		/**
		 * 5XX: server error
		 * HTTP Status-Code 501: Not Implemented.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int NOT_IMPLEMENTED = 501;

		/**
		 * 5XX: server error
		 * HTTP Status-Code 502: Bad Gateway.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int BAD_GATEWAY = 502;

		/**
		 * 5XX: server error
		 * HTTP Status-Code 503: Service Unavailable.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int UNAVAILABLE = 503;

		/**
		 * 5XX: server error
		 * HTTP Status-Code 504: Gateway Timeout.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int GATEWAY_TIMEOUT = 504;

		/**
		 * 5XX: server error
		 * HTTP Status-Code 505: HTTP Version Not Supported.
		 *
		 * @since v1.1.0-beta
		 */
		public static final int VERSION = 505;
	}

	/**
	 * Standard Http method names
	 *
	 * @since v1.1.0-beta
	 */
	public class Method
	{
		public static final String DELETE = "DELETE";
		public static final String HEAD = "HEAD";
		public static final String GET = "GET";
		public static final String OPTIONS = "OPTIONS";
		public static final String POST = "POST";
		public static final String PUT = "PUT";
		public static final String TRACE = "TRACE";
	}
}
