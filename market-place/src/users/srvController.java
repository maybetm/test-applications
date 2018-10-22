package users;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.postgresql.util.PSQLException;

import com.sun.org.apache.regexp.internal.recompile;

import DB.DataModule;
import DB.DataModule.goods;

public class srvController {

	@WebServlet("/CartAdd")
	public static class CartAdd extends HttpServlet {
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response) {
			response.setContentType("text/json; charset=utf-8");
			JSONObject answer = new JSONObject();
			Integer count = null;
			DataModule.setConnection();
			ResultSet resQuery = null;
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			try {
				Boolean accessUp;
				Integer goodsId = new Integer (request.getParameter("goods-id"));
				count = new Integer (request.getParameter("count"));
				resQuery = DataModule.CART.accessUpdate(goodsId, count);	
				resQuery.next();
				accessUp = resQuery.getBoolean("accessUp");
		
	
				System.out.println("access update to cart: " + accessUp);
				System.out.println("access update to cart, gdCount: " + resQuery.getInt("gdCount"));
			if (request.getSession().getAttribute("userID") != null && 
					(Integer) request.getSession().getAttribute("roleID") == 1 && count != null) { 
				  

					if (accessUp) { 
						addGoodsInCart((Integer) request.getSession().getAttribute("userID"),
								new Integer (request.getParameter("goods-id")), count);
						
						answer.put("goodsid", goodsId);
						response.getWriter().println(answer);					
					} else { 
						answer.put("message", "invalid value");
						answer.put("code", "-1");
						answer.put("count", resQuery.getInt("gdCount"));
						response.getWriter().println(answer);	
					}			

					
			} else {		
				answer.put("message", "Access denied");
				answer.put("code", "403");
				System.out.println(answer);
				response.getWriter().println(answer);				
			}
			
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
		

		
		public static void addGoodsInCart (Integer userID, Integer goodsID, Integer count) {		
			PreparedStatement query;
			Integer countGoogdsInCart;
			try {
				 query = 
						DataModule.con.prepareStatement("select count (*) as cR \r\n" + 
														"from cart\r\n" + 
														"where userID = ? and goodsid = ?");
				query.setInt(1, userID);
				query.setInt(2, goodsID);
				ResultSet res = query.executeQuery();
				res.next();
				countGoogdsInCart = res.getInt("cR");	
				
				query = DataModule.con
						.prepareStatement("select count (*) as cR \r\n" + 
										  "from cart\r\n" + 
										  "where userID = ? and goodsid = ?");
				
	
				switch (countGoogdsInCart) {
				case 0:
					DataModule.CART.insert(userID,	goodsID, count);
					break;
					
				case 1:
					DataModule.CART.updateOnChange(userID,	goodsID, count);
					break;
				default:
					break;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	@WebServlet("/GetGoodsInCart") 
	public static class GetGoodsInCart extends HttpServlet {
		protected void doGet(HttpServletRequest request, HttpServletResponse response) {
			response.setContentType("text/json; charset=utf-8");
			JSONObject answer = new JSONObject();
			DataModule.setConnection();
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			try {
				
				if (request.getSession().getAttribute("userID") != null && (Integer) request.getSession().getAttribute("roleID") == 1) { 

					ResultSet query = DataModule.CART.getUserCart( (Integer)request.getSession().getAttribute("userID") );
					
					response.getWriter().println( DataModule.resToJSON(query) );	
					
				} else {		
					answer.put("message", "Access denied");
					answer.put("code", "403");
					System.out.println(answer);
					response.getWriter().println(answer);				
				}
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			System.out.println("test getGoodsInCart");
		}		
		
	}
	
	
	@WebServlet("/removeGoodsFromCart") 
	public static class removeGoodsFromCart extends HttpServlet {
		protected void doGet(HttpServletRequest request, HttpServletResponse response) {
			response.setContentType("text/json; charset=utf-8");
			JSONObject answer = new JSONObject();
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			Integer cartID = null;
			try {			
			cartID = new Integer ( request.getParameter("idCart") );
			
			if (cartID != null) {
				DataModule.CART.delete(cartID);
				answer.put("delStatus", true);				
				response.getWriter().println(answer);
			} 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		

		}
	}
	
	@WebServlet("/PayCart") 
	public static class PayCart extends HttpServlet {
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
			response.setContentType("text/json; charset=utf-8");
			JSONObject answer = new JSONObject();
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
			Integer userID = (Integer) request.getSession().getAttribute("userID");		
			Integer count =  DataModule.CART.toPay(userID);
			answer.put("countPay", count);
			response.getWriter().println(answer);
			
		}
	}
}
