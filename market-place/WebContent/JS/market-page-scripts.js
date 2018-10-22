/**
 * 
 */

var infotarget;

var postLimit = 20; // количество постов по умолчанию 
var offset = 0; // отступ в выборке записей
var pagePosition = 1;
var pathImg = null;
var statusEdit = false;
var goodsID; // флаг для редактирования

$(document).ready(function(){
	
    $(window).on( "load", function() {
        console.log( "window loaded" );
    	$('#goods').click(); 
    });
	
    $("#closeSession").click( function() {
    
    var url = "../CloseSessionServlet";  
    var code = "exit:[{\"code\": \"exit\"}]";
    
    $.ajax({
        url: url,
        method: "POST",
        data:  code,
        contentType: "application/json; charset=utf-8",
        //dataType: 'json',
        error: function(message) {
            console.log(message);
        },
        success: function(data) {
            console.log(data);
            location.href = "../index.jsp";
        }
    });
    	 
    });
    
    
    $('body').mousemove(function(e){
      if (infotarget !==e.target){ 
    	  infotarget = e.target.id;  
    	  //console.log(infotarget);
      	
      }
    });
    
    $("#input-file-btn").click (function() {		
    	$("#input-file").trigger('click');    	
	});
    
    $('.price').on('input', function(){
        var re = /^\d+\.?(\d{1,2})?$/ig,
            cVal = $.trim($(this).val());    
        $(this).val(cVal.replace(/,/g, '.'));
        if(!re.test(cVal)){
            $(this).val(cVal.substr(0,cVal.length-1));
        }
    });
    
    $(".insert-goods-form").submit(function(ev) {
        ev.preventDefault(); // to stop the form from submitting
        
       var elm = $(".input-field");
       var status = false;
       var divError = document.getElementById("error");
 

       for (var i = 0; i <= elm.length - 1; i++) {
    	   console.log ("i =" + i + "| " + elm[i].value);
    	   if (elm[i].value != "") {    		   
    		   status = true;
    	   } else {
    		   var el = elm[i].id.toString();
    		   if (el == "input-file" && statusEdit ) {
    			   status = true;
    		   } else {
    			   console.log ("element by break: " + elm[i].id);
    			   status = false;
        		   break;
    		   }
    		   
    	   }    	   
       }
       console.log ("status: " + status);
      
       if (status) {
            //this.submit(); // If all the validations succeeded
    	   // Если statusEdit = true, значит мы находимся в режиме редактирования
    	   //sendForm - отправка формы
    	   this.submit();
    		   divError.style.display = "none";    		   
    	   }  else { 
    		   divError.style.display = "block";
    	   }
    	  
    });	
    
    $("#goods").click (function() {
        var url = "../ContentManager";
        var Obj = {
        	    "offset": pagePosition.toString()
        	};
    		$.ajax({
    			url: url,
    			method: "GET",
    			data : Obj,
    			//contentType: "application/json; charset=utf-8",
    			dataType: 'json',  
       			error: function(message) {
    	            console.log(message);
    	        },
    	        success: function(data) {
    	           console.log(data);
    	            	         	
    	       	   $('.content').css({  height : '220px' });
    	           $('.content').html ('');
    	           $('.content').html('<div id = "head-content-insert">  Добавить товар </div>');
    	           countPages = data.countPages.countPages;
    	           
    	           for (var i = 0; i <= data.items.length - 1; i++) {    	        	  
    	        	   lastGoodsID = data.items[i].GOODSID;
    	        	   createContent(
    	        				data.items[i].GOODSID,
    	        				data.items[i].GOODSNAME,
    	        				data.items[i].COUNT,
    	        				data.items[i].PRICE,
    	        				data.items[i].INFO,
    	        				data.items[i].CATEGORYNAME,
    	        				data.items[i].LOGO);    	        	  
    	        	}       
    	           
    	           if ( countPages > 1 ) {  	        	   
    	        	  $(".content").append('<div class = "botton-content-navigator" id = "botton-content-nav">   </div>');
    	           }
    	           $('.content').height('auto');
    	           createBottomNavigator(countPages);  
    	           
    	        }  
    		});  
    		document.body.scrollTop = document.documentElement.scrollTop = 0;
	});
    
    $('body').delegate("#head-content-insert", "click", function (event) {
    	var elm = $(".input-field");
    	statusEdit = false;
    	insertmodal ();  
    	document.getElementById("goods-form").reset();
        $("#goods-id-hidden").val("-1");
		$(".div-input-submit").val("Добавить товар");
    	
    });
    
    $('body').delegate('.page-id', "click", function() {		
		offset = this.id;
		pagePosition = this.id;
		console.log("offset : " + offset);
		$('#goods').click(); 
	});
    
    $('body').delegate('.goods-edit ', 'click', function() {
		var id = this.id;
		$("#goods-id-hidden").val(id);
		console.log ("hidden field: " + $("#goods-id-hidden").val() );
		statusEdit = true;
		console.log("id = " + id);
		openUpdateGoodsModal (id);
		$(".div-input-submit").val("Сохранить");
		$("#input-file").val("");
    });
    
    
    
    $('body').delegate('.goods-delete', 'click', function() {
		var id = this.id;
		console.log("id = " + id);
		$('#item-'+id).css({"pointer-events" : "none"});
		$('#item-'+id).css({"background" : "border-box"});
		
		var Obj = {
        	    "goodsID": id.toString()
        	};
		var url = "../DeleteGoods";
		$.ajax({
			url: url,
			method: "POST",
			data : Obj,
			//contentType: "application/json; charset=utf-8",
			dataType: 'json',  
   			error: function(message) {
	            console.log(message);
	        },
	        success: function(data) {
	           console.log(data);

	        }
	
		}); $('#goods').click(); // можно отключить, чтобы выдавать модальное окно
		
	});
    
    $('body').on('input change', '.div-input-text', function(e) {
    	    console.log("3213123");
        	limit(this, 50);
     });
    
    $('#statistics').click(function() {
    	console.log("statistics");
    	getStatistic();
	});
    

    $('body').delegate('.time-menu', "click", function() {	
    	
    	console.log("test time thead");
    });
    
    $('body').on('input change', '#tzList', function(e) {

        var currentSelectVal = $(this).val();
        updateUTC(currentSelectVal);
        
    });
   
});

