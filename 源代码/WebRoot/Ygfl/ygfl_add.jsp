<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ygfl.css" />
<div id="ygflAddDiv">
	<form id="ygflAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">福利年份:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="ygfl_year" name="ygfl.year" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">福利员工:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="ygfl_employeeObj_employeeNo" name="ygfl.employeeObj.employeeNo" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">福利奖金:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="ygfl_fljj" name="ygfl.fljj" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">是否发放:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="ygfl_sfff" name="ygfl.sfff" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">发放日期:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="ygfl_ffrq" name="ygfl.ffrq" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="ygflAddButton" class="easyui-linkbutton">添加</a>
			<a id="ygflClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/Ygfl/js/ygfl_add.js"></script> 
