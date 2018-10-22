<%@page import="logIn.updateTextOnIndex"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Интернет-магазин</title>
<link rel="stylesheet" type="text/css" href="market-page-style.css" >
<link rel="stylesheet" type="text/css" href="overlay-insert-container.css" >
<link rel="stylesheet" type="text/css" href="goods-items.css" >
<link rel="stylesheet" type="text/css" href="statistics-style.css" >
	<script src="http://code.jquery.com/jquery-3.2.1.js"></script>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.js">	</script>
	<script src="../JS/market-page-scripts.js" type="text/javascript"> </script>	
	


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
		<div   id = "marketTitle" >
		<a class = "inHead" href="../index.jsp" > Интернет - Магазин </a>
		</div>		
		<div  Class = "head-market" id = "statistics"> <span class = "btn"> Статистика </span>  </div>
		<div  Class = "head-market" id = "goods" >  <span class = "btn"> Товары </span>   </div>
		<div  Class = "head-market" id = "closeSession"> <span class = "btn" > Выход </span>  </div>
		
	  </div>
	</div>
	
	
	</head>
	<div class = "content">
	
	</div>
	
	<div class = "bottom-panel">              	</div>
	
	
	<div id="overlay-insert-container"  onclick = "closeinsertmodal ()">
		<div id = "cont" >
		<div id = "logo" onclick = "closeinsertmodal ()"> Интернет - Магазин </div>
		<form class = "insert-goods-form" id = "goods-form"  method = "POST" action = "../goodsServlet" enctype="multipart/form-data"> 
		<div class = "line-border">  </div>
			<div class = "div-input"> Наименование товара <br>
			<input class = "div-input-text input-field" id = "goods-name" type = "text" maxlength="67" name = "name-goods" placeholder="Шкаф"> </div>
			<div class = "div-input" id = "goods-info"> Описание товара <br>
			<textarea  class = "input-field" id = "info-goods" name = "info-goods" > </textarea> </div>
			
			<div class="raz">
  				<div><span  >Выберите категорию</span> </div>
  				<div><select class = "input-field"  id = "category-googs" name = "category-goods">
  				</select>
				</div>
  			<div><span >Количество</span>	
  			</div>
  			<div> <input class = "input-field"  style = "width: 100%;" type="number" step="1" min="1" max="10000000"  id="goods-count" name="input-count" 
			oninput="this.value = this.value.replace(/\D/g, '')"/>
			</div>
 				 <div><span >цена</span>  </div>
  				 <div><input id = "input-price" name="input-price" type="text" class="price input-field" placeholder="12345.67">
			</div>
			
			<div>
			 <span> Выберите изображение для вашего товара </span>
			</div>
			
			<div>
				<input id = "input-file-btn"  type = "button" value = "загрузить файл">
			</div>
			
			</div>			
			
			<div class = "div-input" id = "error" style = "padding-bottom: 10px;"> 			
				 Введено не достаточно информации
			</div>
			
			<div class = "div-input" style = "padding-bottom: 10px;"> 			
			<input style = "width: 100%;" class = "div-input-submit" name = "reg" type = "submit" value = "Добавить товар"> 
			</div>				
			<input id = "goods-id-hidden" name="goods-id" type="text" class="hidden" >
			<input class = "input-field" id = "input-file" name = "goods-logo" type = "file" accept="image/*" /> 
				
		</form>
	</div>
	</div>

</body>
</html>