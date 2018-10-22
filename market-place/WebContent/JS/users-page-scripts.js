/**
 * 
 */

var idG;
var countG;

$(document).ready(function(){
	
	$("#exit").click( function() {
		closeSession();
	});
	
    $(window).on( "load", function() {
        console.log( "window loaded" );
        showContent();
    });
    
    $('#marketTitle').click (function() {
		location = "../index.jsp";
	});
    
    $('body').delegate('.plus', 'click', function() {
    	var goodsid;
    		goodsid = this.id;    	
    	var countGoods = $('.count-value-'+goodsid).val();
    		console.log("countGoods: " + countGoods);
    		countGoods++;
    	if (countGoods > 99) countGoods = 99;
    		addToCart (goodsid, countGoods);
    	$('.count-value-'+goodsid).val(countGoods);
    });
    
    $('body').delegate('.minus', 'click', function() {
    	var goodsid;
    		goodsid = this.id;
    	var countGoods = $('.count-value-'+goodsid).val();
    		console.log("countGoods: " + countGoods);
    		countGoods--;
    	if (countGoods < 1) countGoods = 1;
    		addToCart (goodsid, countGoods);
    	$('.count-value-'+goodsid).val(countGoods);
    });
        
    $('body').on('input change', '.numeric', function(e) {
    	console.log("testtete");

        var re = /^\d+\.?(\d{1,2})?$/ig,
            cVal = $.trim($(this).val());    
        if(!re.test(cVal)){
            $(this).val(cVal.substr(0,cVal.length-1));
        }
        	console.log("test change: " + $(this).val())
        	var changNumb = $(this).val();
	        if (changNumb < 1 || isNaN(changNumb)) {
	        	$(this).val(1)
	        }
	        //function addToCart (goodsid, count)
	        var idG = this.id;
	        var countG = $(this).val();
	                
	        $('body').on('change', '.numeric', function(e) {
	        	 console.log("id: " + idG);
	  	        console.log("count: " + countG);
	        	addToCart(idG, countG);
	        });	   
	        
    });
    
    $('body').delegate ('.del', 'click', function() {
		
    	console.log("delete element in cart. ID record: " + this.id);
    	blockDelElem(this.id);
    	deleteItemsFromCart (this.id);
    	updateElementsAfterDeleting();
	});
    
    $('body').delegate ('.solve', 'click', function() {
	
    	console.log('test solve');
    	toPay();
	});
    
    
    
	
});

function toPay () {
	var url = "../PayCart";  
    
    $.ajax({
        url: url,
        method: "GET",
        contentType: "application/json; charset=utf-8",
        //dataType: 'json',
        error: function(message) {
            console.log(message);
        },
        success: function(data) {
            console.log(data);
            window.location.reload();
        }
    });  
}

function deleteItemsFromCart (idCart) {
    var url = "../removeGoodsFromCart";  
    var obj = {
    		"idCart" : idCart
    }
    
    $.ajax({
        url: url,
        data : obj,
        method: "GET",
        contentType: "application/json; charset=utf-8",
        //dataType: 'json',
        error: function(message) {
            console.log(message);
        },
        success: function(data) {
            console.log(data);
        }
    });  
}

function updateElementsAfterDeleting () {
	var url = "../GetGoodsInCart";

	var sumText = "Сумма: ";
    
	$.ajax ({
		url : url,
		method : "GET",
		dataType: 'json',  
   		error: function(message) {
	           console.log(message);
	    },
	    success: function(data) {
	        	console.log(data);
	        	
	        	var itemsCount = data.items.length;	        	

	        	if (itemsCount == 0) {
	        		cartIsempty ();
	        	} else {		        	

		        	console.log(data.items[itemsCount-1].TOTALSUMM);
		        	$('#sum-money').text(data.items[itemsCount-1].TOTALSUMM + " RUB");
		        	$('#count-total').text(data.items[itemsCount-1].TOTALCOUNTCART + "  шт.");
	        	}
	         }   
	});
}

