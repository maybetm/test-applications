package logIn;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import DB.DataModule;


/**
 * Servlet implementation class ServletLogIN
 */
@WebServlet("/ServletLogIN")
public class ServletLogIN extends HttpServlet {
	
	public static HttpSession session = null;	
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletLogIN() {
        super();
        // TODO Auto-generated constructor stub
        
     }
    
    public void init () throws ServletException {
    	
    	super.init();
    	
    	System.out.println("itit ServletLogIN");
    	
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		
		System.out.println("doPost");
		DataModule.setConnection();
		try {
			logIN(request.getParameter("mail"),
				  request.getParameter("password"), request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
	
	public static void logIN (String mail, String pass, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		
		// 1 - client
		// 2 - market
		
		ResultSet user = DataModule.USERS.getResultSet(mail);
		
		if (user != null) { 
			user.next();
		}
	
		//если верный логин и пароль
		if ( (user != null) && (user.getString("password").equals(DataModule.md5Apache(pass)))  ) {
			
			
			session = request.getSession(false);			
			session.setAttribute("mail", user.getString("email"));
			session.setAttribute("roleID", user.getInt("roleID"));
			session.setAttribute("userID", user.getInt("id"));
			System.out.println("[ServletLogIN.logIN] User mail: [" + user.getString("email") + "] is logged on! Time: " + DataModule.getNowDateTime());
			System.out.println("[ServletLogIN.logIN] session timer: " + session.getMaxInactiveInterval());
			System.out.println("[ServletLogIN.logIN] session atributs: userID:" + session.getAttribute("userID") + "; roleID: " + session.getAttribute("roleID"));
			
			
			try {
			if (user.getInt("roleID") == 1) {
				response.sendRedirect("user/user-page.jsp");
			}
			
			if (user.getInt("roleID") == 2) {
				response.sendRedirect("market/market-page.jsp");
			}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else { 
				
			System.out.println("[ServletLogIN.logIN] User not found! Time: " + DataModule.getNowDateTime());
			
			try {
				response.sendRedirect("logInForm.jsp");
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
		
	
	}
	


}
