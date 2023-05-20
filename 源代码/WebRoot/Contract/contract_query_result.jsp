<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/contract.css" /> 

<div id="contract_manage"></div>
<div id="contract_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="contract_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="contract_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="contract_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="contract_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="contract_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="contractQueryForm" method="post">
			合同编号：<input type="text" class="textbox" id="contractNo" name="contractNo" style="width:110px" />
			合同员工：<input class="textbox" type="text" id="employeeObj_employeeNo_query" name="employeeObj.employeeNo" style="width: auto"/>
			合同类型：<input type="text" class="textbox" id="htlx" name="htlx" style="width:110px" />
			合同开始时间：<input type="text" id="startTime" name="startTime" class="easyui-datebox" editable="false" style="width:100px">
			合同结束时间：<input type="text" id="endTime" name="endTime" class="easyui-datebox" editable="false" style="width:100px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="contract_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="contractEditDiv">
	<form id="contractEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">合同编号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="contract_contractNo_edit" name="contract.contractNo" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">合同员工:</span>
			<span class="inputControl">
				<input class="textbox"  id="contract_employeeObj_employeeNo_edit" name="contract.employeeObj.employeeNo" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">合同类型:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="contract_htlx_edit" name="contract.htlx" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">合同开始时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="contract_startTime_edit" name="contract.startTime" />

			</span>

		</div>
		<div>
			<span class="label">合同结束时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="contract_endTime_edit" name="contract.endTime" />

			</span>

		</div>
		<div>
			<span class="label">合同内容:</span>
			<span class="inputControl">
				<script name="contract.content" id="contract_content_edit" type="text/plain"   style="width:100%;height:500px;"></script>

			</span>

		</div>
		<div>
			<span class="label">合同文件:</span>
			<span class="inputControl">
				<a id="contract_htwjA" style="color:red;margin-bottom:5px;">查看</a>&nbsp;&nbsp;
    			<input type="hidden" id="contract_htwj" name="contract.htwj"/>
				<input id="htwjFile" name="htwjFile" value="重新选择文件" type="file" size="50" />
			</span>
		</div>
	</form>
</div>
<script>
//实例化编辑器
//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
var contract_content_editor = UE.getEditor('contract_content_edit'); //合同内容编辑器
</script>
<script type="text/javascript" src="Contract/js/contract_manage.js"></script> 
