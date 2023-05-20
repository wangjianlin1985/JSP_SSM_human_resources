var ygfl_manage_tool = null; 
$(function () { 
	initYgflManageTool(); //建立Ygfl管理对象
	ygfl_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#ygfl_manage").datagrid({
		url : 'Ygfl/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "flId",
		sortOrder : "desc",
		toolbar : "#ygfl_manage_tool",
		columns : [[
			{
				field : "year",
				title : "福利年份",
				width : 140,
			},
			{
				field : "employeeObj",
				title : "福利员工",
				width : 140,
			},
			{
				field : "fljj",
				title : "福利奖金",
				width : 70,
			},
			{
				field : "sfff",
				title : "是否发放",
				width : 140,
			},
			{
				field : "ffrq",
				title : "发放日期",
				width : 140,
			},
		]],
	});

	$("#ygflEditDiv").dialog({
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
				if ($("#ygflEditForm").form("validate")) {
					//验证表单 
					if(!$("#ygflEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#ygflEditForm").form({
						    url:"Ygfl/" + $("#ygfl_flId_edit").val() + "/update",
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
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#ygflEditDiv").dialog("close");
			                        ygfl_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#ygflEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#ygflEditDiv").dialog("close");
				$("#ygflEditForm").form("reset"); 
			},
		}],
	});
});

function initYgflManageTool() {
	ygfl_manage_tool = {
		init: function() {
			$.ajax({
				url : "Employee/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#employeeObj_employeeNo_query").combobox({ 
					    valueField:"employeeNo",
					    textField:"name",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{employeeNo:"",name:"不限制"});
					$("#employeeObj_employeeNo_query").combobox("loadData",data); 
				}
			});
		},
		reload : function () {
			$("#ygfl_manage").datagrid("reload");
		},
		redo : function () {
			$("#ygfl_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#ygfl_manage").datagrid("options").queryParams;
			queryParams["year"] = $("#year").val();
			queryParams["employeeObj.employeeNo"] = $("#employeeObj_employeeNo_query").combobox("getValue");
			queryParams["sfff"] = $("#sfff").val();
			queryParams["ffrq"] = $("#ffrq").val();
			$("#ygfl_manage").datagrid("options").queryParams=queryParams; 
			$("#ygfl_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#ygflQueryForm").form({
			    url:"Ygfl/OutToExcel",
			});
			//提交表单
			$("#ygflQueryForm").submit();
		},
		remove : function () {
			var rows = $("#ygfl_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var flIds = [];
						for (var i = 0; i < rows.length; i ++) {
							flIds.push(rows[i].flId);
						}
						$.ajax({
							type : "POST",
							url : "Ygfl/deletes",
							data : {
								flIds : flIds.join(","),
							},
							beforeSend : function () {
								$("#ygfl_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#ygfl_manage").datagrid("loaded");
									$("#ygfl_manage").datagrid("load");
									$("#ygfl_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#ygfl_manage").datagrid("loaded");
									$("#ygfl_manage").datagrid("load");
									$("#ygfl_manage").datagrid("unselectAll");
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
			var rows = $("#ygfl_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "Ygfl/" + rows[0].flId +  "/update",
					type : "get",
					data : {
						//flId : rows[0].flId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (ygfl, response, status) {
						$.messager.progress("close");
						if (ygfl) { 
							$("#ygflEditDiv").dialog("open");
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
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}
