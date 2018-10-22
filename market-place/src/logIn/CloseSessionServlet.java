package logIn;



import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class CloseSessionServlet
 */
@WebServlet("/CloseSessionServlet")
public class CloseSessionServlet extends HttpServlet {
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		 StringBuffer sb = new StringBuffer();
		    String line = null;

		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		        sb.append(line);
		    
	
		request.getSession().invalidate();
	
		System.out.println("close session: " + sb.toString());
		
		
	}



}
