<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Регистрация</title>
	<link rel="stylesheet" type="text/css" href="registration-form.css" >
</head>
<body>
		
	<div id = "container" >
		<div id = "logo"> <a class = "inHead" href="index.jsp" > Интернет - Магазин </a> </div>
		<form method = "POST" action = "ServletReg"> 
		<div class = "line-border">  </div>
			<div class = "div-input"> Как вас зовут? <br>
			<input class = "div-input-text" type = "text" name = "firstName" placeholder="Александр"> </div>
			<div class = "div-input"> Email <br>
			<input class = "div-input-text" type = "text" name = "mail" placeholder="alexandr.ivanov@gmail.com">  </div>
			<div class = "div-input"> Введите пароль <br>
			<input class = "div-input-text" type = "password" name = "password">  </div>					
			
			<div class = "div-input" style = "padding-bottom: 10px;"> 			
				<input class = "div-input-submit" name = "reg" type = "submit" value = "Зарегистрироваться"> 
			 </div>	
			<div class = "div-input"  Style = "padding-top: 0px;"> 			
				<input class = "div-input-submit" name = "regEnt" type = "submit" value = "Зарегистрироваться как компания "> 
			 </div>				 	
		</form>
	</div>

	
</body>
</html>