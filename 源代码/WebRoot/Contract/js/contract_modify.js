$(function () {
	//实例化编辑器
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	UE.delEditor('contract_content_edit');
	var contract_content_edit = UE.getEditor('contract_content_edit'); //合同内容编辑器
	contract_content_edit.addListener("ready", function () {
		 // editor准备好之后才可以使用 
		 ajaxModifyQuery();
	}); 
  function ajaxModifyQuery() {	
	$.ajax({
		url : "Contract/" + $("#contract_contractNo_edit").val() + "/update",
		type : "get",
		data : {
			//contractNo : $("#contract_contractNo_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (contract, response, status) {
			$.messager.progress("close");
			if (contract) { 
				$("#contract_contractNo_edit").val(contract.contractNo);
				$("#contract_contractNo_edit").validatebox({
					required : true,
					missingMessage : "请输入合同编号",
					editable: false
				});
				$("#contract_employeeObj_employeeNo_edit").combobox({
					url:"Employee/listAll",
					valueField:"employeeNo",
					textField:"name",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#contract_employeeObj_employeeNo_edit").combobox("select", contract.employeeObjPri);
						//var data = $("#contract_employeeObj_employeeNo_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#contract_employeeObj_employeeNo_edit").combobox("select", data[0].employeeNo);
						//}
					}
				});
				$("#contract_htlx_edit").val(contract.htlx);
				$("#contract_htlx_edit").validatebox({
					required : true,
					missingMessage : "请输入合同类型",
				});
				$("#contract_startTime_edit").datetimebox({
					value: contract.startTime,
					required: true,
					showSeconds: true,
				});
				$("#contract_endTime_edit").datetimebox({
					value: contract.endTime,
					required: true,
					showSeconds: true,
				});
				contract_content_edit.setContent(contract.content);
				$("#contract_htwj").val(contract.htwj);
				if(contract.htwj == "") $("#contract_htwjA").html("暂无文件");
				$("#contract_htwjA").attr("href", contract.htwj);
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

  }

	$("#contractModifyButton").click(function(){ 
		if ($("#contractEditForm").form("validate")) {
			$("#contractEditForm").form({
			    url:"Contract/" +  $("#contract_contractNo_edit").val() + "/update",
			    onSubmit: function(){
					if($("#contractEditForm").form("validate"))  {
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
                	var obj = jQuery.parseJSON(data);
                    if(obj.success){
                        $.messager.alert("消息","信息修改成功！");
                        $(".messager-window").css("z-index",10000);
                        //location.href="frontlist";
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    } 
			    }
			});
			//提交表单
			$("#contractEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
