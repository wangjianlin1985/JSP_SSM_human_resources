<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/job.css" />
<div id="job_editDiv">
	<form id="jobEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">岗位id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="job_jobId_edit" name="job.jobId" value="<%=request.getParameter("jobId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">招聘部门:</span>
			<span class="inputControl">
				<input class="textbox"  id="job_departmentObj_departmentNo_edit" name="job.departmentObj.departmentNo" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">招聘岗位:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="job_positionName_edit" name="job.positionName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">招聘人数:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="job_personNum_edit" name="job.personNum" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">需求专业:</span>
			<span class="inputControl">
				<textarea id="job_needSpecial_edit" name="job.needSpecial" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div>
			<span class="label">学历:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="job_xueli_edit" name="job.xueli" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">招聘对象:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="job_zpdx_edit" name="job.zpdx" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">招聘备注:</span>
			<span class="inputControl">
				<textarea id="job_jobMemo_edit" name="job.jobMemo" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div>
			<span class="label">发布时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="job_addTime_edit" name="job.addTime" />

			</span>

		</div>
		<div class="operation">
			<a id="jobModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/Job/js/job_modify.js"></script> 
