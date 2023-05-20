<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Department" %>
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
<title>岗位招聘添加</title>
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
			    	<li role="presentation" ><a href="<%=basePath %>Job/frontlist">岗位招聘列表</a></li>
			    	<li role="presentation" class="active"><a href="#jobAdd" aria-controls="jobAdd" role="tab" data-toggle="tab">添加岗位招聘</a></li>
				</ul>
				<!-- Tab panes -->
				<div class="tab-content">
				    <div role="tabpanel" class="tab-pane" id="jobList">
				    </div>
				    <div role="tabpanel" class="tab-pane active" id="jobAdd"> 
				      	<form class="form-horizontal" name="jobAddForm" id="jobAddForm" enctype="multipart/form-data" method="post"  class="mar_t15">
						  <div class="form-group">
						  	 <label for="job_departmentObj_departmentNo" class="col-md-2 text-right">招聘部门:</label>
						  	 <div class="col-md-8">
							    <select id="job_departmentObj_departmentNo" name="job.departmentObj.departmentNo" class="form-control">
							    </select>
						  	 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="job_positionName" class="col-md-2 text-right">招聘岗位:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="job_positionName" name="job.positionName" class="form-control" placeholder="请输入招聘岗位">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="job_personNum" class="col-md-2 text-right">招聘人数:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="job_personNum" name="job.personNum" class="form-control" placeholder="请输入招聘人数">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="job_needSpecial" class="col-md-2 text-right">需求专业:</label>
						  	 <div class="col-md-8">
							    <textarea id="job_needSpecial" name="job.needSpecial" rows="8" class="form-control" placeholder="请输入需求专业"></textarea>
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="job_xueli" class="col-md-2 text-right">学历:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="job_xueli" name="job.xueli" class="form-control" placeholder="请输入学历">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="job_zpdx" class="col-md-2 text-right">招聘对象:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="job_zpdx" name="job.zpdx" class="form-control" placeholder="请输入招聘对象">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="job_jobMemo" class="col-md-2 text-right">招聘备注:</label>
						  	 <div class="col-md-8">
							    <textarea id="job_jobMemo" name="job.jobMemo" rows="8" class="form-control" placeholder="请输入招聘备注"></textarea>
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="job_addTimeDiv" class="col-md-2 text-right">发布时间:</label>
						  	 <div class="col-md-8">
				                <div id="job_addTimeDiv" class="input-group date job_addTime col-md-12" data-link-field="job_addTime">
				                    <input class="form-control" id="job_addTime" name="job.addTime" size="16" type="text" value="" placeholder="请选择发布时间" readonly>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				                </div>
						  	 </div>
						  </div>
				          <div class="form-group">
				             <span class="col-md-2""></span>
				             <span onclick="ajaxJobAdd();" class="btn btn-primary bottom5 top5">添加</span>
				          </div>
						</form> 
				        <style>#jobAddForm .form-group {margin:10px;}  </style>
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
	//提交添加岗位招聘信息
	function ajaxJobAdd() { 
		//提交之前先验证表单
		$("#jobAddForm").data('bootstrapValidator').validate();
		if(!$("#jobAddForm").data('bootstrapValidator').isValid()){
			return;
		}
		jQuery.ajax({
			type : "post",
			url : basePath + "Job/add",
			dataType : "json" , 
			data: new FormData($("#jobAddForm")[0]),
			success : function(obj) {
				if(obj.success){ 
					alert("保存成功！");
					$("#jobAddForm").find("input").val("");
					$("#jobAddForm").find("textarea").val("");
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
	//验证岗位招聘添加表单字段
	$('#jobAddForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			"job.positionName": {
				validators: {
					notEmpty: {
						message: "招聘岗位不能为空",
					}
				}
			},
			"job.personNum": {
				validators: {
					notEmpty: {
						message: "招聘人数不能为空",
					},
					integer: {
						message: "招聘人数不正确"
					}
				}
			},
			"job.needSpecial": {
				validators: {
					notEmpty: {
						message: "需求专业不能为空",
					}
				}
			},
			"job.xueli": {
				validators: {
					notEmpty: {
						message: "学历不能为空",
					}
				}
			},
			"job.zpdx": {
				validators: {
					notEmpty: {
						message: "招聘对象不能为空",
					}
				}
			},
			"job.addTime": {
				validators: {
					notEmpty: {
						message: "发布时间不能为空",
					}
				}
			},
		}
	}); 
	//初始化招聘部门下拉框值 
	$.ajax({
		url: basePath + "Department/listAll",
		type: "get",
		success: function(departments,response,status) { 
			$("#job_departmentObj_departmentNo").empty();
			var html="";
    		$(departments).each(function(i,department){
    			html += "<option value='" + department.departmentNo + "'>" + department.departmentName + "</option>";
    		});
    		$("#job_departmentObj_departmentNo").html(html);
    	}
	});
	//发布时间组件
	$('#job_addTimeDiv').datetimepicker({
		language:  'zh-CN',  //显示语言
		format: 'yyyy-mm-dd hh:ii:ss',
		weekStart: 1,
		todayBtn:  1,
		autoclose: 1,
		minuteStep: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0
	}).on('hide',function(e) {
		//下面这行代码解决日期组件改变日期后不验证的问题
		$('#jobAddForm').data('bootstrapValidator').updateStatus('job.addTime', 'NOT_VALIDATED',null).validateField('job.addTime');
	});
})
</script>
</body>
</html>
