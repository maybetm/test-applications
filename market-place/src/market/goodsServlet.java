package market;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;

import com.sun.xml.internal.ws.message.DataHandlerAttachment;

import DB.DataModule;

/**
 * Servlet implementation class goodsServlet
 */
@WebServlet("/goodsServlet")
@MultipartConfig
public class goodsServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html; charset=utf-8");
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		DataModule.setConnection();
		Integer goodsID = new Integer ( request.getParameter("goods-id"));
		System.out.println("[goodsServlet] goodsid: " + goodsID );

		
		Part filePart;
		HashMap<String, String> hashMap = new HashMap<String, String>();
		
		for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
		    String name = entry.getKey();
		    String value = entry.getValue()[0];			    
		    hashMap.put(name, value);
		}
		System.out.println("part logo: " + request.getPart("goods-logo"));
		System.out.println("[goodsServlet] hashMap: " + hashMap);
		
		filePart = request.getPart("goods-logo");
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();		
		String path =  request.getServletContext().getRealPath("/images/");
		System.out.println("fileName : " + fileName);
		//[goodsServlet] hashMap: {input-price=999,32 ?, input-count=9999, category-goods=3, info-goods=Ð­ÑÐ¾Ñ Ð¿ÑÐµÐºÑÐ°ÑÐ½ÑÐ¹ Ð³Ð¾ÑÑÐ¾Ðº Ð¿Ð¾Ð´Ð¾Ð¹Ð´ÑÑ Ð»ÑÐ±Ð¾Ð¹ Ð´Ð¾Ð¼Ð¾ÑÐ¾Ð·ÑÐ¹ÐºÐµ, ÑÐ°Ð¼Ð¾Ð¹ Ð»ÑÑÑÐµÐ¹ Ð¸ Ð¿ÑÐµÐºÑÐ°ÑÐ½Ð¾Ð¹. Ð Ð½ÑÐ¼ Ð¼Ð¾Ð¶Ð½Ð¾ ÑÐ°ÑÑÐ¸ÑÑ Ð¼Ð°ÑÐ¸ÑÑÐ°Ð½Ñ, Ð¿Ð¾Ð¼Ð¸Ð´Ð¾ÑÑ Ð¸ ÐºÐ°ÐºÑÑÑÑ Ð²ÑÑÐ°ÑÐ¸Ð²Ð°ÑÑ., goods-id=9, name-goods=Ð¦Ð²ÐµÑÐ¾ÑÐ½ÑÐ¹ Ð³Ð°ÑÑÐ¾Ðº}

		if (goodsID != null && goodsID == -1) {
			insert(request, response);
			response.sendRedirect("market/market-page.jsp");
		} else { 
			if (fileName.equals("")) {
				
				System.out.println("file not found  " + fileName);
				//Integer id, String name, String info, Integer marketID, Integer categoryID, Integer count, BigDecimal price
				DataModule.goods.update(new Integer (hashMap.get("goods-id")), hashMap.get("name-goods"), hashMap.get("info-goods"),
						(Integer) request.getSession().getAttribute("userID"), new Integer(hashMap.get("category-goods")),
						new Integer (hashMap.get("input-count")), new BigDecimal( hashMap.get("input-price")) );
				response.sendRedirect("market/market-page.jsp");
				 
			} else {
				fileName =  UUID.randomUUID().toString() + fileName;
				System.out.println("file is true");
				File file = new File(path + fileName);
				InputStream in = filePart.getInputStream();				
				System.out.println("absolute path: " + file.getAbsolutePath());				
				FileUtils.copyInputStreamToFile(in, file);
				//Integer id, String name, String info, Integer marketID, Integer categoryID, Integer count, BigDecimal price
				DataModule.goods.update(new Integer (hashMap.get("goods-id")), hashMap.get("name-goods"), hashMap.get("info-goods"),
						(Integer) request.getSession().getAttribute("userID"), new Integer(hashMap.get("category-goods")),
						new Integer (hashMap.get("input-count")), new BigDecimal( hashMap.get("input-price")), fileName );
				response.sendRedirect("market/market-page.jsp");
			}
			
		}
	}
	
	public static void insert (HttpServletRequest request, HttpServletResponse response) {
		try {

			Part filePart = request.getPart("goods-logo");
			String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();			
			System.out.println("fileName : " + fileName);
			
			InputStream in = filePart.getInputStream();			
			
			// нужен путь типа: ../images/nameFile.jpg
			
			String path =  request.getServletContext().getRealPath("/images/");
			
			System.out.println("path: " + path);
			fileName =  UUID.randomUUID().toString() + fileName;		
			File file = new File(path + fileName);
			
			System.out.println("absolute path: " + file.getAbsolutePath());
			
			FileUtils.copyInputStreamToFile(in, file);
			
			DataModule.goods.insert(request.getParameter("name-goods"),
					request.getParameter("info-goods"),
					(Integer) request.getSession().getAttribute("userID"),
					new Integer (request.getParameter("category-goods")), 
					new Integer (request.getParameter("input-count")),
					new BigDecimal(request.getParameter("input-price")),
					fileName); 
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