function getStatistic () { 
	var url = "../GetStatistics";
	
	$.ajax({
		url: url,
		method: "GET",
		//contentType: "application/json; charset=utf-8",
		dataType: 'json',  
			error: function(message) {
            console.log(message);
        },
        success: function(data) {
        	console.log(data);
        	
        	createTableStat();
        
        	for (i = 1; i < data.items.length ; i ++) {
        		insertTableStat(i,
        				data.items[i].USERNAME,
        				data.items[i].GNAME,
        				data.items[i].CCOUNT,
        				data.items[i].GPRICE,
        				data.items[i].TOTALSUMPOSITION,
        				data.items[i].DATETOPAY,
        				data.items[i].ID);
        				//"2018-10-20 17:49:13" );
        	}
        	
        	for (i = 1; i < data.timezones.items.length; i ++) {
        		insertTzList(data.timezones.items[i].OFFSETUTC,
        					 data.timezones.items[i].NAME);
        	}
        	
        	$("#tzList").val(data.items[1].TIMESET);
        	
        //	insertTableStat(3, "sergey2907@mail.com", "123123123", 99, "12388888.00", "3688889.00", "2018-10-20 17:49:13" );
        //(id, mail, name, count, price, totalSum, datePay)
        	

        }

	});
	
}


function limit(obj, size){
	console.log(obj.value.length);
	if (obj.value.length > size) {
		obj.value = obj.value.substring(0, size);
		}
	}

