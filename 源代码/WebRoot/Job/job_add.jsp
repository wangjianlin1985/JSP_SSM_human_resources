<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/job.css" />
<div id="jobAddDiv">
	<form id="jobAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">招聘部门:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="job_departmentObj_departmentNo" name="job.departmentObj.departmentNo" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">招聘岗位:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="job_positionName" name="job.positionName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">招聘人数:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="job_personNum" name="job.personNum" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">需求专业:</span>
			<span class="inputControl">
				<textarea id="job_needSpecial" name="job.needSpecial" rows="6" cols="80"></textarea>

			</span>

		</div>
		<div>
			<span class="label">学历:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="job_xueli" name="job.xueli" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">招聘对象:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="job_zpdx" name="job.zpdx" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">招聘备注:</span>
			<span class="inputControl">
				<textarea id="job_jobMemo" name="job.jobMemo" rows="6" cols="80"></textarea>

			</span>

		</div>
		<div>
			<span class="label">发布时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="job_addTime" name="job.addTime" />

			</span>

		</div>
		<div class="operation">
			<a id="jobAddButton" class="easyui-linkbutton">添加</a>
			<a id="jobClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/Job/js/job_add.js"></script> 
