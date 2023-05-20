<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/department.css" />
<div id="department_editDiv">
	<form id="departmentEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">部门编号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="department_departmentNo_edit" name="department.departmentNo" value="<%=request.getParameter("departmentNo") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">部门名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="department_departmentName_edit" name="department.departmentName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">部门职责:</span>
			<span class="inputControl">
				<textarea id="department_zhizhe_edit" name="department.zhizhe" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div>
			<span class="label">成立日期:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="department_bornDate_edit" name="department.bornDate" />

			</span>

		</div>
		<div class="operation">
			<a id="departmentModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/Department/js/department_modify.js"></script> 
