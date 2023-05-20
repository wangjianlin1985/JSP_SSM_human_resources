<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ygfl.css" />
<div id="ygfl_editDiv">
	<form id="ygflEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">记录id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="ygfl_flId_edit" name="ygfl.flId" value="<%=request.getParameter("flId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">福利年份:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="ygfl_year_edit" name="ygfl.year" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">福利员工:</span>
			<span class="inputControl">
				<input class="textbox"  id="ygfl_employeeObj_employeeNo_edit" name="ygfl.employeeObj.employeeNo" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">福利奖金:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="ygfl_fljj_edit" name="ygfl.fljj" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">是否发放:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="ygfl_sfff_edit" name="ygfl.sfff" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">发放日期:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="ygfl_ffrq_edit" name="ygfl.ffrq" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="ygflModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/Ygfl/js/ygfl_modify.js"></script> 
