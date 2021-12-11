
package in.jaxer.api.tasks;

import in.jaxer.api.dtos.ApiResponseDto;
import in.jaxer.api.dtos.RequestResponseDto;
import in.jaxer.api.listners.Authentication;
import in.jaxer.api.request.AbstractRestController;
import in.jaxer.core.net.Servlets;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shakir Ansari
 */
@WebServlet(urlPatterns = "/jaxerRestController")
public class RestController extends AbstractRestController
{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ApiResponseDto apiResponseDto = doProcess(request, response, null, new AuthenticationImpl());

		System.out.println("apiResponseDto: [" + apiResponseDto + "]");

		Servlets.printJsonResponse(response, apiResponseDto, false);
	}

	@Override
	protected String getBasePackage()
	{
		return "in.jaxer.*";
	}

	private class AuthenticationImpl implements Authentication
	{

		@Override
		public void doAuthentication(Connection connection, RequestResponseDto requestResponseObject)
		{
		}
	}

}
