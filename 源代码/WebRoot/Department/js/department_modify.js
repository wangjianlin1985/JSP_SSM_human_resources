$(function () {
	$.ajax({
		url : "Department/" + $("#department_departmentNo_edit").val() + "/update",
		type : "get",
		data : {
			//departmentNo : $("#department_departmentNo_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (department, response, status) {
			$.messager.progress("close");
			if (department) { 
				$("#department_departmentNo_edit").val(department.departmentNo);
				$("#department_departmentNo_edit").validatebox({
					required : true,
					missingMessage : "请输入部门编号",
					editable: false
				});
				$("#department_departmentName_edit").val(department.departmentName);
				$("#department_departmentName_edit").validatebox({
					required : true,
					missingMessage : "请输入部门名称",
				});
				$("#department_zhizhe_edit").val(department.zhizhe);
				$("#department_zhizhe_edit").validatebox({
					required : true,
					missingMessage : "请输入部门职责",
				});
				$("#department_bornDate_edit").datebox({
					value: department.bornDate,
					required: true,
					showSeconds: true,
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#departmentModifyButton").click(function(){ 
		if ($("#departmentEditForm").form("validate")) {
			$("#departmentEditForm").form({
			    url:"Department/" +  $("#department_departmentNo_edit").val() + "/update",
			    onSubmit: function(){
					if($("#departmentEditForm").form("validate"))  {
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
			$("#departmentEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
