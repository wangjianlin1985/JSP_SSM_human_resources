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
<title>员工合同添加</title>
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
			    	<li role="presentation" ><a href="<%=basePath %>Contract/frontlist">员工合同列表</a></li>
			    	<li role="presentation" class="active"><a href="#contractAdd" aria-controls="contractAdd" role="tab" data-toggle="tab">添加员工合同</a></li>
				</ul>
				<!-- Tab panes -->
				<div class="tab-content">
				    <div role="tabpanel" class="tab-pane" id="contractList">
				    </div>
				    <div role="tabpanel" class="tab-pane active" id="contractAdd"> 
				      	<form class="form-horizontal" name="contractAddForm" id="contractAddForm" enctype="multipart/form-data" method="post"  class="mar_t15">
						  <div class="form-group">
							 <label for="contract_contractNo" class="col-md-2 text-right">合同编号:</label>
							 <div class="col-md-8"> 
							 	<input type="text" id="contract_contractNo" name="contract.contractNo" class="form-control" placeholder="请输入合同编号">
							 </div>
						  </div> 
						  <div class="form-group">
						  	 <label for="contract_employeeObj_employeeNo" class="col-md-2 text-right">合同员工:</label>
						  	 <div class="col-md-8">
							    <select id="contract_employeeObj_employeeNo" name="contract.employeeObj.employeeNo" class="form-control">
							    </select>
						  	 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="contract_htlx" class="col-md-2 text-right">合同类型:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="contract_htlx" name="contract.htlx" class="form-control" placeholder="请输入合同类型">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="contract_startTimeDiv" class="col-md-2 text-right">合同开始时间:</label>
						  	 <div class="col-md-8">
				                <div id="contract_startTimeDiv" class="input-group date contract_startTime col-md-12" data-link-field="contract_startTime">
				                    <input class="form-control" id="contract_startTime" name="contract.startTime" size="16" type="text" value="" placeholder="请选择合同开始时间" readonly>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				                </div>
						  	 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="contract_endTimeDiv" class="col-md-2 text-right">合同结束时间:</label>
						  	 <div class="col-md-8">
				                <div id="contract_endTimeDiv" class="input-group date contract_endTime col-md-12" data-link-field="contract_endTime">
				                    <input class="form-control" id="contract_endTime" name="contract.endTime" size="16" type="text" value="" placeholder="请选择合同结束时间" readonly>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				                </div>
						  	 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="contract_content" class="col-md-2 text-right">合同内容:</label>
						  	 <div class="col-md-8">
							    <textarea name="contract.content" id="contract_content" style="width:100%;height:500px;"></textarea>
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="contract_htwj" class="col-md-2 text-right">合同文件:</label>
						  	 <div class="col-md-8">
							    <a id="contract_htwjImg" border="0px"></a><br/>
							    <input type="hidden" id="contract_htwj" name="contract.htwj"/>
							    <input id="htwjFile" name="htwjFile" type="file" size="50" />
						  	 </div>
						  </div>
				          <div class="form-group">
				             <span class="col-md-2""></span>
				             <span onclick="ajaxContractAdd();" class="btn btn-primary bottom5 top5">添加</span>
				          </div>
						</form> 
				        <style>#contractAddForm .form-group {margin:10px;}  </style>
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
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
<script>
//实例化编辑器
var contract_content_editor = UE.getEditor('contract_content'); //合同内容编辑器
var basePath = "<%=basePath%>";
	//提交添加员工合同信息
	function ajaxContractAdd() { 
		//提交之前先验证表单
		$("#contractAddForm").data('bootstrapValidator').validate();
		if(!$("#contractAddForm").data('bootstrapValidator').isValid()){
			return;
		}
		if(contract_content_editor.getContent() == "") {
			alert('合同内容不能为空');
			return;
		}
		jQuery.ajax({
			type : "post",
			url : basePath + "Contract/add",
			dataType : "json" , 
			data: new FormData($("#contractAddForm")[0]),
			success : function(obj) {
				if(obj.success){ 
					alert("保存成功！");
					$("#contractAddForm").find("input").val("");
					$("#contractAddForm").find("textarea").val("");
					contract_content_editor.setContent("");
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
	//验证员工合同添加表单字段
	$('#contractAddForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			"contract.contractNo": {
				validators: {
					notEmpty: {
						message: "合同编号不能为空",
					}
				}
			},
			"contract.htlx": {
				validators: {
					notEmpty: {
						message: "合同类型不能为空",
					}
				}
			},
			"contract.startTime": {
				validators: {
					notEmpty: {
						message: "合同开始时间不能为空",
					}
				}
			},
			"contract.endTime": {
				validators: {
					notEmpty: {
						message: "合同结束时间不能为空",
					}
				}
			},
		}
	}); 
	//初始化合同员工下拉框值 
	$.ajax({
		url: basePath + "Employee/listAll",
		type: "get",
		success: function(employees,response,status) { 
			$("#contract_employeeObj_employeeNo").empty();
			var html="";
    		$(employees).each(function(i,employee){
    			html += "<option value='" + employee.employeeNo + "'>" + employee.name + "</option>";
    		});
    		$("#contract_employeeObj_employeeNo").html(html);
    	}
	});
	//合同开始时间组件
	$('#contract_startTimeDiv').datetimepicker({
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
		$('#contractAddForm').data('bootstrapValidator').updateStatus('contract.startTime', 'NOT_VALIDATED',null).validateField('contract.startTime');
	});
	//合同结束时间组件
	$('#contract_endTimeDiv').datetimepicker({
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
		$('#contractAddForm').data('bootstrapValidator').updateStatus('contract.endTime', 'NOT_VALIDATED',null).validateField('contract.endTime');
	});
})
</script>
</body>
</html>
