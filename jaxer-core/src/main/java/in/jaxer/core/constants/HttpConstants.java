package in.jaxer.core.constants;

/**
 * @author Shakir
 */
public class HttpConstants
{
	/**
	 * Standard Http method names
	 */
	public static final String DELETE = "DELETE";
	public static final String HEAD = "HEAD";
	public static final String GET = "GET";
	public static final String OPTIONS = "OPTIONS";
	public static final String POST = "POST";
	public static final String PUT = "PUT";
	public static final String TRACE = "TRACE";

	/**
	 * Standard Http Headers
	 */
	public static final String Accept = "Accept";
	public static final String Access_Control_Allow_Headers = "Access-Control-Allow-Headers";
	public static final String Access_Control_Allow_Origin = "Access-Control-Allow-Origin";
	public static final String Access_Control_Allow_Methods = "Access-Control-Allow-Methods";
	public static final String Content_Type = "Content-type";
	public static final String Cache_Control = "Cache-Control";
	public static final String Expires = "Expires";

	/**
	 * 2XX: generally "OK"
	 * HTTP Status-Code 200: OK.
	 */
	public static final int OK = 200;

	/**
	 * 2XX: generally "OK"
	 * HTTP Status-Code 201: Created.
	 */
	public static final int CREATED = 201;

	/**
	 * 2XX: generally "OK"
	 * HTTP Status-Code 202: Accepted.
	 */
	public static final int ACCEPTED = 202;

	/**
	 * 2XX: generally "OK"
	 * HTTP Status-Code 203: Non-Authoritative Information.
	 */
	public static final int NOT_AUTHORITATIVE = 203;

	/**
	 * 2XX: generally "OK"
	 * HTTP Status-Code 204: No Content.
	 */
	public static final int NO_CONTENT = 204;

	/**
	 * 2XX: generally "OK"
	 * HTTP Status-Code 205: Reset Content.
	 */
	public static final int RESET = 205;

	/**
	 * 2XX: generally "OK"
	 * HTTP Status-Code 206: Partial Content.
	 */
	public static final int PARTIAL = 206;

	/**
	 * 3XX: relocation/redirect
	 * HTTP Status-Code 300: Multiple Choices.
	 */
	public static final int MULT_CHOICE = 300;

	/**
	 * 3XX: relocation/redirect
	 * HTTP Status-Code 301: Moved Permanently.
	 */
	public static final int MOVED_PERM = 301;

	/**
	 * 3XX: relocation/redirect
	 * HTTP Status-Code 302: Temporary Redirect.
	 */
	public static final int MOVED_TEMP = 302;

	/**
	 * 3XX: relocation/redirect
	 * HTTP Status-Code 303: See Other.
	 */
	public static final int SEE_OTHER = 303;

	/**
	 * 3XX: relocation/redirect
	 * HTTP Status-Code 304: Not Modified.
	 */
	public static final int NOT_MODIFIED = 304;

	/**
	 * 3XX: relocation/redirect
	 * HTTP Status-Code 305: Use Proxy.
	 */
	public static final int USE_PROXY = 305;

	/**
	 * 4XX: client error
	 * HTTP Status-Code 400: Bad Request.
	 */
	public static final int BAD_REQUEST = 400;

	/**
	 * 4XX: client error
	 * HTTP Status-Code 401: Unauthorized.
	 */
	public static final int UNAUTHORIZED = 401;

	/**
	 * 4XX: client error
	 * HTTP Status-Code 402: Payment Required.
	 */
	public static final int PAYMENT_REQUIRED = 402;

	/**
	 * 4XX: client error
	 * HTTP Status-Code 403: Forbidden.
	 */
	public static final int FORBIDDEN = 403;

	/**
	 * 4XX: client error
	 * HTTP Status-Code 404: Not Found.
	 */
	public static final int NOT_FOUND = 404;

	/**
	 * 4XX: client error
	 * HTTP Status-Code 405: Method Not Allowed.
	 */
	public static final int BAD_METHOD = 405;

	/**
	 * 4XX: client error
	 * HTTP Status-Code 406: Not Acceptable.
	 */
	public static final int NOT_ACCEPTABLE = 406;

	/**
	 * 4XX: client error
	 * HTTP Status-Code 407: Proxy Authentication Required.
	 */
	public static final int PROXY_AUTH = 407;

	/**
	 * 4XX: client error
	 * HTTP Status-Code 408: Request Time-Out.
	 */
	public static final int CLIENT_TIMEOUT = 408;

	/**
	 * 4XX: client error
	 * HTTP Status-Code 409: Conflict.
	 */
	public static final int CONFLICT = 409;

	/**
	 * 4XX: client error
	 * HTTP Status-Code 410: Gone.
	 */
	public static final int GONE = 410;

	/**
	 * 4XX: client error
	 * HTTP Status-Code 411: Length Required.
	 */
	public static final int LENGTH_REQUIRED = 411;

	/**
	 * 4XX: client error
	 * HTTP Status-Code 412: Precondition Failed.
	 */
	public static final int PRECON_FAILED = 412;

	/**
	 * 4XX: client error
	 * HTTP Status-Code 413: Request Entity Too Large.
	 */
	public static final int ENTITY_TOO_LARGE = 413;

	/**
	 * 4XX: client error
	 * HTTP Status-Code 414: Request-URI Too Large.
	 */
	public static final int REQ_TOO_LONG = 414;

	/**
	 * 4XX: client error
	 * HTTP Status-Code 415: Unsupported Media Type.
	 */
	public static final int UNSUPPORTED_TYPE = 415;

	/**
	 * 5XX: server error
	 * HTTP Status-Code 500: Internal Server Error.
	 */
	public static final int INTERNAL_ERROR = 500;

	/**
	 * 5XX: server error
	 * HTTP Status-Code 501: Not Implemented.
	 */
	public static final int NOT_IMPLEMENTED = 501;

	/**
	 * 5XX: server error
	 * HTTP Status-Code 502: Bad Gateway.
	 */
	public static final int BAD_GATEWAY = 502;

	/**
	 * 5XX: server error
	 * HTTP Status-Code 503: Service Unavailable.
	 */
	public static final int UNAVAILABLE = 503;

	/**
	 * 5XX: server error
	 * HTTP Status-Code 504: Gateway Timeout.
	 */
	public static final int GATEWAY_TIMEOUT = 504;

	/**
	 * 5XX: server error
	 * HTTP Status-Code 505: HTTP Version Not Supported.
	 */
	public static final int VERSION = 505;
}
