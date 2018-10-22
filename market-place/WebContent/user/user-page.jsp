<%@page import="logIn.updateTextOnIndex"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Интернет-магазин</title>
<link rel="stylesheet" type="text/css" href="IndexStyle.css" >
<link rel="stylesheet" type="text/css" href="goods-items.css" >
<link rel="stylesheet" type="text/css" href="cart-style.css" >
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.js">	</script>
<script src="../JS/users-page-scripts.js" type="text/javascript"> </script>	
		
</head>
<body>
	<head>
	<div id = "logIn">
			<div class = "regInList" style = "float: right" >	
			<a href="../registration-form.jsp"> Зарегистрироваться</a>
			</div>
			<div style = "float: right; padding-right: 20px;">
			<a href="../logInForm.jsp"> 
					<%
 						out.println(updateTextOnIndex.getTextForLoginBtn(request));
 					%>
		    </a>
			</div>			
	</div>
	
	<div id = "headMarket" align="center">

	 <div   class = "headMarketText">
		<div   id = "marketTitle" class = "head-btn" >
		 Интернет - Магазин 
		</div>		
		
		<div  id = "exit" class = "head-btn">	Выход  </div>

		
	  </div>
	</div>
	

	<div class = "content">
	
	</div>
	
	<div class = "bottom-panel">
	
	</div>

	
	
	</head>
	
</body>
</html>