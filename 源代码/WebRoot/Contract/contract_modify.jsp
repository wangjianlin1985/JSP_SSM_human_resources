<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/contract.css" />
<div id="contract_editDiv">
	<form id="contractEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">合同编号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="contract_contractNo_edit" name="contract.contractNo" value="<%=request.getParameter("contractNo") %>" style="width:200px" />
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
				<script id="contract_content_edit" name="contract.content" type="text/plain"   style="width:750px;height:500px;"></script>

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
		<div class="operation">
			<a id="contractModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/Contract/js/contract_modify.js"></script> 
