<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Contract" %>
<%@ page import="com.chengxusheji.po.Employee" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Contract> contractList = (List<Contract>)request.getAttribute("contractList");
    //获取所有的employeeObj信息
    List<Employee> employeeList = (List<Employee>)request.getAttribute("employeeList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String contractNo = (String)request.getAttribute("contractNo"); //合同编号查询关键字
    Employee employeeObj = (Employee)request.getAttribute("employeeObj");
    String htlx = (String)request.getAttribute("htlx"); //合同类型查询关键字
    String startTime = (String)request.getAttribute("startTime"); //合同开始时间查询关键字
    String endTime = (String)request.getAttribute("endTime"); //合同结束时间查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>员工合同查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="row"> 
		<div class="col-md-9 wow fadeInDown" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li><a href="<%=basePath %>index.jsp">首页</a></li>
			    	<li role="presentation" class="active"><a href="#contractListPanel" aria-controls="contractListPanel" role="tab" data-toggle="tab">员工合同列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>Contract/contract_frontAdd.jsp" style="display:none;">添加员工合同</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="contractListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>合同编号</td><td>合同员工</td><td>合同类型</td><td>合同开始时间</td><td>合同结束时间</td><td>合同文件</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<contractList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		Contract contract = contractList.get(i); //获取到员工合同对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=contract.getContractNo() %></td>
 											<td><%=contract.getEmployeeObj().getName() %></td>
 											<td><%=contract.getHtlx() %></td>
 											<td><%=contract.getStartTime() %></td>
 											<td><%=contract.getEndTime() %></td>
 											<td><%=contract.getHtwj().equals("")?"暂无文件":"<a href='" + basePath + contract.getHtwj() + "' target='_blank'>" + contract.getHtwj() + "</a>"%>
 											<td>
 												<a href="<%=basePath  %>Contract/<%=contract.getContractNo() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="contractEdit('<%=contract.getContractNo() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="contractDelete('<%=contract.getContractNo() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
 											</td> 
 										</tr>
 										<%}%>
				    				</table>
				    				</div>
				    			</div>
				    		</div>

				    		<div class="row">
					            <div class="col-md-12">
						            <nav class="pull-left">
						                <ul class="pagination">
						                    <li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
						                     <%
						                    	int startPage = currentPage - 5;
						                    	int endPage = currentPage + 5;
						                    	if(startPage < 1) startPage=1;
						                    	if(endPage > totalPage) endPage = totalPage;
						                    	for(int i=startPage;i<=endPage;i++) {
						                    %>
						                    <li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
						                    <%  } %> 
						                    <li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						                </ul>
						            </nav>
						            <div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
					            </div>
				            </div> 
				    </div>
				</div>
			</div>
		</div>
	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>员工合同查询</h1>
		</div>
		<form name="contractQueryForm" id="contractQueryForm" action="<%=basePath %>Contract/frontlist" class="mar_t15" method="post">
			<div class="form-group">
				<label for="contractNo">合同编号:</label>
				<input type="text" id="contractNo" name="contractNo" value="<%=contractNo %>" class="form-control" placeholder="请输入合同编号">
			</div>






            <div class="form-group">
            	<label for="employeeObj_employeeNo">合同员工：</label>
                <select id="employeeObj_employeeNo" name="employeeObj.employeeNo" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(Employee employeeTemp:employeeList) {
	 					String selected = "";
 					if(employeeObj!=null && employeeObj.getEmployeeNo()!=null && employeeObj.getEmployeeNo().equals(employeeTemp.getEmployeeNo()))
 						selected = "selected";
	 				%>
 				 <option value="<%=employeeTemp.getEmployeeNo() %>" <%=selected %>><%=employeeTemp.getName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="htlx">合同类型:</label>
				<input type="text" id="htlx" name="htlx" value="<%=htlx %>" class="form-control" placeholder="请输入合同类型">
			</div>






			<div class="form-group">
				<label for="startTime">合同开始时间:</label>
				<input type="text" id="startTime" name="startTime" class="form-control"  placeholder="请选择合同开始时间" value="<%=startTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
			<div class="form-group">
				<label for="endTime">合同结束时间:</label>
				<input type="text" id="endTime" name="endTime" class="form-control"  placeholder="请选择合同结束时间" value="<%=endTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="contractEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" style="width:900px;" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;员工合同信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
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
			 	<textarea name="contract.content" id="contract_content_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="contract_htwj_edit" class="col-md-3 text-right">合同文件:</label>
		  	 <div class="col-md-9">
			    <a id="contract_htwjA" target="_blank"></a><br/>
			    <input type="hidden" id="contract_htwj" name="contract.htwj"/>
			    <input id="htwjFile" name="htwjFile" type="file" size="50" />
		  	 </div>
		  </div>
		</form> 
	    <style>#contractEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxContractModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
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
//实例化编辑器
var contract_content_edit = UE.getEditor('contract_content_edit'); //合同内容编辑器
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.contractQueryForm.currentPage.value = currentPage;
    document.contractQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.contractQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.contractQueryForm.currentPage.value = pageValue;
    documentcontractQueryForm.submit();
}

/*弹出修改员工合同界面并初始化数据*/
function contractEdit(contractNo) {
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
				contract_content_edit.setContent(contract.content, false);
				$("#contract_htwj").val(contract.htwj);
				$("#contract_htwjA").text(contract.htwj);
				$("#contract_htwjA").attr("href", basePath +　contract.htwj);
				$('#contractEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除员工合同信息*/
function contractDelete(contractNo) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Contract/deletes",
			data : {
				contractNos : contractNo,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#contractQueryForm").submit();
					//location.href= basePath + "Contract/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
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
})
</script>
</body>
</html>

