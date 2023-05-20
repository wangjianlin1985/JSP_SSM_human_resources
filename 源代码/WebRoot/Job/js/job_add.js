$(function () {
	$("#job_departmentObj_departmentNo").combobox({
	    url:'Department/listAll',
	    valueField: "departmentNo",
	    textField: "departmentName",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#job_departmentObj_departmentNo").combobox("getData"); 
            if (data.length > 0) {
                $("#job_departmentObj_departmentNo").combobox("select", data[0].departmentNo);
            }
        }
	});
	$("#job_positionName").validatebox({
		required : true, 
		missingMessage : '请输入招聘岗位',
	});

	$("#job_personNum").validatebox({
		required : true,
		validType : "integer",
		missingMessage : '请输入招聘人数',
		invalidMessage : '招聘人数输入不对',
	});

	$("#job_needSpecial").validatebox({
		required : true, 
		missingMessage : '请输入需求专业',
	});

	$("#job_xueli").validatebox({
		required : true, 
		missingMessage : '请输入学历',
	});

	$("#job_zpdx").validatebox({
		required : true, 
		missingMessage : '请输入招聘对象',
	});

	$("#job_addTime").datetimebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	//单击添加按钮
	$("#jobAddButton").click(function () {
		//验证表单 
		if(!$("#jobAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#jobAddForm").form({
			    url:"Job/add",
			    onSubmit: function(){
					if($("#jobAddForm").form("validate"))  { 
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
                        $("#jobAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#jobAddForm").submit();
		}
	});

	//单击清空按钮
	$("#jobClearButton").click(function () { 
		$("#jobAddForm").form("clear"); 
	});
});
