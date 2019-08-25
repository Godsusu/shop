$(function(){
    $(".search-product").click(function(){
    	$(".product-info").empty();
        $.getJSON("product?method=viewProduct",function(data){	
            $.each(data["list"],function(index,obj){
                var tr=$("<tr>")
                .append($("<td>").text(obj.productId))
                .append($("<td>").text(obj.name))
                .append($("<td>").text(obj.price))
                .append($("<td>").text(obj.onlineDate))
                .append($("<td>").text(obj.descInfo))
                .append($("<td>").html('<img width="200px" height="150px" src="http://localhost:8080/JDShop/upload/'+obj.picurl+'"/>'));
                
                
                var $operationtd=$("<td>");
                var $operationdel=$("<a>");
                var $operationupd=$("<a>");
                $operationdel.text("删除");
                $operationdel.attr("href","javascript:void(0)");
                $operationdel.attr("onclick","deleteProduct(this)");
                $operationtd.append($operationdel);
                $operationupd.text("修改");
                $operationupd.attr("href","javascript:void(0)");
                $operationupd.attr("onclick","updateProduct(this)");
                $operationtd.append($operationupd);
                
                tr.append($operationtd);
                
                $(".product-info").append(tr);
            })
        })
    });
    $(".search-product").click();
})

function deleteProduct(a){
	var productid=$(a).parent().parent().find("td:first").text();
	$.ajax({
		url:"product?method=deleteProduct&productid="+productid,
		datatype:"text",
		type:"post",
		async:false,
		success:function(data){
			alert(data);
			$(".search-product").click();
		}
	})
}

function updateProduct(a){   //update传入数据
	var productid=$(a).parent().parent().find("td:first").text();
	var productName=$(a).parent().parent().find("td").eq(1).text();
	var productPrice=$(a).parent().parent().find("td").eq(2).text();
	var productDate=$(a).parent().parent().find("td").eq(3).text();
	var productDesc=$(a).parent().parent().find("td").eq(4).text();
	var productPic=$(a).parent().parent().find("td").eq(5).text();
	
	$(".udproductid").val(productid);
	$(".udproductname").val(productName);
	$(".udproductprice").val(productPrice);
	$(".udproductdate").val(productDate);
	$(".udproductdesc").val(productDesc);
	$(".udproductpic").val(productPic);
	
	$(".bg").fadeIn(200);
	$(".update-product-info").fadeIn(200);
	$(".submit-product-info").attr("onclick","updataInfo('updateProductInfo')");
}

function updataInfo(method){    //updata和add提交数据
	$(".bg").fadeOut(200);
	$(".update-product-info").fadeOut(200);
	$(".add-product-info").fadeOut(200);
	
	if(method=="addProductInfo"){
		data=$(".new-product-info").serialize();
	}else if(method=="updateProductInfo"){
		data=$(".update-product-form").serialize();
	}
	
	$.ajax({
		url:"product?method="+method,
		datatype:"text",
		type:"POST",
		data:data,
		success:function(data){
			alert(data);
			$(".search-product").click();
		}
	})
}

function cancel(){
	$(".bg").fadeOut(200);
	$(".update-product-info").fadeOut(200);
	$(".add-product-info").fadeOut(200);
}

$(function(){                //添加用户按钮
	$(".add-product").click(function(){
		$(".bg").fadeIn(200);
		$(".add-product-info").fadeIn(200);
		//$(".submit-product-info").attr("onclick","updataInfo('addProductInfo')");
	})
})

  


