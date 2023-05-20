<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/contract.css" />
<div id="contractAddDiv">
	<form id="contractAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">合同编号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="contract_contractNo" name="contract.contractNo" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">合同员工:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="contract_employeeObj_employeeNo" name="contract.employeeObj.employeeNo" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">合同类型:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="contract_htlx" name="contract.htlx" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">合同开始时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="contract_startTime" name="contract.startTime" />

			</span>

		</div>
		<div>
			<span class="label">合同结束时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="contract_endTime" name="contract.endTime" />

			</span>

		</div>
		<div>
			<span class="label">合同内容:</span>
			<span class="inputControl">
				<script name="contract.content" id="contract_content" type="text/plain"   style="width:750px;height:500px;"></script>
			</span>

		</div>
		<div>
			<span class="label">合同文件:</span>
			<span class="inputControl">
				<input id="htwjFile" name="htwjFile" type="file" size="50" />
			</span>
		</div>
		<div class="operation">
			<a id="contractAddButton" class="easyui-linkbutton">添加</a>
			<a id="contractClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/Contract/js/contract_add.js"></script> 
