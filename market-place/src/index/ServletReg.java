package index;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DB.DataModule;


/**
 * Servlet implementation class servletReg
 */
@WebServlet("/ServletReg")
public class ServletReg extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub	
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		
		DataModule.setConnection();				
		
		if (request.getParameter("reg") != null) {
			try {
				insertUsers(request, response, 1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (request.getParameter("regEnt") != null) {
			try {
				insertUsers(request, response, 2);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
			
		//response.sendRedirect("index.html");
	}
	
	public static void insertUsers (HttpServletRequest request, HttpServletResponse response, Integer roleID) throws SQLException, IOException {		
	
		// сделать оповещение пользователю на экране, что его данные не подходяд
		//пока просто происходит редирект на форму регистрации
		
		
		
		if ( (request.getParameter("mail").equals(""))
				|| (DataModule.USERS.findUser(request.getParameter("mail").toLowerCase())) //поиск похожей почты в users
				|| (request.getParameter("password").equals("") ) 
				|| request.getParameter("firstName").equals("")) {			
			
			System.out.println("[insertUser] little data to create an account, or this account is already registered. time: " + DataModule.getNowDateTime());
			response.sendRedirect("registration-form.jsp");
			
		} else {

		DataModule.USERS.insert(
				request.getParameter("firstName"),
				request.getParameter("mail"),
				request.getParameter("password"), roleID);
		response.sendRedirect("user/user-page.jsp");
			
		}
	}	
}
