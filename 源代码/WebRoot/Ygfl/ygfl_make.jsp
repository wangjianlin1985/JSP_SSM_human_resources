<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ygfl.css" />
<div id="ygflAddDiv">
	<form id="ygflAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">福利年份:</span>
			<span class="inputControl">
				<select id="year" name="year" style="width:150px;">
					<option value="2019">2019年</option>
					<option value="2020">2020年</option>
					<option value="2021">2021年</option>
				</select>
			</span>

		</div>
		
		<div class="operation">
			<a id="ygflAddButton" class="easyui-linkbutton">开始评比</a>
			<a id="ygflClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/Ygfl/js/ygfl_make.js"></script> 
