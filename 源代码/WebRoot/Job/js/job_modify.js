$(function () {
	$.ajax({
		url : "Job/" + $("#job_jobId_edit").val() + "/update",
		type : "get",
		data : {
			//jobId : $("#job_jobId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (job, response, status) {
			$.messager.progress("close");
			if (job) { 
				$("#job_jobId_edit").val(job.jobId);
				$("#job_jobId_edit").validatebox({
					required : true,
					missingMessage : "请输入岗位id",
					editable: false
				});
				$("#job_departmentObj_departmentNo_edit").combobox({
					url:"Department/listAll",
					valueField:"departmentNo",
					textField:"departmentName",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#job_departmentObj_departmentNo_edit").combobox("select", job.departmentObjPri);
						//var data = $("#job_departmentObj_departmentNo_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#job_departmentObj_departmentNo_edit").combobox("select", data[0].departmentNo);
						//}
					}
				});
				$("#job_positionName_edit").val(job.positionName);
				$("#job_positionName_edit").validatebox({
					required : true,
					missingMessage : "请输入招聘岗位",
				});
				$("#job_personNum_edit").val(job.personNum);
				$("#job_personNum_edit").validatebox({
					required : true,
					validType : "integer",
					missingMessage : "请输入招聘人数",
					invalidMessage : "招聘人数输入不对",
				});
				$("#job_needSpecial_edit").val(job.needSpecial);
				$("#job_needSpecial_edit").validatebox({
					required : true,
					missingMessage : "请输入需求专业",
				});
				$("#job_xueli_edit").val(job.xueli);
				$("#job_xueli_edit").validatebox({
					required : true,
					missingMessage : "请输入学历",
				});
				$("#job_zpdx_edit").val(job.zpdx);
				$("#job_zpdx_edit").validatebox({
					required : true,
					missingMessage : "请输入招聘对象",
				});
				$("#job_jobMemo_edit").val(job.jobMemo);
				$("#job_addTime_edit").datetimebox({
					value: job.addTime,
					required: true,
					showSeconds: true,
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#jobModifyButton").click(function(){ 
		if ($("#jobEditForm").form("validate")) {
			$("#jobEditForm").form({
			    url:"Job/" +  $("#job_jobId_edit").val() + "/update",
			    onSubmit: function(){
					if($("#jobEditForm").form("validate"))  {
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
			$("#jobEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
