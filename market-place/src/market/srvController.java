package market;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import DB.DataModule;
import javafx.scene.chart.PieChart.Data;
import sun.security.util.PropertyExpander;
import java.util.Map.Entry;

public class srvController {

	@WebServlet("/getCategory")
	public static class getCategory extends HttpServlet {
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) {
			
			JSONObject jsonObject = null;
			
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setContentType("text/html; charset=utf-8");
			
			System.out.println("getCategory opened");			

			Integer goodsID = null;
			
			try {
				
			goodsID = new Integer (request.getParameter("goodsID"));
			
			} catch (NumberFormatException e) {

				System.out.println("goodsID is null.");	    	
				DataModule.setConnection();
				try {
					jsonObject = new JSONObject(DataModule.category.getList());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					response.getWriter().println(jsonObject);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (goodsID != null) {		
				try {
					response.getWriter().println(getJsonEditGoods(goodsID, (Integer) request.getSession().getAttribute("userID")));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();					
				}
			} 
}

		
		public static JSONObject getJsonEditGoods (Integer goodsID, Integer userID ) {
			
			JSONObject jsonObject = null;
			ResultSet resultQuery = null;
			ResultSetMetaData rsMeta = null;
			List<String> columnNames = new ArrayList<String>();
			JSONObject jsonResult = new JSONObject();
			JSONObject jsonRecord = new JSONObject();
			
			DataModule.setConnection();
			PreparedStatement query;
			try {
				query = DataModule.con.prepareStatement("select *from goods\r\n" + 
										"where id = ? and marketid = ?");
				query.setInt(1, goodsID);
				query.setInt(2, userID);
				
				resultQuery = query.executeQuery();				
				rsMeta = resultQuery.getMetaData();				
		Integer columnCount = rsMeta.getColumnCount();
				
				for (int i = 1; i <= columnCount; i++) {
					columnNames.add(rsMeta.getColumnName(i).toUpperCase());
					}
				
				resultQuery.next();
				for (int i = 1; i <= columnCount; i++) {	
					
					String key = columnNames.get(i - 1);		
					String value = resultQuery.getString(i);
					jsonRecord.put(key, value);
				}				

				jsonObject = new JSONObject(DataModule.category.getList());
			
				jsonResult.put("goods", jsonRecord);
				jsonResult.put("category", jsonObject);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("[getCategory edit gods].jsonRecord : " +jsonResult);
			return jsonResult;
		}
}

	
	@WebServlet("/ContentManager")
	public static class ContentManager extends HttpServlet {		
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			System.out.println("test");
			ResultSet query;			
			response.setContentType("text/json; charset=utf-8");
			request.setCharacterEncoding("UTF-8");			
			
			DataModule.setConnection();
			
			 Integer pageNumber = new Integer (request.getParameter("offset"));   
			 System.out.println("pageNumber:  " + pageNumber);

			 if (pageNumber == 0 ) {				 
				 pageNumber = 1;
			 }
			 
			Integer role = null; 
			try {  
			role = (Integer) request.getSession().getAttribute("roleID"); 
			} catch (Exception e) {
				// TODO: handle exception
			}
			//если rolePageID = 1, то запрос пришёл с index.jsp
			Integer rolePageID = null;
			try {
				rolePageID = new Integer (request.getParameter("roleID"));
				if (role == null || rolePageID == 1) {
					role = 1;
				}
				
				System.out.println("role: " + role + " rolePageID: " + rolePageID);
			} catch (Exception e) {
				// TODO: handle exception
				rolePageID = null;
				System.out.println("Клиент не отправил roleID");
			}
			
			switch (role) {
			case 1:
				contentUsers(request, response, pageNumber, 0, role);
				break;
			case 2:
				contentMarket (request,  response, pageNumber);
				break;
				
			default:
				System.out.println("defaul content");
				break;
			}
		}
		
	public static void contentUsers (HttpServletRequest request, HttpServletResponse response, Integer pageNumber, Integer userID, Integer roleID) {
		System.out.println("users content");
		
try {				
			JSONObject jsonResult = new JSONObject();
			Integer offsetID = lastID(pageNumber, 20);
			
			PreparedStatement anyQueryPrepared = 
					DataModule.con.prepareStatement("SELECT" + 
							"  goods.categoryid," + 
							"  goods.id as goodsId, " + 
							"  goods.marketid, " + 
							"  goods.name as goodsName, " + 
							"  goods.info, " + 
							"  goods.count, " + 
							"  goods.price, " + 
							"  goods.logo, " + 
							"  category.name as categoryName  " + 
							"FROM \r\n" + 
							"  public.goods, \r\n" + 
							"  public.category\r\n" + 
							"WHERE \r\n" + 
							"  goods.categoryid = category.id and " + 
							"  goods.id > ?" + " and goods.count >= 1 " + 
							"Order by goodsId " + 
							"limit '20' ");							
					anyQueryPrepared.setInt(1, offsetID);
					System.out.println("offsetID = " +  offsetID);
					jsonResult = getJSonResult(anyQueryPrepared.executeQuery(), userID, roleID);
					
					Integer rolePageId = (Integer) request.getSession().getAttribute("roleID");
					
					if (rolePageId == null) { 
						rolePageId = 0;
					}
					
			jsonResult.put("rolePageID", rolePageId);
			
			Integer userIdSession = null;
			userIdSession = (Integer) request.getSession().getAttribute("userID");
			
			if (userIdSession != null) {
				JSONObject inCart = new JSONObject();
				inCart = DataModule.resToJSON(
										DataModule.CART.getGoodsinCart(userIdSession));
				System.out.println("inCart. " + inCart);
				Integer countRecord = inCart.getJSONArray("items").length();
				if (countRecord > 0) { 		
					jsonResult.put("inCart", inCart);
				}
			}
			
			
			
			response.getWriter().println(jsonResult);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	public static void contentMarket (HttpServletRequest request, HttpServletResponse response, Integer pageNumber) {
		try {				
			JSONObject jsonResult = new JSONObject();
			System.out.println("contentMarket, pagenumb: " + pageNumber);
			Integer offsetID = lastIDMarket(pageNumber, 20, (Integer) request.getSession().getAttribute("userID"));
			System.out.println("contentMarket, offsetID: " + offsetID);					
			PreparedStatement anyQueryPrepared = 
					DataModule.con.prepareStatement("SELECT" + 
							"  goods.categoryid," + 
							"  goods.id as goodsId, " + 
							"  goods.marketid, " + 
							"  goods.name as goodsName, " + 
							"  goods.info, " + 
							"  goods.count, " + 
							"  goods.price, " + 
							"  goods.logo, " + 
							"  category.name as categoryName  " + 
							"FROM \r\n" + 
							"  public.goods, \r\n" + 
							"  public.category\r\n" + 
							"WHERE \r\n" + 
							"  goods.categoryid = category.id and " + 
							"  goods.marketid = ? and " +
							"  goods.id > ?" + 
							"Order by goodsId " + 
							"limit '20' ");				
					anyQueryPrepared.setInt(1,(int) request.getSession().getAttribute("userID"));						
					anyQueryPrepared.setInt(2, offsetID);
					System.out.println("offsetID = " +  offsetID);
					jsonResult = getJSonResult(anyQueryPrepared.executeQuery(),
							(int) request.getSession().getAttribute("userID"), (Integer) request.getSession().getAttribute("roleID"));
					jsonResult.put("rolePageID", request.getSession().getAttribute("roleID"));
					
					
			response.getWriter().println(jsonResult);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Integer lastIDMarket (Integer pagenumber, Integer limit, Integer marketID) throws SQLException {
		
	pagenumber = pagenumber - 1;
	
	Integer countRecord = limit * pagenumber;
	
	if (countRecord <= 0 || countRecord == null) { 	
		System.out.println("[lastID] lastId returned '0'.");
		return 0;
	} else { 
		pagenumber = countRecord;
	}
	
	System.out.println("lastIDMarket.marketID = " + marketID);
	System.out.println("lastIDMarket.countRecord = " + countRecord);

	PreparedStatement queryGetLastID = DataModule.con.prepareStatement("SELECT * FROM (\r\n" + 
			"  SELECT\r\n" + 
			"    ROW_NUMBER() OVER (ORDER BY goods.id ASC) AS rownumber,\r\n" + 
			"    id\r\n" + 
			"  FROM goods WHERE goods.marketid = ? \r\n" + 
			") as goods\r\n" + 
			"WHERE rownumber = ?" +
			"order by id ");	
	queryGetLastID.setInt(1, marketID);
	queryGetLastID.setInt(2, pagenumber);
	ResultSet lastIDResultSet = queryGetLastID.executeQuery();
	
	lastIDResultSet.next();
	System.out.println("[lastID] lastId returned id: " + lastIDResultSet.getInt("id"));
	
	return lastIDResultSet.getInt("id");
}
	
	public static Integer lastID (Integer pagenumber, Integer limit) throws SQLException {
	
	pagenumber = pagenumber - 1;
	
	Integer countRecord = limit * pagenumber;
	
	if (countRecord <= 0 || countRecord == null) { 	
		System.out.println("[lastID] lastId returned '0'.");
		return 0;
	} else { 
		pagenumber = countRecord;
	}
	
	
	PreparedStatement queryGetLastID =
			DataModule.con.prepareStatement("SELECT * FROM (\r\n" + 
											"  SELECT\r\n" + 
											"  ROW_NUMBER() OVER (ORDER BY goods.id ASC) AS rownumber,\r\n" + 
											"    id\r\n" + 
											"  FROM goods where goods.count >= 1 \r\n" + 
											") as goods\r\n" + 
											"WHERE rownumber = ?" +
											"order by id ");
	queryGetLastID.setInt(1, pagenumber);
	
	ResultSet lastIDResultSet = queryGetLastID.executeQuery();
	
	lastIDResultSet.next();
	System.out.println("[lastID] lastId returned id: " + lastIDResultSet.getInt("id"));
	
	return lastIDResultSet.getInt("id");
}
				
	public static JSONObject getJSonResult (ResultSet query, Integer UserID, Integer roleID) throws SQLException {
			
			JSONObject jsonResult = new JSONObject();
	
			List <JSONObject> resultList = new ArrayList<JSONObject>();
			
			List<String> columnNames = new ArrayList<String>();
			
			ResultSetMetaData rsMeta = null;
			
			JSONObject countPage = new JSONObject();
			
			int columnCount = 0;
			
			switch (roleID) {
			case 1:{
				ResultSet queryCount = DataModule.anyQuery("select count (*) as countRecords From goods");
				queryCount.next();
				
				Integer a = queryCount.getInt("countRecords");
				Integer limit = 20;
				
				double countPages = (double) a / limit; 			
				
				System.out.println("countPages = " + (int) Math.ceil(countPages) + " count rec: " + queryCount.getInt("countRecords"));
				countPage.put("countPages",(int) Math.ceil(countPages) );			
			}

				break;
			case 2: {
				ResultSet queryCount = DataModule.anyQuery("select count (*) as countRecords From goods where marketid = \'" + UserID + "\'");
				queryCount.next();
				
				Integer a = queryCount.getInt("countRecords");
				Integer limit = 20;
				
				double countPages = (double) a / limit; 			
				
				System.out.println("countPages = " + (int) Math.ceil(countPages) + " count rec: " + queryCount.getInt("countRecords"));
				countPage.put("countPages",(int) Math.ceil(countPages) );
			}
				break;
				
			default:
				break;
			}
						
			try {
				rsMeta = query.getMetaData();
				columnCount = rsMeta.getColumnCount();
				
			for (int i = 1; i <= columnCount; i++) {
				columnNames.add(rsMeta.getColumnName(i).toUpperCase());
				}
			
			System.out.println("[getJSonResult].columnNames : " +columnNames);
			
			while  (query.next()) { 
				JSONObject response = new JSONObject();
				
				for (int i = 1; i <= columnCount; i++) {	
					
					String key = columnNames.get(i - 1);		
					String value = query.getString(i);
					response.put(key, value);
				}
				resultList.add(response);
				
				}			
			jsonResult.put("items", resultList);
			jsonResult.put("countPages", countPage);
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			
				query.close();
			}
			
			System.out.println("[getJSonResult].resultList: " + jsonResult);
			return jsonResult;
			
		}
	
		
	}
	
	@WebServlet("/DeleteGoods")
	public static class DeleteGoods extends HttpServlet {
	
		protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			response.setContentType("text/json; charset=utf-8");
			request.setCharacterEncoding("UTF-8");			
			
			Integer goodsID = new Integer (request.getParameter("goodsID"));   
			System.out.println("goodsID:  " + goodsID);
			
			DataModule.setConnection();
			DataModule.goods.delete(goodsID, (Integer) request.getSession().getAttribute("userID"));
		}
		
	}
	
	@WebServlet("/UpdateGoods")
	public static class UpdateGoods extends HttpServlet {
		
		protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setContentType("text/html; charset=utf-8");
			// [id-goods, name-goods, info-goods, category-goods, input-count, input-price, goods-logo]
			HashMap<String, String> hashMap = new HashMap<String, String>();
			
			for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			    String name = entry.getKey();
			    String value = entry.getValue()[0];			    
			    hashMap.put(name, value);
			}
			System.out.println("hashMap: " + hashMap);
			
			if (hashMap.get("goods-logo").equals("") || hashMap.get("goods-logo") == null ) { 
				//	Integer id, String name, String info, int marketID, int categoryID, int count, BigDecimal price
					DataModule.goods.update(new Integer (hashMap.get("id-goods")), hashMap.get("name-goods"),
							hashMap.get("info-goods"), (Integer) request.getSession().getAttribute("userID"), 
							new Integer (hashMap.get("category-goods")), new Integer (hashMap.get("input-count")) , new BigDecimal(hashMap.get("input-price")) );
					
				} else { 
					DataModule.goods.update(new Integer (hashMap.get("id-goods")), hashMap.get("name-goods"),
							hashMap.get("info-goods"), (Integer) request.getSession().getAttribute("userID"), 
							new Integer (hashMap.get("category-goods")), new Integer (hashMap.get("input-count")) , new BigDecimal(hashMap.get("input-price")) );
				}
			
		//	DataModule.goods.update(id, name, info, marketID, categoryID, count, price);
			
		}
	}
	
	@WebServlet("/GetStatistics")
	public static class GetStatistics extends HttpServlet {
		
		protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setContentType("text/json; charset=utf-8");
			
			Integer marketID = (Integer) request.getSession().getAttribute("userID");
			ResultSet resQuery =  DataModule.Statistics.getStatisticsMarket(marketID);
			JSONObject jsQueryResult = DataModule.resToJSON(resQuery);
			
			try {
			resQuery = DataModule.anyQuery("select timezones.name, timezones.offsetutc from timezones");
			JSONObject timezones = DataModule.resToJSON(resQuery);
			jsQueryResult.put("timezones", timezones);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			response.getWriter().println(jsQueryResult);
			
		}
	}
	
	@WebServlet("/UpdateUTC")
	public static class UpdateUTC extends HttpServlet {
		
		protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("UpdateUTC init");
			response.setContentType("text/json; charset=utf-8");		

			JSONObject answer = null;
			ResultSet resQ = null;
			Integer userID = (Integer) request.getSession().getAttribute("userID");
			String utc = request.getParameter("utc");
			
			resQ = DataModule.Statistics.getStatisticsDateToPay(userID);
			
			answer = DataModule.resToJSON(resQ);
			response.getWriter().println(answer);
			DataModule.USERS.updateUTC (userID, utc);
			
		}
	}
	
}
