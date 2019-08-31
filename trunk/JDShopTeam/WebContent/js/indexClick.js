$(function(){
	$(".fmaint").find("a").each(function(){
		$(this).click(function(){
			var categoryName=this.text;
			$.ajax({
				url:"product?method=selectCategoryID&categoryName="+categoryName,
				type:"post",
				dataType:"text",
				success:function(data){
					window.location.href="product/ProductSelect.html?id="+data;
				}
			})
		})
	})
})