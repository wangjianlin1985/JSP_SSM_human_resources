<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ygfl.css" /> 

<div id="ygfl_manage"></div>
<div id="ygfl_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="ygfl_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="ygfl_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="ygfl_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="ygfl_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="ygfl_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="ygflQueryForm" method="post">
			福利年份：<input type="text" class="textbox" id="year" name="year" style="width:110px" />
			福利员工：<input class="textbox" type="text" id="employeeObj_employeeNo_query" name="employeeObj.employeeNo" style="width: auto"/>
			是否发放：<input type="text" class="textbox" id="sfff" name="sfff" style="width:110px" />
			发放日期：<input type="text" class="textbox" id="ffrq" name="ffrq" style="width:110px" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ygfl_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="ygflEditDiv">
	<form id="ygflEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">记录id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="ygfl_flId_edit" name="ygfl.flId" style="width:200px" />
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
				<select id="ygfl_sfff_edit" name="ygfl.sfff">
					<option vlaue="是">是</option>
					<option vlaue="否">否</option>
				</select>
			</span>

		</div>
		<div>
			<span class="label">发放日期:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="ygfl_ffrq_edit" name="ygfl.ffrq" style="width:200px" />

			</span>

		</div>
	</form>
</div>
<script type="text/javascript" src="Ygfl/js/ygfl_manage.js"></script> 
