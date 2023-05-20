<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Contract" %>
<%@ page import="com.chengxusheji.po.Employee" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的employeeObj信息
    List<Employee> employeeList = (List<Employee>)request.getAttribute("employeeList");
    Contract contract = (Contract)request.getAttribute("contract");

%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>修改员工合同信息</TITLE>
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
  		<li class="active">员工合同信息修改</li>
	</ul>
		<div class="row"> 
      	<form class="form-horizontal" name="contractEditForm" id="contractEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="contract_contractNo_edit" class="col-md-3 text-right">合同编号:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="contract_contractNo_edit" name="contract.contractNo" class="form-control" placeholder="请输入合同编号" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="contract_employeeObj_employeeNo_edit" class="col-md-3 text-right">合同员工:</label>
		  	 <div class="col-md-9">
			    <select id="contract_employeeObj_employeeNo_edit" name="contract.employeeObj.employeeNo" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="contract_htlx_edit" class="col-md-3 text-right">合同类型:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="contract_htlx_edit" name="contract.htlx" class="form-control" placeholder="请输入合同类型">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="contract_startTime_edit" class="col-md-3 text-right">合同开始时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date contract_startTime_edit col-md-12" data-link-field="contract_startTime_edit">
                    <input class="form-control" id="contract_startTime_edit" name="contract.startTime" size="16" type="text" value="" placeholder="请选择合同开始时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="contract_endTime_edit" class="col-md-3 text-right">合同结束时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date contract_endTime_edit col-md-12" data-link-field="contract_endTime_edit">
                    <input class="form-control" id="contract_endTime_edit" name="contract.endTime" size="16" type="text" value="" placeholder="请选择合同结束时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="contract_content_edit" class="col-md-3 text-right">合同内容:</label>
		  	 <div class="col-md-9">
			    <script name="contract.content" id="contract_content_edit" type="text/plain"   style="width:100%;height:500px;"></script>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="contract_htwj_edit" class="col-md-3 text-right">合同文件:</label>
		  	 <div class="col-md-9">
			    <a id="contract_htwjImg" width="200px" border="0px"></a><br/>
			    <input type="hidden" id="contract_htwj" name="contract.htwj"/>
			    <input id="htwjFile" name="htwjFile" type="file" size="50" />
		  	 </div>
		  </div>
			  <div class="form-group">
			  	<span class="col-md-3""></span>
			  	<span onclick="ajaxContractModify();" class="btn btn-primary bottom5 top5">修改</span>
			  </div>
		</form> 
	    <style>#contractEditForm .form-group {margin-bottom:5px;}  </style>
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
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
<script>
var contract_content_editor = UE.getEditor('contract_content_edit'); //合同内容编辑框
var basePath = "<%=basePath%>";
/*弹出修改员工合同界面并初始化数据*/
function contractEdit(contractNo) {
  contract_content_editor.addListener("ready", function () {
    // editor准备好之后才可以使用 
    ajaxModifyQuery(contractNo);
  });
}
 function ajaxModifyQuery(contractNo) {
	$.ajax({
		url :  basePath + "Contract/" + contractNo + "/update",
		type : "get",
		dataType: "json",
		success : function (contract, response, status) {
			if (contract) {
				$("#contract_contractNo_edit").val(contract.contractNo);
				$.ajax({
					url: basePath + "Employee/listAll",
					type: "get",
					success: function(employees,response,status) { 
						$("#contract_employeeObj_employeeNo_edit").empty();
						var html="";
		        		$(employees).each(function(i,employee){
		        			html += "<option value='" + employee.employeeNo + "'>" + employee.name + "</option>";
		        		});
		        		$("#contract_employeeObj_employeeNo_edit").html(html);
		        		$("#contract_employeeObj_employeeNo_edit").val(contract.employeeObjPri);
					}
				});
				$("#contract_htlx_edit").val(contract.htlx);
				$("#contract_startTime_edit").val(contract.startTime);
				$("#contract_endTime_edit").val(contract.endTime);
				contract_content_editor.setContent(contract.content, false);
				$("#contract_htwjA").val(contract.htwj);
				$("#contract_htwjA").attr("href", basePath +　contract.htwj);
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*ajax方式提交员工合同信息表单给服务器端修改*/
function ajaxContractModify() {
	$.ajax({
		url :  basePath + "Contract/" + $("#contract_contractNo_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#contractEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                location.reload(true);
                $("#contractQueryForm").submit();
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
    /*合同开始时间组件*/
    $('.contract_startTime_edit').datetimepicker({
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
    /*合同结束时间组件*/
    $('.contract_endTime_edit').datetimepicker({
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
    contractEdit("<%=request.getParameter("contractNo")%>");
 })
 </script> 
</body>
</html>

