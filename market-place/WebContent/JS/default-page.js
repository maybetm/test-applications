/**
 * 
 */
var contextApp = "/market-place";
var postLimit = 20; // количество постов по умолчанию 
var offset = 1; // отступ в выборке записей
var pagePosition = 0;
var pageRoleID = 0; // 0 - Гость, 1 - пользователь, 2 - магазин
$(document).ready(function(){
	
	$("#exit").click( function() {
		closeSession();
	});
	
    $(window).on( "load", function() {
        console.log( "window loaded" );
        showContent ();
    });
    
    $('#marketTitle').click (function() {
		location = "index.jsp";
	});
	
    $('#marketCart').click (function () {
    	
    	location = contextApp+"/user/user-page.jsp";
    });
    
    $('body').delegate('.page-id', "click", function() {		
		offset = this.id;
		pagePosition = this.id;
		console.log("offset : " + pagePosition); 
    	   //$('.content').css({  height : '220px' });
           $('.content').html ('');
		showContent();
	});
    
    $('body').delegate('.add-to-cart', 'click', function() {
    	var goodsid;
    	goodsid = this.id;
    	console.log("goodsid: " + goodsid);
    	addToCart(goodsid);
    });
    
    
});

function addToCart (goodsid) {
	
	var defaultCount = 1;
	var url = contextApp+"/CartAdd";
	var obj = {
		"goods-id": goodsid,
		"count" : defaultCount.toString()
	}
	
	$.ajax ({
		url : url,
		method : "GET",
		data : obj,
		dataType: 'json',  
   		error: function(message) {
	           console.log(message);
	    },
	    success: function(data) {
        	console.log(data);
        
        	if (data.code == 403) {
        		location = "index.jsp";
        	}
        	
        	if (data.goodsid = goodsid) {
        		styleElementsAddtoCart (goodsid)
        	}
	    }
	
	});
}

function showContent () {
	
	var url = contextApp+"/ContentManager";
    var Obj = {
    	    "offset": pagePosition.toString(),
    	    "roleID": 1
    	};
    

    
	$.ajax ({
		url : url,
		method : "GET",
		data : Obj,
		dataType: 'json',  
   		error: function(message) {
	           console.log(message);
	    },
	    success: function(data) {
	        	console.log(data);
	        	
	        	countPages = data.countPages.countPages;
	        	pageRoleID = data.rolePageID;
	        	 createHeadNavigator(countPages);
	        	
		        for (var i = 0; i <= data.items.length - 1; i++) {
		        	//console.log(data.items[i]);
 	        	   	createContent(
	        				data.items[i].GOODSID,
	        				data.items[i].GOODSNAME,
	        				data.items[i].COUNT,
	        				data.items[i].PRICE,
	        				data.items[i].INFO,
	        				data.items[i].CATEGORYNAME,
	        				data.items[i].LOGO);
		        }
		        createBottomNavigator(countPages);
		        console.log(" pageRoleID: " +pageRoleID);
		        switch (pageRoleID) {
				case 0:
					hiddenFuncBtn()
					break;
				case 1: {
					var len = data.inCart.items.length;
					for (var i = 0; i <= len - 1; i++) {
						styleElementsAddtoCart(data.inCart.items[i].GOODSID);
					}
					showFuncBtn();
					
					
				}break;
				case 2:
					hiddenFuncBtn()
					break;					
					
				default:
					break;
				}
		        
	        }   
	});
}

function showFuncBtn () { 
	$('.userBTN').css({display : 'block'});
}

function hiddenFuncBtn () {
	console.log("hiddenFuncBtn");
	$('.userBTN').css({display: 'none'});
}

function styleElementsAddtoCart (goodsid) {
	var isAdded = "Товар добавлен в вашу корзину";
	$(".goods-add-cart#"+goodsid).text(isAdded);
	$(".goods-add-cart#"+goodsid).css({color: '#a55858'});
}

function createHeadNavigator(cP) {
	
	if ( cP > 1 ) {  	        	   
     	 $(".content").append('<div class = " head-content-navigator" id = "head-content-nav">   </div>');
   	}
        $('.content').height('auto');	
	

	var newdiv = document.createElement("div");	
	for (var i = 1; i <=cP; i++) {
		$('#head-content-nav').append('<b class = "page-id"  id = "' + i +'">' + i + '</b>');
	}	
}

function createBottomNavigator(cP) {
	
	if ( cP > 1 ) {  	        	   
     	 $(".content").append('<div class = "botton-content-navigator" id = "botton-content-nav">   </div>');
   	}
        $('.content').height('auto');	
	

	var newdiv = document.createElement("div");	
	for (var i = 1; i <=cP; i++) {
		$('#botton-content-nav').append('<b class = "page-id"  id = "' + i +'">' + i + '</b>');
	}	
}


function createContent (goodID, goodsName, goodsCount, goodsPrice, goodsInfo, goodsCategory, logo) {
	//'+ goodsLogoUrC +'
	var tamplateItems = 
		'<div class = "goods-img-div"> <img class = "goods-logo" src = "' + contextApp +'/images/' +logo + '"> </div>' +
		'<div id = "group-box"> <div class = "goods-name box-item" > ' + goodsName + '</div>' +
		'<div class = "goods-category box-item box-item">' + goodsCategory + '</div>' +
		'<div class = "goods-count box-item box-item">В наличии: ' + goodsCount + ' шт.</div>' +
		'<div class = "goods-price box-item box-item">Цена: ' + goodsPrice + ' Руб.</div>' +		
		'<div class = "goods-add-cart" id =' + goodID + ' ><div class = "add-to-cart box-item userBTN" id =' + goodID + ' > Добавить в корзину </div>' +
		'<div class = "goods-delete box-item unused" >  </div> </div> </div>' +
		'<div class = "goods-info box-item">' +goodsInfo + '</div>';		
	
	
		$(".content").append('<div class = "content-item-' + goodID + ' content-item" id = item-' + goodID + '></div>');	
		$('.content-item-' + goodID + '').html(tamplateItems);
}