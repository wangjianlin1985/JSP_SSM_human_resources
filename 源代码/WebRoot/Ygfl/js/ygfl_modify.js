$(function () {
	$.ajax({
		url : "Ygfl/" + $("#ygfl_flId_edit").val() + "/update",
		type : "get",
		data : {
			//flId : $("#ygfl_flId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (ygfl, response, status) {
			$.messager.progress("close");
			if (ygfl) { 
				$("#ygfl_flId_edit").val(ygfl.flId);
				$("#ygfl_flId_edit").validatebox({
					required : true,
					missingMessage : "请输入记录id",
					editable: false
				});
				$("#ygfl_year_edit").val(ygfl.year);
				$("#ygfl_year_edit").validatebox({
					required : true,
					missingMessage : "请输入福利年份",
				});
				$("#ygfl_employeeObj_employeeNo_edit").combobox({
					url:"Employee/listAll",
					valueField:"employeeNo",
					textField:"name",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#ygfl_employeeObj_employeeNo_edit").combobox("select", ygfl.employeeObjPri);
						//var data = $("#ygfl_employeeObj_employeeNo_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#ygfl_employeeObj_employeeNo_edit").combobox("select", data[0].employeeNo);
						//}
					}
				});
				$("#ygfl_fljj_edit").val(ygfl.fljj);
				$("#ygfl_fljj_edit").validatebox({
					required : true,
					validType : "number",
					missingMessage : "请输入福利奖金",
					invalidMessage : "福利奖金输入不对",
				});
				$("#ygfl_sfff_edit").val(ygfl.sfff);
				$("#ygfl_sfff_edit").validatebox({
					required : true,
					missingMessage : "请输入是否发放",
				});
				$("#ygfl_ffrq_edit").val(ygfl.ffrq);
				$("#ygfl_ffrq_edit").validatebox({
					required : true,
					missingMessage : "请输入发放日期",
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#ygflModifyButton").click(function(){ 
		if ($("#ygflEditForm").form("validate")) {
			$("#ygflEditForm").form({
			    url:"Ygfl/" +  $("#ygfl_flId_edit").val() + "/update",
			    onSubmit: function(){
					if($("#ygflEditForm").form("validate"))  {
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
			$("#ygflEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
