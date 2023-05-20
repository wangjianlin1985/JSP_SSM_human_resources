$(function () {
	//实例化编辑器
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	UE.delEditor('contract_content');
	var contract_content_editor = UE.getEditor('contract_content'); //合同内容编辑框
	$("#contract_contractNo").validatebox({
		required : true, 
		missingMessage : '请输入合同编号',
	});

	$("#contract_employeeObj_employeeNo").combobox({
	    url:'Employee/listAll',
	    valueField: "employeeNo",
	    textField: "name",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#contract_employeeObj_employeeNo").combobox("getData"); 
            if (data.length > 0) {
                $("#contract_employeeObj_employeeNo").combobox("select", data[0].employeeNo);
            }
        }
	});
	$("#contract_htlx").validatebox({
		required : true, 
		missingMessage : '请输入合同类型',
	});

	$("#contract_startTime").datetimebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	$("#contract_endTime").datetimebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	//单击添加按钮
	$("#contractAddButton").click(function () {
		if(contract_content_editor.getContent() == "") {
			alert("请输入合同内容");
			return;
		}
		//验证表单 
		if(!$("#contractAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#contractAddForm").form({
			    url:"Contract/add",
			    onSubmit: function(){
					if($("#contractAddForm").form("validate"))  { 
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
                        $("#contractAddForm").form("clear");
                        contract_content_editor.setContent("");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#contractAddForm").submit();
		}
	});

	//单击清空按钮
	$("#contractClearButton").click(function () { 
		$("#contractAddForm").form("clear"); 
	});
});
