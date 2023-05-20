var job_manage_tool = null; 
$(function () { 
	initJobManageTool(); //建立Job管理对象
	job_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#job_manage").datagrid({
		url : 'Job/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "jobId",
		sortOrder : "desc",
		toolbar : "#job_manage_tool",
		columns : [[
			{
				field : "departmentObj",
				title : "招聘部门",
				width : 140,
			},
			{
				field : "positionName",
				title : "招聘岗位",
				width : 140,
			},
			{
				field : "personNum",
				title : "招聘人数",
				width : 70,
			},
			{
				field : "needSpecial",
				title : "需求专业",
				width : 140,
			},
			{
				field : "xueli",
				title : "学历",
				width : 140,
			},
			{
				field : "zpdx",
				title : "招聘对象",
				width : 140,
			},
			{
				field : "addTime",
				title : "发布时间",
				width : 140,
			},
		]],
	});

	$("#jobEditDiv").dialog({
		title : "修改管理",
		top: "50px",
		width : 700,
		height : 515,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#jobEditForm").form("validate")) {
					//验证表单 
					if(!$("#jobEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#jobEditForm").form({
						    url:"Job/" + $("#job_jobId_edit").val() + "/update",
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
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#jobEditDiv").dialog("close");
			                        job_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#jobEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#jobEditDiv").dialog("close");
				$("#jobEditForm").form("reset"); 
			},
		}],
	});
});

function initJobManageTool() {
	job_manage_tool = {
		init: function() {
			$.ajax({
				url : "Department/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#departmentObj_departmentNo_query").combobox({ 
					    valueField:"departmentNo",
					    textField:"departmentName",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{departmentNo:"",departmentName:"不限制"});
					$("#departmentObj_departmentNo_query").combobox("loadData",data); 
				}
			});
		},
		reload : function () {
			$("#job_manage").datagrid("reload");
		},
		redo : function () {
			$("#job_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#job_manage").datagrid("options").queryParams;
			queryParams["departmentObj.departmentNo"] = $("#departmentObj_departmentNo_query").combobox("getValue");
			queryParams["positionName"] = $("#positionName").val();
			queryParams["xueli"] = $("#xueli").val();
			queryParams["zpdx"] = $("#zpdx").val();
			queryParams["addTime"] = $("#addTime").datebox("getValue"); 
			$("#job_manage").datagrid("options").queryParams=queryParams; 
			$("#job_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#jobQueryForm").form({
			    url:"Job/OutToExcel",
			});
			//提交表单
			$("#jobQueryForm").submit();
		},
		remove : function () {
			var rows = $("#job_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var jobIds = [];
						for (var i = 0; i < rows.length; i ++) {
							jobIds.push(rows[i].jobId);
						}
						$.ajax({
							type : "POST",
							url : "Job/deletes",
							data : {
								jobIds : jobIds.join(","),
							},
							beforeSend : function () {
								$("#job_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#job_manage").datagrid("loaded");
									$("#job_manage").datagrid("load");
									$("#job_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#job_manage").datagrid("loaded");
									$("#job_manage").datagrid("load");
									$("#job_manage").datagrid("unselectAll");
									$.messager.alert("消息",data.message);
								}
							},
						});
					}
				});
			} else {
				$.messager.alert("提示", "请选择要删除的记录！", "info");
			}
		},
		edit : function () {
			var rows = $("#job_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "Job/" + rows[0].jobId +  "/update",
					type : "get",
					data : {
						//jobId : rows[0].jobId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (job, response, status) {
						$.messager.progress("close");
						if (job) { 
							$("#jobEditDiv").dialog("open");
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
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}
