$(function () {
	 

	//单击添加按钮
	$("#ygflAddButton").click(function () {
		//验证表单 
		if(!$("#ygflAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#ygflAddForm").form({
			    url:"Ygfl/make",
			    onSubmit: function(){
					if($("#ygflAddForm").form("validate"))  { 
	                	$.messager.progress({
							text : "正在提交数据中...",
						}); 
	                	return true;
	                } else {
	                    return false;
	                }
			    },
			    success:function(data){
			    	$.messager.progress("close");
                    //此处data={"Success":true}是字符串
                	var obj = jQuery.parseJSON(data); 
                    if(obj.success){ 
                        $.messager.alert("消息","福利评比结果生成成功！");
                        $(".messager-window").css("z-index",10000);
                        $("#ygflAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#ygflAddForm").submit();
		}
	});

	//单击清空按钮
	$("#ygflClearButton").click(function () { 
		$("#ygflAddForm").form("clear"); 
	});
});
