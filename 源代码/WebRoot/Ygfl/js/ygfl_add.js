$(function () {
	$("#ygfl_year").validatebox({
		required : true, 
		missingMessage : '请输入福利年份',
	});

	$("#ygfl_employeeObj_employeeNo").combobox({
	    url:'Employee/listAll',
	    valueField: "employeeNo",
	    textField: "name",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#ygfl_employeeObj_employeeNo").combobox("getData"); 
            if (data.length > 0) {
                $("#ygfl_employeeObj_employeeNo").combobox("select", data[0].employeeNo);
            }
        }
	});
	$("#ygfl_fljj").validatebox({
		required : true,
		validType : "number",
		missingMessage : '请输入福利奖金',
		invalidMessage : '福利奖金输入不对',
	});

	$("#ygfl_sfff").validatebox({
		required : true, 
		missingMessage : '请输入是否发放',
	});

	$("#ygfl_ffrq").validatebox({
		required : true, 
		missingMessage : '请输入发放日期',
	});

	//单击添加按钮
	$("#ygflAddButton").click(function () {
		//验证表单 
		if(!$("#ygflAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#ygflAddForm").form({
			    url:"Ygfl/add",
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
                        $.messager.alert("消息","保存成功！");
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
