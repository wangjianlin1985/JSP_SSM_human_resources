<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Job" %>
<%@ page import="com.chengxusheji.po.Department" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的departmentObj信息
    List<Department> departmentList = (List<Department>)request.getAttribute("departmentList");
    Job job = (Job)request.getAttribute("job");

%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>修改岗位招聘信息</TITLE>
  <link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/animate.css" rel="stylesheet"> 
</head>
<body style="margin-top:70px;"> 
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
	<ul class="breadcrumb">
  		<li><a href="<%=basePath %>index.jsp">首页</a></li>
  		<li class="active">岗位招聘信息修改</li>
	</ul>
		<div class="row"> 
      	<form class="form-horizontal" name="jobEditForm" id="jobEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="job_jobId_edit" class="col-md-3 text-right">岗位id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="job_jobId_edit" name="job.jobId" class="form-control" placeholder="请输入岗位id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="job_departmentObj_departmentNo_edit" class="col-md-3 text-right">招聘部门:</label>
		  	 <div class="col-md-9">
			    <select id="job_departmentObj_departmentNo_edit" name="job.departmentObj.departmentNo" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="job_positionName_edit" class="col-md-3 text-right">招聘岗位:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="job_positionName_edit" name="job.positionName" class="form-control" placeholder="请输入招聘岗位">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="job_personNum_edit" class="col-md-3 text-right">招聘人数:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="job_personNum_edit" name="job.personNum" class="form-control" placeholder="请输入招聘人数">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="job_needSpecial_edit" class="col-md-3 text-right">需求专业:</label>
		  	 <div class="col-md-9">
			    <textarea id="job_needSpecial_edit" name="job.needSpecial" rows="8" class="form-control" placeholder="请输入需求专业"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="job_xueli_edit" class="col-md-3 text-right">学历:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="job_xueli_edit" name="job.xueli" class="form-control" placeholder="请输入学历">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="job_zpdx_edit" class="col-md-3 text-right">招聘对象:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="job_zpdx_edit" name="job.zpdx" class="form-control" placeholder="请输入招聘对象">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="job_jobMemo_edit" class="col-md-3 text-right">招聘备注:</label>
		  	 <div class="col-md-9">
			    <textarea id="job_jobMemo_edit" name="job.jobMemo" rows="8" class="form-control" placeholder="请输入招聘备注"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="job_addTime_edit" class="col-md-3 text-right">发布时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date job_addTime_edit col-md-12" data-link-field="job_addTime_edit">
                    <input class="form-control" id="job_addTime_edit" name="job.addTime" size="16" type="text" value="" placeholder="请选择发布时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
			  <div class="form-group">
			  	<span class="col-md-3""></span>
			  	<span onclick="ajaxJobModify();" class="btn btn-primary bottom5 top5">修改</span>
			  </div>
		</form> 
	    <style>#jobEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
   </div>
</div>


<jsp:include page="../footer.jsp"></jsp:include>
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*弹出修改岗位招聘界面并初始化数据*/
function jobEdit(jobId) {
	$.ajax({
		url :  basePath + "Job/" + jobId + "/update",
		type : "get",
		dataType: "json",
		success : function (job, response, status) {
			if (job) {
				$("#job_jobId_edit").val(job.jobId);
				$.ajax({
					url: basePath + "Department/listAll",
					type: "get",
					success: function(departments,response,status) { 
						$("#job_departmentObj_departmentNo_edit").empty();
						var html="";
		        		$(departments).each(function(i,department){
		        			html += "<option value='" + department.departmentNo + "'>" + department.departmentName + "</option>";
		        		});
		        		$("#job_departmentObj_departmentNo_edit").html(html);
		        		$("#job_departmentObj_departmentNo_edit").val(job.departmentObjPri);
					}
				});
				$("#job_positionName_edit").val(job.positionName);
				$("#job_personNum_edit").val(job.personNum);
				$("#job_needSpecial_edit").val(job.needSpecial);
				$("#job_xueli_edit").val(job.xueli);
				$("#job_zpdx_edit").val(job.zpdx);
				$("#job_jobMemo_edit").val(job.jobMemo);
				$("#job_addTime_edit").val(job.addTime);
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*ajax方式提交岗位招聘信息表单给服务器端修改*/
function ajaxJobModify() {
	$.ajax({
		url :  basePath + "Job/" + $("#job_jobId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#jobEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                location.reload(true);
                $("#jobQueryForm").submit();
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

$(function(){
        /*小屏幕导航点击关闭菜单*/
        $('.navbar-collapse a').click(function(){
            $('.navbar-collapse').collapse('hide');
        });
        new WOW().init();
    /*发布时间组件*/
    $('.job_addTime_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd hh:ii:ss',
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
    jobEdit("<%=request.getParameter("jobId")%>");
 })
 </script> 
</body>
</html>

