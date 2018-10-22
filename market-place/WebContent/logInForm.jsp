<%@page import="DB.DataModule"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Авторизация</title>
	<link rel="stylesheet" type="text/css" href="registration-form.css" >
</head>
<body>
		
	<div id = "container" >
		<div id = "logo"> <a class = "inHead" href="index.jsp" > Интернет - Магазин </a> </div>
		<form method = "POST" action = "ServletLogIN" > 
			<div class = "div-input"> Email <br>
			<input class = "div-input-text" type = "text" name = "mail" placeholder="alexandr.ivanov@gmail.com">  </div>
			<div class = "div-input"> Введите пароль <br>
			<input class = "div-input-text" type = "password" name = "password">  </div>					
			
			<div class = "div-input" style = "padding-bottom: 10px;"> 			
				<input class = "div-input-submit" type = "submit" value = "Войти"> 
			 </div>	
 	
		</form>
	</div>	
</body>
</html>