<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Ygfl" %>
<%@ page import="com.chengxusheji.po.Employee" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的employeeObj信息
    List<Employee> employeeList = (List<Employee>)request.getAttribute("employeeList");
    Ygfl ygfl = (Ygfl)request.getAttribute("ygfl");

%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>修改员工福利信息</TITLE>
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
  		<li class="active">员工福利信息修改</li>
	</ul>
		<div class="row"> 
      	<form class="form-horizontal" name="ygflEditForm" id="ygflEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="ygfl_flId_edit" class="col-md-3 text-right">记录id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="ygfl_flId_edit" name="ygfl.flId" class="form-control" placeholder="请输入记录id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="ygfl_year_edit" class="col-md-3 text-right">福利年份:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="ygfl_year_edit" name="ygfl.year" class="form-control" placeholder="请输入福利年份">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="ygfl_employeeObj_employeeNo_edit" class="col-md-3 text-right">福利员工:</label>
		  	 <div class="col-md-9">
			    <select id="ygfl_employeeObj_employeeNo_edit" name="ygfl.employeeObj.employeeNo" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="ygfl_fljj_edit" class="col-md-3 text-right">福利奖金:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="ygfl_fljj_edit" name="ygfl.fljj" class="form-control" placeholder="请输入福利奖金">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="ygfl_sfff_edit" class="col-md-3 text-right">是否发放:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="ygfl_sfff_edit" name="ygfl.sfff" class="form-control" placeholder="请输入是否发放">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="ygfl_ffrq_edit" class="col-md-3 text-right">发放日期:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="ygfl_ffrq_edit" name="ygfl.ffrq" class="form-control" placeholder="请输入发放日期">
			 </div>
		  </div>
			  <div class="form-group">
			  	<span class="col-md-3""></span>
			  	<span onclick="ajaxYgflModify();" class="btn btn-primary bottom5 top5">修改</span>
			  </div>
		</form> 
	    <style>#ygflEditForm .form-group {margin-bottom:5px;}  </style>
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
/*弹出修改员工福利界面并初始化数据*/
function ygflEdit(flId) {
	$.ajax({
		url :  basePath + "Ygfl/" + flId + "/update",
		type : "get",
		dataType: "json",
		success : function (ygfl, response, status) {
			if (ygfl) {
				$("#ygfl_flId_edit").val(ygfl.flId);
				$("#ygfl_year_edit").val(ygfl.year);
				$.ajax({
					url: basePath + "Employee/listAll",
					type: "get",
					success: function(employees,response,status) { 
						$("#ygfl_employeeObj_employeeNo_edit").empty();
						var html="";
		        		$(employees).each(function(i,employee){
		        			html += "<option value='" + employee.employeeNo + "'>" + employee.name + "</option>";
		        		});
		        		$("#ygfl_employeeObj_employeeNo_edit").html(html);
		        		$("#ygfl_employeeObj_employeeNo_edit").val(ygfl.employeeObjPri);
					}
				});
				$("#ygfl_fljj_edit").val(ygfl.fljj);
				$("#ygfl_sfff_edit").val(ygfl.sfff);
				$("#ygfl_ffrq_edit").val(ygfl.ffrq);
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*ajax方式提交员工福利信息表单给服务器端修改*/
function ajaxYgflModify() {
	$.ajax({
		url :  basePath + "Ygfl/" + $("#ygfl_flId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#ygflEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                location.reload(true);
                $("#ygflQueryForm").submit();
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
    ygflEdit("<%=request.getParameter("flId")%>");
 })
 </script> 
</body>
</html>

