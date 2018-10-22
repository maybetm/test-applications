package DB;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class DataModule {
	
	//  Database credentials
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/market-place-db";
    private static final String USER = "admin";    
    //connect устанавливается без пароля ????
    static final String PASS = "admin";

    public static Connection con = null;
    
    public static String getNowDateTime () {

    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	Date date = new java.util.Date();    	
    	return dateFormat.format(date); //2016/11/16 12:08:43
    }
	
    public static String md5Apache (String pas) {
    	
    	return DigestUtils.md5Hex(pas);
    	
    }
    
    public static PreparedStatement anyQueryPrepared;
    
	public static ResultSet anyQuery (String sql) throws SQLException {
		
		Statement query = con.createStatement();
		
		return query.executeQuery(sql);
	}
    
	
	public static Boolean setConnection () {
		 
		System.out.println("[setConnection] Testing connection to PostgreSQL JDBC");
	 
		if (con != null) { 
			System.out.println("[setConnection] PostgreSQL JDBC Driver successfully connected alredy exist.");
			return true;
		}
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("[setConnection] PostgreSQL JDBC Driver is not found. Include it in your library path ");
			e.printStackTrace();
		}
	 
		System.out.println("[setConnection] PostgreSQL JDBC Driver successfully connected");
		con = null;
	 
		try {
			con = DriverManager
			.getConnection(DB_URL, USER, PASS);
	 
		} catch (SQLException e) {
			System.out.println("[setConnection] Connection Failed");
			e.printStackTrace();
		}
	 
		if (con != null) {
			System.out.println("[setConnection] You successfully connected to database now");
			return true;
		} else {
			System.out.println("[setConnection] Failed to make connection to database");
			return false;
		}
		
	}
	
	
	
	public static JSONObject resToJSON (ResultSet query) {
		/*
		 * Метод преобразует ResultSEt в json
		 */
		
		JSONObject jsonResult = new JSONObject();
		List<String> columnNames = new ArrayList<String>();
		List <JSONObject> items = new ArrayList<JSONObject>();
		ResultSetMetaData rsMeta;
		try {
			rsMeta = query.getMetaData();
			Integer colCount = rsMeta.getColumnCount();
			
			for (int i = 1; i <= colCount; i++) {
				columnNames.add(rsMeta.getColumnName(i).toUpperCase());
			}
			System.out.println("getColumnNames: " + columnNames);
			
			while (query.next()) {
				JSONObject json = new JSONObject();
				
				for (int i = 1 ; i <= colCount; i ++) {
					String key = columnNames.get(i - 1);
					String value = query.getString(i);
					json.put(key, value);
				}
				items.add(json);
			}
			jsonResult.put("items", items);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("[datamodule] resToJSON: " + jsonResult);
		return jsonResult;
	}
	


	public static class Role {
		
		//market - 2
		//client/User - 1
		//adm - &&
		
		public static void createTable() throws SQLException {
			
			Statement query = con.createStatement();
			
			query.execute("CREATE TABLE ROLES (" +
					"id Integer UNIQUE, " +				
					"NameRole VARCHAR(10) UNIQUE"+
					")");
			System.out.println("[Role.createTable] time: " + getNowDateTime());
		}
		
	}
	
	public static class TimesZones {
		
		public static void createTable() throws SQLException {
			
			Statement query = con.createStatement();
			
			query.execute("CREATE TABLE timezones (" +
					"id SERIAL PRIMARY KEY, " +				
					"name text,"+
					"offsetutc text,"+
					"info text"+
					")");
			System.out.println("[TimesZones.createTable] time: " + getNowDateTime());
		}
	}
	

	
	public static class category {
		
		public static List<String> categoryList = new ArrayList<>(
				Arrays.asList("Одежда и обувь", "Аксессура и украшения"
				, "Дом и сад", "Авто-, мото", "Техника и электроника"
				, "Материалы для ремонта", "Товары для детей", "Красота и здоровье"
				, "Спорт и отдых", "Продукты питания", "Подарки, хобби, книги", "Инструмент"
				, "Другое"));
		
		public static void createTable() throws SQLException {
			Statement query = con.createStatement();
			
			query.execute("CREATE TABLE category (" +
					"id SERIAL PRIMARY KEY, " +
					"name VARCHAR(100)"+
					")");
			
			System.out.println("[category.createTable] time: " + getNowDateTime());
		}
		
		public static HashMap<Integer, String> getList () throws SQLException {
			
			
			HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
			
			ResultSet result = null;
						
			result = anyQuery("Select *from category");
			
						
			while(result.next()) {
				hashMap.put(result.getInt("id"), result.getString("name"));
			}
			
			System.out.println("list: " + hashMap);
			
			return hashMap;
						
		}
	
		public static void insert () throws SQLException {			
			Statement query = con.createStatement();
		
			StringBuffer sqlText = new StringBuffer("insert into category (name) " + 
					"values ");  
			
	        for (int i = 0; i <= categoryList.size() - 1; i++ ) {
	              if (i == categoryList.size() - 1) {
	                sqlText.append("\n ('"+ categoryList.get(i)+ "'); ");
	            } else {
	                sqlText.append("\n ('"+ categoryList.get(i)+ "'), ");
	            }

	        }			
			System.out.println("[category.insert] sqltext: " + sqlText);
			
			query.execute(sqlText.toString());			
		}
		
	}
	
	public static class goods {
		
		public static void createTable() throws SQLException {
			
			Statement query = con.createStatement();
			
			query.execute("CREATE TABLE goods (" +
					"id SERIAL PRIMARY KEY, " +
					"marketID Integer," +
					"name VARCHAR(35),"+
					"info TEXT,"+
					"categoryID Integer," +
					"count Integer,"+
					"price Numeric,"+
					"imageurl VARCHAR(350)"+
					")");
			System.out.println("[goods.createTable] time: " + getNowDateTime());
		}
		
		public static void insert (String name, String info, int marketID, int categoryID, int count, BigDecimal price, String logo) throws SQLException {
			
			PreparedStatement query =
					con.prepareStatement("INSERT into goods" + 
			"(marketID, name, info, categoryID, count, price, logo)" + 
			"values (?, ?, ?, ?, ?, ?, ?)");			
			query.setInt(1, marketID);	
			query.setString(2, name);
			query.setString(3, info);
			query.setInt(4, categoryID);
			query.setInt(5, count);
			query.setBigDecimal(6, price);
			query.setString(7, logo);			
			query.execute();			
			System.out.println("[goods.insert] add new goods, name: ["+name+"] " + "date: " +  getNowDateTime());
		}
		
		public static void delete (Integer id, Integer marketID) { 
			try {
				PreparedStatement query =
						con.prepareStatement("DELETE FROM goods\r\n" + 
								"WHERE id = ? and marketid = ?");
				query.setInt(1, id);
				query.setInt(2, marketID);
				query.execute();
				System.out.println("[goods.delete]  goods id: [" + id + "] is deleted. + date: " + getNowDateTime() );				
				CART.deleteByGoodsID (id);					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public static void update (Integer id, String name, String info, Integer marketID, Integer categoryID, Integer count, BigDecimal price, String logo) {
			PreparedStatement query;
			try {
				query = con.prepareStatement("UPDATE goods\r\n" +  
						"SET name = ?, info = ?, categoryID = ?, count = ?, price = ?, logo = ?" +
						"where id = ? and marketid= ?");
				query.setString(1, name);
				query.setString(2, info);
				query.setInt(3, categoryID);
				query.setInt(4, count);
				query.setBigDecimal(5, price);
				query.setString(6, logo);
				query.setInt(7, id);
				query.setInt(8, marketID);
				query.execute();	
				System.out.println("[goods.update] update  goods, name: ["+name+"] " + "date: " +  getNowDateTime());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				
			}			
					
			

		}
		
		public static void update (Integer id, String name, String info, Integer marketID, Integer categoryID, Integer count, BigDecimal price) {
			
			PreparedStatement query;
			try {
				query = con.prepareStatement("UPDATE goods\r\n" + 
						"SET name = ?, info = ?, categoryID = ?, count = ?, price= ?" + 
						"where id = ? and marketid= ?");
				query.setString(1, name);
				query.setString(2, info);
				query.setInt(3, categoryID);
				query.setInt(4, count);
				query.setBigDecimal(5, price);
				query.setInt(6, id);
				query.setInt(7, marketID);
				query.execute();	
				System.out.println("[goods.update] update  goods, name: ["+name+"] " + "date: " +  getNowDateTime());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
					
			
		}
		
	}
	
	public static class  USERS {
		
		public static void createTable() throws SQLException {
			
			Statement query = con.createStatement();
			
			query.execute("CREATE TABLE USERS (" +
					"id SERIAL PRIMARY KEY, " +
					"login VARCHAR(50), " +
					"email VARCHAR(255) UNIQUE, " +
					"password VARCHAR(45), " +
					"roleID INTEGER,"+
					"timeset text"+
					")");
			System.out.println("[USERS.createTable] time: " + getNowDateTime());
		};
		
		public static void insert (String login,  String mail, String pass, Integer roleID) throws SQLException {			
			PreparedStatement query = 
					con.prepareStatement("INSERT into users" + 
					"(login, Email, password, roleID)" + 
					"values (?, ?, ?, ?)");
			query.setString(1, login);
			query.setString(2, mail.toLowerCase());
			query.setString(3, md5Apache(pass));
			query.setInt(4, roleID);
			query.execute();		
			System.out.println("[USERS.insert] add new user, mail: ["+mail+"] " + "date: " +  getNowDateTime());
		}
		
		public static Boolean findUser (String mail) throws SQLException  {		
			
			PreparedStatement query = 
					con.prepareStatement("SELECT email FROM users " +
										"WHERE email = ?");
			query.setString(1, mail.toLowerCase());
			ResultSet resultSet = query.executeQuery();
				if (resultSet.next()) {					
					System.out.println("[USERS.findUser] resultSet > 0 " + true);
					return true;					
				} else {
					System.out.println("[USERS.findUser] resultSet > 0 " + false);
					return false;
				}						
		}
		
		public static ResultSet getResultSet (String mail) throws SQLException  {			
			
			PreparedStatement query;
			query = con.prepareStatement("SELECT count(*) as cR \r\n" +
										"FROM users " +
										"WHERE email = ?");
			query.setString(1, mail.toLowerCase());
			ResultSet resultSet = query.executeQuery();
			resultSet.next();
			Integer countRecord = resultSet.getInt("cr") ;
			System.out.println("[users].getResultSet.countRecord: " + countRecord);
			
			if (countRecord == 1) {
				
				query = con.prepareStatement("select *from users \r\n"
											+ "where email = ?");
				query.setString(1, mail);
				resultSet = query.executeQuery();
				return resultSet;
			} else {
				return null;
			}
			
			
						
		}
		
	public static void updateUTC (Integer userID, String utc) {
		
		PreparedStatement query;
		ResultSet resQ = null;
		Integer cUTC = null;
		
		System.out.println("[updateUTC] userID: " + userID);
		System.out.println("[updateUTC] UTC: "+ utc);
		try {
			query = con.prepareStatement("select count (*) as cUtc from timezones \r\n"
										+ "where offsetutc = ?");
			query.setString(1, utc);
			resQ = query.executeQuery();
			resQ.next();
			
			cUTC = resQ.getInt("cUtc");
			if (cUTC !=null && cUTC != 0) {
				
			query = con.prepareStatement("update users set timeset = ? \r\n"
										+ "where id = ?");
			query.setString(1, utc);
			query.setInt(2, userID);
			query.execute();
			System.out.println("[updateUTC] update UTC userID: " + userID + ", UTC: " + utc);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	}	
	public static class Statistics {
		
		public static void createTable () throws SQLException { 
			
			Statement query = con.createStatement();
			
			query.execute("CREATE TABLE Statistics (" +
					"id SERIAL PRIMARY KEY, " +
					"userName text, " +
					"gName text, " +
					"gCategory text, " +
					"gInfo text, " +
					"cCount Integer," +
					"gPrice Numeric," +
					"totalSumPosition Numeric," +
					"marketID Integer," +
					"dateToPay timestamp with time zone" +
					")");
			System.out.println("[Statistics.createTable] time: " + getNowDateTime());
		}
		
		public static ResultSet getStatisticsMarket (Integer marketID) {
			
			ResultSet resQuery = null;
			
			try {
				PreparedStatement query = 
						con.prepareStatement("select \r\n" + 
								"statistics.id, username, gname, gcategory, ccount, gprice, totalsumposition, marketid,  users.timeset,\r\n" + 
								"(select date_trunc('minute', datetopay::timestamptz at time zone users.timeset)) as datetopay\r\n" + 
								"from statistics, users\r\n" + 
								"where marketid = ? and users.id = marketid ");
				query.setInt(1, marketID);
				resQuery = query.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return resQuery;
		}
		
		public static ResultSet getStatisticsDateToPay (Integer marketID) {
			
			ResultSet resQuery = null;
			
			try {
				PreparedStatement query = 
						con.prepareStatement("select \r\n" + 
								"statistics.id, marketid,  users.timeset,\r\n" + 
								"(select date_trunc('minute', datetopay::timestamptz at time zone users.timeset)) as datetopay\r\n" + 
								"from statistics, users\r\n" + 
								"where marketid = ? and users.id = marketid ");
				query.setInt(1, marketID);
				resQuery = query.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return resQuery;
		}
		
	}
	
	public static class CART {
		
		public static void createTable () throws SQLException { 
			
			Statement query = con.createStatement();
			
			query.execute("CREATE TABLE CART (" +
					"id SERIAL PRIMARY KEY, " +
					"userID Integer, " +
					"goodsID Integer, " +
					"count Integer" +
					")");
			System.out.println("[CART.createTable] time: " + getNowDateTime());
		}
		
		public static ResultSet accessUpdate (Integer goodsid, Integer count) {
			
			ResultSet resQuery = null;
			
			try {
				PreparedStatement query = 
						DataModule.con.prepareStatement("select \r\n" + 
								"(select goods.count from goods where id = ?) as gdCount,\r\n" + 
								"(select (goods.count >= ?) from goods where id = ? ) as accessUp ");
				query.setInt(1, goodsid);
				query.setInt(2, count);
				query.setInt(3, goodsid);
				resQuery = query.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return resQuery;
		}
		
		public static ResultSet getGoodsinCart(Integer userID) {
			
			ResultSet resQuery = null;
			
			try {
				PreparedStatement query = con.prepareStatement("select \r\n" + 
						"(select count(*) from cart where userid = ?),\r\n" + 
						"	cart.goodsid \r\n" + 
						"from cart\r\n" + 
						"where userid = ?");
				query.setInt(1, userID);
				query.setInt(2, userID);
				resQuery = query.executeQuery();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return resQuery;
		}
		
		public static void insert (Integer userID, Integer goodsID, Integer count) {
			
			try {
				PreparedStatement query;
				query = con.prepareStatement("INSERT into cart" + 
				"(userID, goodsID, count)" + 
				"values (?, ?, ?)");
				query.setInt(1, userID);
				query.setInt(2, goodsID);
				query.setInt(3, count);
				query.execute();	
				
				System.out.println("[CART.insert] add position in cart userID: ["+userID+"] " + "date: " +  getNowDateTime());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		public static void update (Integer userID, Integer goodsID, Integer count) { 
			try {
				PreparedStatement query;
				query = con.prepareStatement("UPDATE cart \r\n" + 
											"SET count = count + ? \r\n" +
											"where userid = ? and goodsID = ?");
				query.setInt(1, count);
				query.setInt(2, userID);
				query.setInt(3, goodsID);
				query.execute();	
				
				System.out.println("[CART.update] update position in cart userID: ["+userID+"] " + "date: " +  getNowDateTime());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public static void updateOnChange (Integer userID, Integer goodsID, Integer count) { 
			try {
				PreparedStatement query;
				query = con.prepareStatement("UPDATE cart \r\n" + 
											"SET count = ? \r\n" +
											"where userid = ? and goodsID = ?");
				query.setInt(1, count);
				query.setInt(2, userID);
				query.setInt(3, goodsID);
				query.execute();	
				
				System.out.println("[CART.updateOnChange] update position in cart userID: ["+userID+"] " + "date: " +  getNowDateTime());
				System.out.println("[CART.updateOnChange] goodsID: ["+goodsID+"] " + "date: " +  getNowDateTime());
				System.out.println("[CART.updateOnChange] count: ["+count+"] " + "date: " +  getNowDateTime());
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		public static ResultSet getUserCart (Integer userID) { 
			try {
				PreparedStatement query;
				query = con.prepareStatement("SELECT\r\n" + 
						"cart.goodsid,\r\n" + 
						"goods.name,  \r\n" + 
						" goods.price, \r\n" + 
						"goods.logo,  \r\n" + 
						"goods.info, \r\n" + 
						"cart.count,  \r\n" + 
						"cart.id,  \r\n" + 
						"cart.userid, \r\n" + 
						"(select sum (cart.count) from cart, goods where cart.goodsid = goods.id and cart.userid = ?) as totalCountCart,\r\n" + 
						"(select sum(cart.count*goods.price) from cart, goods where cart.goodsid = goods.id and cart.userid = ?) as totalSumm, \r\n" + 
						"(cart.count * goods.price) as sumOfPosition\r\n" + 
						"FROM  \r\n" + 
						"cart, \r\n" + 
						"goods\r\n" + 
						"WHERE\r\n" + 
						"cart.goodsid = goods.id and\r\n" + 
						"cart.userid = ?");
				query.setInt(1, userID);
				query.setInt(2, userID);
				query.setInt(3, userID);
				ResultSet res = query.executeQuery();
				System.out.println("[CART.getUserCart] get cart userID: ["+userID+"] " + "date: " +  getNowDateTime());
				return res;					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			
		}
		
		public static void delete ( Integer cartID) {
			
			PreparedStatement query;
			System.out.print("[CART.delete] cartID: " + cartID);
			try {
				query = con.prepareStatement("delete from cart\r\n" + 
											"where id = ?");
				query.setInt(1, cartID);
				query.execute();
				System.out.print("[CART.delete] remove item from cart, id: " + cartID);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public static void deleteByGoodsID (Integer goodsID) {
			
			PreparedStatement query;

			try {
				query = con.prepareStatement("delete from cart\r\n" + 
											"where goodsid = ?");
				query.setInt(1, goodsID);
				query.execute();
				System.out.print("[CART.deleteByGoodsID] remove item from cart, goodsID: " + goodsID);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		public static Integer toPay (Integer userID) { 
			CallableStatement query;
			Integer recordCount = null;
			try {
				query = con.prepareCall("{CALL toPay(?)}");
				query.setInt(1, userID);
				
				ResultSet resQ = query.executeQuery();
				resQ.next();
				recordCount = resQ.getInt(1);
				System.out.println("[cart.toPay] user paid goods. userID: " + userID + " recordCount: " + recordCount);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return recordCount;
		}
		
		public static void createSQltoPay () {
			String sql = "CREATE OR REPLACE FUNCTION public.topay(usrid integer) RETURNS integer AS\r\n" + 
					"$BODY$ \r\n" + 
					"DECLARE\r\n" + 
					"	cartCount Integer;\r\n" + 
					" BEGIN    \r\n" + 
					" \r\n" + 
					" update goods set count = goods.count - data_table.count\r\n" + 
					"from\r\n" + 
					"(select unnest(array (SELECT  goods.id FROM   public.cart,  public.goods,  public.category,  public.users\r\n" + 
					"			WHERE \r\n" + 
					"			  cart.userid = usrid AND\r\n" + 
					"			  cart.goodsid = goods.id AND\r\n" + 
					"			  users.id = cart.userid AND \r\n" + 
					"			  category.id = goods.categoryid)) as id, \r\n" + 
					"\r\n" + 
					"        unnest(array(SELECT  cart.count FROM   public.cart,  public.goods,  public.category,  public.users\r\n" + 
					"			WHERE \r\n" + 
					"			  cart.userid = usrid AND\r\n" + 
					"			  cart.goodsid = goods.id AND\r\n" + 
					"			  users.id = cart.userid AND \r\n" + 
					"			  category.id = goods.categoryid)) as count) as data_table\r\n" + 
					"where goods.id = data_table.id;\r\n" + 
					"\r\n" + 
					"insert into statistics\r\n" + 
					"(username, gname, gcategory, ginfo, ccount, gprice,  totalsumposition, datetopay)\r\n" + 
					"SELECT \r\n" + 
					"  users.email, goods.name as gname, category.name as cName, goods.info as ginfo,\r\n" + 
					"  cart.count as cCount, goods.price as priceOne, (goods.price * cart.count) as sumItems, (select now() )\r\n" + 
					"  \r\n" + 
					"FROM \r\n" + 
					"  public.cart, public.goods,  public.category, public.users\r\n" + 
					"WHERE \r\n" + 
					"  cart.userid = usrid AND cart.goodsid = goods.id AND users.id = cart.userid AND category.id = goods.categoryid;\r\n" + 
					"\r\n" + 
					"  cartCount = (select count(*) from cart where userid = usrid); 		\r\n" + 
					"  return cartCount;\r\n" + 
					"END; $BODY$\r\n" + 
					"LANGUAGE plpgsql VOLATILE NOT LEAKPROOF\r\n" + 
					"COST 100;\r\n" + 
					"";
		}
		
	}
}

