var contract_manage_tool = null; 
$(function () { 
	initContractManageTool(); //建立Contract管理对象
	contract_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#contract_manage").datagrid({
		url : 'Contract/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "contractNo",
		sortOrder : "desc",
		toolbar : "#contract_manage_tool",
		columns : [[
			{
				field : "contractNo",
				title : "合同编号",
				width : 140,
			},
			{
				field : "employeeObj",
				title : "合同员工",
				width : 140,
			},
			{
				field : "htlx",
				title : "合同类型",
				width : 140,
			},
			{
				field : "startTime",
				title : "合同开始时间",
				width : 140,
			},
			{
				field : "endTime",
				title : "合同结束时间",
				width : 140,
			},
			{
				field : "htwj",
				title : "合同文件",
				width : "350px",
				formatter: function(val,row) {
 					if(val == "") return "暂无文件";
					return "<a href='" + val + "' target='_blank' style='color:red;'>" + val + "</a>";
				}
 			},
		]],
	});

	$("#contractEditDiv").dialog({
		title : "修改管理",
		top: "10px",
		width : 1000,
		height : 600,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#contractEditForm").form("validate")) {
					//验证表单 
					if(!$("#contractEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#contractEditForm").form({
						    url:"Contract/" + $("#contract_contractNo_edit").val() + "/update",
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
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#contractEditDiv").dialog("close");
			                        contract_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#contractEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#contractEditDiv").dialog("close");
				$("#contractEditForm").form("reset"); 
			},
		}],
	});
});

function initContractManageTool() {
	contract_manage_tool = {
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
			$("#contract_manage").datagrid("reload");
		},
		redo : function () {
			$("#contract_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#contract_manage").datagrid("options").queryParams;
			queryParams["contractNo"] = $("#contractNo").val();
			queryParams["employeeObj.employeeNo"] = $("#employeeObj_employeeNo_query").combobox("getValue");
			queryParams["htlx"] = $("#htlx").val();
			queryParams["startTime"] = $("#startTime").datebox("getValue"); 
			queryParams["endTime"] = $("#endTime").datebox("getValue"); 
			$("#contract_manage").datagrid("options").queryParams=queryParams; 
			$("#contract_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#contractQueryForm").form({
			    url:"Contract/OutToExcel",
			});
			//提交表单
			$("#contractQueryForm").submit();
		},
		remove : function () {
			var rows = $("#contract_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var contractNos = [];
						for (var i = 0; i < rows.length; i ++) {
							contractNos.push(rows[i].contractNo);
						}
						$.ajax({
							type : "POST",
							url : "Contract/deletes",
							data : {
								contractNos : contractNos.join(","),
							},
							beforeSend : function () {
								$("#contract_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#contract_manage").datagrid("loaded");
									$("#contract_manage").datagrid("load");
									$("#contract_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#contract_manage").datagrid("loaded");
									$("#contract_manage").datagrid("load");
									$("#contract_manage").datagrid("unselectAll");
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
			var rows = $("#contract_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "Contract/" + rows[0].contractNo +  "/update",
					type : "get",
					data : {
						//contractNo : rows[0].contractNo,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (contract, response, status) {
						$.messager.progress("close");
						if (contract) { 
							$("#contractEditDiv").dialog("open");
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
							contract_content_editor.setContent(contract.content, false);
							$("#contract_htwj").val(contract.htwj);
							if(contract.htwj == "") $("#contract_htwjA").html("暂无文件");
							else $("#contract_htwjA").html(contract.htwj);
							$("#contract_htwjA").attr("href", contract.htwj);
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