function createTableStat (){
    $('.content').html('');
    var table = $('<table></table>').addClass('statistics');
    var thead = $('<thead>' + 
            '<tr>' +
            '<th>№</th>'+
            '<th>email покупателя</th>'+
            '<th>Имя товара</th>'+
            '<th>Кол-во</th>'+
            '<th>Стоимость</th>'+
            '<th>Итого</th>'+
            '<th class = "time-menu">Дата покупки</th>'+
            '</tr>'+
    		'</thead>').addClass('stHead');  
    
    var tHeadInfo = $('<thead>' +
    				 '<tr>' +
    				 '<th>Всего продано(шт.): </th>' +
    				 '<td id = "totalCount">?? </td>' +
    				 '<th> Проданно на сумму(руб.): </th>' +
    				 '<tr id = "totalsum"> ??</tr>'+
    				 '</tr>' +
    				 '</thead>');
    
    var theadInfoDiv = $('<div class = "headinfo">' +
    					'<div class = "tzLabel">Выберите часовой пояс: </div>' +
    					'<div class = "select-box"> <select id = "tzList"> </select> </div>' +
    				   '</div>')
    				   
    /*	 $.each (data, function (key, val) {	        	 
	        	 console.log(key, val);
	        	 $("#category-googs").append ( $("<option value=\"" + key + "\">" + 
	        			val + "</option>") );
	         }) */
    
    var tBody = $('<tbody></tbody>').addClass('stBody');
    $('.stHead').append('<tr></tr>');
    
     $('.content').append(theadInfoDiv);	 
     $('.content').append(table);
     $('.statistics').append(thead);
     $('.statistics').append(tBody);
     
     
     $('.time-menu').append('<div class = "drop-menu" style = "block : none"></div');
}



function insertTableStat (id, mail, name, count, price, totalSum, datePay, stID) {
	
	var table = $('.stBody');
	
	
	table.append( '<tr>'+
					'<td>'+ id +'</td>' +
					'<td>'+ mail +'</td>' +
					'<td>'+ name +'</td>' +
					'<td>'+ count +'</td>' +
					'<td>'+ price +'</td>' +
					'<td>'+ totalSum +'</td>' +
					'<td id = time-'+id+'>'+ datePay +'</td>' +
			      '</tr>' );
	
}

function updateTime(tdID, tm) {
	$('#time-'+tdID).html(tm);
}

function insertTzList (offset, label) {
	
	$("#tzList").append ( $("<option value=\""+ offset + "\">" + label +"</option>")); 
}

function createContentStatic (gIterator, email, gName, gCategory, gCount, gPrice, totSum, cDate) {
	var tamplateItems = 
		'<div class = "gIterator static-item " >'+ gIterator +'</div>' +
		'<div class = "gUserEmail static-item " > '+ email +'</div>' +
		'<div class = "gName static-item " > '+ gName +'</div>' +
		'<div class = "gCategory static-item " > '+ gCategory +'</div>' +
		'<div class = "gCount static-item " > '+ gCount +'</div>' +
		'<div class = "gPrice static-item " > '+ gPrice +'</div>' +	
		'<div class = "totalSum static-item " > '+ totSum +'</div>' +
		'<div class = "cDate static-item " > '+ cDate +'</div>';	
	$('.content').append ('<div class = "static-items item-'+gIterator+'" ></div>');
	$('.item-'+gIterator).html(tamplateItems);
    
}

function createContent (goodID, goodsName, goodsCount, goodsPrice, goodsInfo, goodsCategory, logo) {
	//'+ goodsLogoUrC +'
	var tamplateItems = 
		'<div class = "goods-img-div"> <img class = "goods-logo" src = "../images/' +logo + '"> </div>' +
		'<div id = "group-box"> <div class = "goods-name box-item" > ' + goodsName + '</div>' +
		'<div class = "goods-category box-item box-item">' + goodsCategory + '</div>' +
		'<div class = "goods-count box-item box-item">Количество: ' + goodsCount + '</div>' +
		'<div class = "goods-price box-item box-item">Цена: ' + goodsPrice + '</div>' +
		'<div class = "goods-delete-edit box-item" id =' + goodID + ' ><div class = "goods-edit box-item" id =' + goodID + ' > Редактировать </div>' +
		'<div class = "goods-delete box-item" id =' + goodID + '> Удалить </div> </div> </div>' +
		'<div class = "goods-info box-item">' +goodsInfo + '</div>';		
	
		$(".content").append('<div class = "content-item-' + goodID + ' content-item" id = item-' + goodID + '></div>');	
		$('.content-item-' + goodID + '').html(tamplateItems);
}

