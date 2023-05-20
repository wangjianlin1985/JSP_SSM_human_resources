<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Employee" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>员工福利添加</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<jsp:include page="../header.jsp"></jsp:include>
<div class="container">
	<div class="row">
		<div class="col-md-12 wow fadeInUp" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li role="presentation" ><a href="<%=basePath %>Ygfl/frontlist">员工福利列表</a></li>
			    	<li role="presentation" class="active"><a href="#ygflAdd" aria-controls="ygflAdd" role="tab" data-toggle="tab">添加员工福利</a></li>
				</ul>
				<!-- Tab panes -->
				<div class="tab-content">
				    <div role="tabpanel" class="tab-pane" id="ygflList">
				    </div>
				    <div role="tabpanel" class="tab-pane active" id="ygflAdd"> 
				      	<form class="form-horizontal" name="ygflAddForm" id="ygflAddForm" enctype="multipart/form-data" method="post"  class="mar_t15">
						  <div class="form-group">
						  	 <label for="ygfl_year" class="col-md-2 text-right">福利年份:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="ygfl_year" name="ygfl.year" class="form-control" placeholder="请输入福利年份">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="ygfl_employeeObj_employeeNo" class="col-md-2 text-right">福利员工:</label>
						  	 <div class="col-md-8">
							    <select id="ygfl_employeeObj_employeeNo" name="ygfl.employeeObj.employeeNo" class="form-control">
							    </select>
						  	 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="ygfl_fljj" class="col-md-2 text-right">福利奖金:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="ygfl_fljj" name="ygfl.fljj" class="form-control" placeholder="请输入福利奖金">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="ygfl_sfff" class="col-md-2 text-right">是否发放:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="ygfl_sfff" name="ygfl.sfff" class="form-control" placeholder="请输入是否发放">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="ygfl_ffrq" class="col-md-2 text-right">发放日期:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="ygfl_ffrq" name="ygfl.ffrq" class="form-control" placeholder="请输入发放日期">
							 </div>
						  </div>
				          <div class="form-group">
				             <span class="col-md-2""></span>
				             <span onclick="ajaxYgflAdd();" class="btn btn-primary bottom5 top5">添加</span>
				          </div>
						</form> 
				        <style>#ygflAddForm .form-group {margin:10px;}  </style>
					</div>
				</div>
			</div>
		</div>
	</div> 
</div>

<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script type="text/javascript" src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script>
var basePath = "<%=basePath%>";
	//提交添加员工福利信息
	function ajaxYgflAdd() { 
		//提交之前先验证表单
		$("#ygflAddForm").data('bootstrapValidator').validate();
		if(!$("#ygflAddForm").data('bootstrapValidator').isValid()){
			return;
		}
		jQuery.ajax({
			type : "post",
			url : basePath + "Ygfl/add",
			dataType : "json" , 
			data: new FormData($("#ygflAddForm")[0]),
			success : function(obj) {
				if(obj.success){ 
					alert("保存成功！");
					$("#ygflAddForm").find("input").val("");
					$("#ygflAddForm").find("textarea").val("");
				} else {
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
	//验证员工福利添加表单字段
	$('#ygflAddForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			"ygfl.year": {
				validators: {
					notEmpty: {
						message: "福利年份不能为空",
					}
				}
			},
			"ygfl.fljj": {
				validators: {
					notEmpty: {
						message: "福利奖金不能为空",
					},
					numeric: {
						message: "福利奖金不正确"
					}
				}
			},
			"ygfl.sfff": {
				validators: {
					notEmpty: {
						message: "是否发放不能为空",
					}
				}
			},
			"ygfl.ffrq": {
				validators: {
					notEmpty: {
						message: "发放日期不能为空",
					}
				}
			},
		}
	}); 
	//初始化福利员工下拉框值 
	$.ajax({
		url: basePath + "Employee/listAll",
		type: "get",
		success: function(employees,response,status) { 
			$("#ygfl_employeeObj_employeeNo").empty();
			var html="";
    		$(employees).each(function(i,employee){
    			html += "<option value='" + employee.employeeNo + "'>" + employee.name + "</option>";
    		});
    		$("#ygfl_employeeObj_employeeNo").html(html);
    	}
	});
})
</script>
</body>
</html>
