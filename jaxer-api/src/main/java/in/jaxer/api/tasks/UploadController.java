package in.jaxer.api.tasks;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;

/**
 * @author Shakir Ansari
 */
//@WebServlet(urlPatterns = "/uploadController")
@MultipartConfig()
public class UploadController extends HttpServlet
{
//	@Override
//	protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
//	{
//		System.out.println("UploadController.doPost() - " + new Date());
//
//		ApiResponseCO apiResponseCO = null;
//
//		try
//		{
//			RequestHandler requestHandler = new RequestHandler(httpServletRequest, httpServletResponse, true);
//			apiResponseCO = requestHandler.handleMultipartRequests();
//		} catch (Exception exception)
//		{
//			if (apiResponseCO == null)
//			{
//				apiResponseCO = new ApiResponseCO();
//				apiResponseCO.addErrorCo(exception);
//			}
//		} finally
//		{
////			System.out.println("UploadController.doPost() - " + AppUtils.getGson().toJson(apiResponseCO));
//			Servlets.printJsonResponse(httpServletResponse, AppUtils.getGson(), apiResponseCO);
//		}
//	}
}
