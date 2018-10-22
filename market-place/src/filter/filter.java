package filter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DataModule;
import logIn.ServletLogIN;

public class filter {

	public static Integer checkSession (HttpServletRequest request) {
		
		Integer role; //user; market
		
		if (request.getSession().getAttribute("roleID") == null) {
			return null;
		} 
		
		try {
			
			if (request.getSession().getAttribute("roleID") != null) {
				role =  (Integer) request.getSession().getAttribute("roleID");
				return role;
	       }			
			
		} catch (IllegalStateException ex)  {
			
			System.out.println("[updateBTN.getTextForLoginBtn] session is not found.");
			
		}
		
		return null;
		
	}
	
	@WebFilter("/logInForm.jsp")
	public static class redirectLogIn implements Filter {

		public void destroy() {
			// TODO Auto-generated method stub
		}

		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
			
			System.out.println("logInForm filter doFilter");
			
	        HttpServletRequest req = (HttpServletRequest) request;
	        
	        HttpServletResponse resp = (HttpServletResponse) response;
	        
	        String servletPath = req.getContextPath();
			
			Integer role = checkSession(req);
			

			if (role != null) {
				
				switch (role) {
				case 1: resp.sendRedirect(servletPath + "/user/user-page.jsp"); break;
				
				case 2: resp.sendRedirect(servletPath + "/market/market-page.jsp"); break;
				

				default: chain.doFilter(request, response);
					break;
				}
				
			} else { 
				chain.doFilter(request, response);
			}

			
			// pass the request along the filter chain
			//chain.doFilter(request, response);
		}

		public void init(FilterConfig fConfig) throws ServletException {
			// TODO Auto-generated method stub
			
			System.out.println("logInForm filter init");
		}
	}

	@WebFilter(urlPatterns = {"/market/*", "/DeleteGoods/*", "/getCategory/*", "/GetStatistics/*","/UpdateGoods/*","/UpdateUTC/*", "/goodsServlet/*"})
	public static class Market implements Filter {

		/**
		 * @see Filter#destroy()
		 */
		public void destroy() {
			// TODO Auto-generated method stub
		}

		/**
		 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
		 */
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	
			HttpServletRequest req = (HttpServletRequest) request;
	        
	        HttpServletResponse resp = (HttpServletResponse) response;
	        
	        String servletPath = req.getServletPath();
			
			Integer role = (Integer) req.getSession().getAttribute("roleID");
			
			//возврат на домашнюю страницу
			//ещё нужен редирект на страницу с ошибкой (Нет доступа)
			String urlHome = req.getContextPath() + "/index.jsp";
			
			if ((role != null) && (role == 2)) {	
				
				chain.doFilter(request, response);
			} else { 

				resp.sendRedirect(urlHome);				
			}
			
      
	        // Разрешить request продвигаться дальше. (Перейти данный Filter).
	       // chain.doFilter(request, response);
	        
		}

		public void init(FilterConfig fConfig) throws ServletException {
			// TODO Auto-generated method stub
			System.out.println("Market filter init");
			

		}
	}
	
	@WebFilter(urlPatterns = {"/user/*", "/removeGoodsFromCart/*", "/CartAdd/*", "/GetGoodsInCart/*", "/PayCart/*"})
	public static class User implements javax.servlet.Filter {
		
//		private FilterConfig config = null; 
//	    private boolean active = false;

	    @Override
	    public void init(FilterConfig filterConfig) throws ServletException {
	    	// TODO Auto-generated method stub
	    	
			System.out.println("user filter init");			

	    }
	    
		@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
				throws IOException, ServletException {
			
	        HttpServletRequest req = (HttpServletRequest) request;
	        
	        HttpServletResponse resp = (HttpServletResponse) response;
	        
	        String servletPath = req.getServletPath();
			
			Integer role = checkSession(req);
			
			//возврат на домашнюю страницу
			String urlHome = req.getContextPath() + "/index.jsp";
			
	        System.out.println("#INFO " + new Date() + " - ServletPath :" + servletPath //
	                + ", URL =" + req.getRequestURL() + " ;role: " + checkSession(req));
			
			if ((role != null) && (role == 1) ) {	
				chain.doFilter(request, response);
			} else { 
		        System.out.println("#INFO " + new Date() + " - ServletPath :" + servletPath //
		                + ", URL =" + req.getRequestURL());
				resp.sendRedirect(urlHome);				
			}
			
			
	        System.out.println("#INFO " + new Date() + " - ServletPath :" + servletPath //
	                + ", URL =" + req.getRequestURL());
	        
	        // Разрешить request продвигаться дальше. (Перейти данный Filter).
	        //chain.doFilter(request, response);
		}
		
		public void destroy() {

		}

	}



}