function createBottomNavigator(cP) {	
	
	for (var i = 1; i <=cP; i++) {
		$('#botton-content-nav').append('<b class = "page-id"  id = "' + i +'">' + i + '</b>');
	}	
}

function updateUTC (utcValue) {
	
    var url = "../UpdateUTC";
    var obj = {
    		utc : utcValue
    }
    console.log("test update utc: " + utcValue);
    
		$.ajax({
			url: url,
			data: obj,
			method: "GET",    		
			dataType: 'json',
			error: function(message) {
	            console.log(message);
	        },
	        success: function(data) {
	            console.log(data);
	    
	            for (i = 1; i < data.items.length; i++) {
	            	updateTime(i,  data.items[i].DATETOPAY)
	            }   
	        }
	           
		});
}

function insertmodal () {
	
	var ovl = document.getElementById("overlay-insert-container");
    var url = "../getCategory";
    	
		$.ajax({
			url: url,
			method: "POST",    		
			dataType: 'json',
			error: function(message) {
	            console.log(message);
	        },
	        success: function(data) {
	            console.log(data);
	            
	         $("#category-googs").empty();
	         $.each (data, function (key, val) {	        	 
	        	 console.log(key, val);
	        	 $("#category-googs").append ( $("<option value=\"" + key + "\">" + 
	        			val + "</option>") );
	         })
	        }
	           
		});
		ovl.style.display = "block";
		document.body.style.overflow = 'hidden';

}

function updateGoods () {
	
	var obj = {
			"id-goods" : goodsID,
			"name-goods": $("#goods-name").val(),
			"info-goods":  $("#info-goods").val(),
			"category-goods": $("#category-googs").val(),
			"input-count":    $("#goods-count").val(),
			"input-price":  $("#input-price").val(),
			"goods-logo": $("#input-file").val(),
	}
	
	var url = "../UpdateGoods";
	
	console.log ("updateGoods obj: ");
	console.log (obj);
	
	$.ajax({
		url: url,
		data: obj,
		method: "POST",    		
		dataType: 'json',
		error: function(message) {
            console.log(message);
        },
        success: function(data) {
        	console.log(data);
        }                   
     })

	
}

function openUpdateGoodsModal (idGoods) {
	
	var ovl = document.getElementById("overlay-insert-container");
    var url = "../getCategory";
	var obj = {
    	    "goodsID": idGoods.toString()
    	};
		$.ajax({
			url: url,
			data: obj,
			method: "POST",    		
			dataType: 'json',
			error: function(message) {
	            console.log(message);
	        },
	        success: function(data) {
	            console.log(data);
	            
	         $("#category-googs").empty();
	         $.each (data.category, function (key, val) {	        	 
	        	 console.log(key, val);
	        	 $("#category-googs").append ( $("<option value=\"" + key + "\">" + 
		        			val + "</option>") );
	         })
	         
	         $("#goods-name").val(data.goods.NAME);
	         $("#info-goods").val(data.goods.INFO);
	         $("#goods-count").val(data.goods.COUNT);
	         $("#input-price").val(data.goods.PRICE);
	         $("#category-googs").val(data.goods.CATEGORYID);
	         pathImg = data.goods.LOGO;

	        }        
	           
		});
		ovl.style.display = "block";
		document.body.style.overflow = 'hidden';

} 



function closeinsertmodal () {
	var ovl = document.getElementById("overlay-insert-container");
	var error = document.getElementById("error");
        if (infotarget == "overlay-insert-container") {
    		ovl.style.display = "none";
    		error.style.display = "none";
    		document.body.style.overflow = 'auto';
        }
		

}