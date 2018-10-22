package logIn;
import javax.servlet.http.HttpServletRequest;


public abstract class updateTextOnIndex implements HttpServletRequest {

	
	private static HttpServletRequest request;
	
	public void setServletRequest(HttpServletRequest request) {
	    this.request = request;
	}

	public static String getTextForLoginBtn (HttpServletRequest request )  {
		
		String mail;

				
		if (request.getSession().getAttribute("mail") == null) {
			return "Войти";
		} 
		
		try {
			if (request.getSession().getAttribute("mail") != null) {
				mail = request.getSession().getAttribute("mail").toString();
				return mail;
	       }			
			
		} catch (IllegalStateException ex)  {
			
			System.out.println("[updateBTN.getTextForLoginBtn] session is not found.");
			
			return "Войти";
		}
		
		return null; 
	}


	
}
