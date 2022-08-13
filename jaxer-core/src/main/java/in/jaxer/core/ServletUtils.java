package in.jaxer.core;

import in.jaxer.core.constants.ContentType;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shakir
 * @date 14-08-2022
 * @since v1.1.0-beta
 */
@Log4j2
public class ServletUtils
{
	/**
	 * <p>This method will check if the request's content type is multipart/form-data</p>
	 * <p>In simple words, this method will let you know if the request contains attachments or not</p>
	 *
	 * @since v1.1.0-beta
	 */
	static public boolean isMultipartRequest(HttpServletRequest request)
	{
		log.debug("request: {}", request);
		return request.getContentType() != null
				&& request.getContentType().toLowerCase().contains(ContentType.MULTIPART_FORM_DATA);
	}

}