function closeSession () {
        
        var url = "../CloseSessionServlet";  
       
        $.ajax({
            url: url,
            method: "POST",
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
}

function cartIsempty () {
		$('.content').html ('');
		$('.content').html('<div class = "goods-cart-empty-info box-item"> Ваша корзина пуста.. </div>');	
}

function showContent () {
	
	var url = "../GetGoodsInCart";

    
	$.ajax ({
		url : url,
		method : "GET",
		dataType: 'json',  
   		error: function(message) {
	           console.log(message);
	    },
	    success: function(data) {
	        	console.log(data);
	        	
	        	var itemsCount = data.items.length;	        	

	        	if (itemsCount == 0) {
	        		cartIsempty ();
	        	} else {
		        	for (var i = 0; i <=itemsCount - 1; i++) {
		        		createContent( i + 1,
		        				data.items[i].GOODSID,
		        				data.items[i].NAME,
		        				data.items[i].COUNT,
		        				data.items[i].SUMOFPOSITION,
		        				data.items[i].INFO,
		        				data.items[i].LOGO,
		        				data.items[i].ID);
		        	}
		        	createBottomSum();
		        	console.log(data.items[itemsCount-1].TOTALSUMM);
		        	$('#sum-money').text(data.items[itemsCount-1].TOTALSUMM + " RUB");
		        	$('#count-total').text(data.items[itemsCount-1].TOTALCOUNTCART + "  шт.");
	        	}
	        	

	        }   
	});
}




function updateContentInCart (goodsid) {
	
	var url = "../GetGoodsInCart";

	var sumText = "Сумма: ";
    
	$.ajax ({
		url : url,
		method : "GET",
		dataType: 'json',  
   		error: function(message) {
	           console.log(message);
	    },
	    success: function(data) {
	        	console.log(data);
	        	
	        	var itemsCount = data.items.length;	        	

	        	if (itemsCount == 0) {
	        		cartIsempty ();
	        	} else {		        	
	        		var id = $(".gp-" + goodsid).attr("id");
	        		$(".gp-" + goodsid).text(sumText + data.items[id-1].SUMOFPOSITION);	
		        	console.log(data.items[itemsCount-1].TOTALSUMM);
		        	$('#sum-money').text(data.items[itemsCount-1].TOTALSUMM + " RUB");
		        	$('#count-total').text(data.items[itemsCount-1].TOTALCOUNTCART + "  шт.");
	        	}
	         }   
	});
}



function addToCart (goodsid, count) {
	
	var defaultCount = 1;
	var url = "../CartAdd";
	var obj = {
		"goods-id": goodsid,
		"count" : count,
		"locationUpdate" : "cart"
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
        	
        	if (data.code == -1){
        		showInvalidateCount(data.count, goodsid);
        		console.log($('.count-value-'+goodsid).val(data.count));
        	}
        	updateContentInCart(goodsid);
	    }
	
	});
}

function blockDelElem (id) {
	console.log("blockDelElem: " + id);
	console.log("blockDelElem: " + $('.del-'+id));

	$('#item-'+id).css({"pointer-events" : "none"});
	$('#item-'+id).css({'background' : "border-box"});
}

function showInvalidateCount(count, goodsID){
	$('#invalid-count-'+goodsID).text("Доступно: " + count);
	$('#invalid-count-'+goodsID).css({display:'block'})
}



function createContent (iterator, goodID, goodsName, goodsCount, goodsPrice, goodsInfo, logo, cartID) {
	//'+ goodsLogoUrC +'
	
	var tamplateCount = '<span class = "count-goods-line">' + 
				'<button type="button" class="minus button-count" id =' + goodID + '>-</button>' +
				'<input type="text" data-link="quantity" autocomplete="off" maxlength="2" id = "'+goodID+'" class="count-value-' +goodID+' in_tb numeric ignore">' +
				'<button type="button" class="plus button-count" id =' + goodID + ' >+</button>'+
				'<div class = "invalid-count" id = "invalid-count-' + goodID + '"> invalid-count</div>' +
				'</span>';
	
	var tamplateItems = 
		'<div class = "goods-img-div"> ' + iterator +'  </div>' +
		'<div class = "goods-img-div"> <img class = "goods-logo" src = "../images/' +logo + '"> </div>' +
		'<div id = "group-box"> <div class = "goods-name box-item" > ' + goodsName + '</div>' +
		'<div class = "goods-count box-item box-item">Количество: ' + tamplateCount +'</div>' +
		'<div id = '+iterator+' class = "gp-' + goodID +' goods-price box-item box-item">Сумма: ' + goodsPrice + '</div>' +
		'<div class = "goods-delete box-item unused" >  </div> </div> </div>' +
		'<div class = "goods-info box-item">' +goodsInfo + '</div>' + 
		'<span id = '+cartID+' class = "box-item goods-box-span close del del-'+cartID+'"></span>';	
		
		$(".content").append('<div class = "del-'+cartID+' content-item-' + goodID + ' content-item" id = item-' + cartID + '></div>');	
		$('.content-item-' + goodID + '').html(tamplateItems);
		$('.count-value-'+goodID).val(goodsCount);
		
}

function createBottomSum(cP) {
	      	   
     	$(".content").append('<div class = "basket-footer" >   </div>');
     	$(".basket-footer").append('<div class = "basket-footer-down" >   </div>');
 
     	$('.basket-footer-down').append('<div class = "sum-box-items " id = "sum-box" style = "	padding-right : 10px ; "></div>');
     	$('.sum-box-items').append('<div class = "sum-money sum-box-item" id = "sum-money">   12 000 руб</div>');
     	$('.sum-box-items').append('<div class = "sum-text sum-box-item" id = "sum-text">Итого: </div>');
     	
     	$('.basket-footer-down').append('<div class = "count-box-items " id = "count-box" style = "float: left; padding-right : 10px ; "></div>');
     	$('.count-box-items').append('<div class = "sum-money sum-box-item" id = "count-total">   12 </div>');
     	$('.count-box-items').append('<div class = "sum-text sum-box-item" id = "sum-text">Количество: </div>');   	
     	
     	$('.basket-footer-down').append('<input class="solve"  type="button" value="Оплатить" >');
        
}