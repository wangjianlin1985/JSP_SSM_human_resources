<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Ygfl" %>
<%@ page import="com.chengxusheji.po.Employee" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Ygfl> ygflList = (List<Ygfl>)request.getAttribute("ygflList");
    //获取所有的employeeObj信息
    List<Employee> employeeList = (List<Employee>)request.getAttribute("employeeList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String year = (String)request.getAttribute("year"); //福利年份查询关键字
    Employee employeeObj = (Employee)request.getAttribute("employeeObj");
    String sfff = (String)request.getAttribute("sfff"); //是否发放查询关键字
    String ffrq = (String)request.getAttribute("ffrq"); //发放日期查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>员工福利查询</title>
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
			    	<li role="presentation" class="active"><a href="#ygflListPanel" aria-controls="ygflListPanel" role="tab" data-toggle="tab">员工福利列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>Ygfl/ygfl_frontAdd.jsp" style="display:none;">添加员工福利</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="ygflListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>福利年份</td><td>福利员工</td><td>福利奖金</td><td>是否发放</td><td>发放日期</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<ygflList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		Ygfl ygfl = ygflList.get(i); //获取到员工福利对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=ygfl.getYear() %></td>
 											<td><%=ygfl.getEmployeeObj().getName() %></td>
 											<td><%=ygfl.getFljj() %></td>
 											<td><%=ygfl.getSfff() %></td>
 											<td><%=ygfl.getFfrq() %></td>
 											<td>
 												<a href="<%=basePath  %>Ygfl/<%=ygfl.getFlId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="ygflEdit('<%=ygfl.getFlId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="ygflDelete('<%=ygfl.getFlId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
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
    		<h1>员工福利查询</h1>
		</div>
		<form name="ygflQueryForm" id="ygflQueryForm" action="<%=basePath %>Ygfl/frontlist" class="mar_t15" method="post">
			<div class="form-group">
				<label for="year">福利年份:</label>
				<input type="text" id="year" name="year" value="<%=year %>" class="form-control" placeholder="请输入福利年份">
			</div>






            <div class="form-group">
            	<label for="employeeObj_employeeNo">福利员工：</label>
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
				<label for="sfff">是否发放:</label>
				<input type="text" id="sfff" name="sfff" value="<%=sfff %>" class="form-control" placeholder="请输入是否发放">
			</div>






			<div class="form-group">
				<label for="ffrq">发放日期:</label>
				<input type="text" id="ffrq" name="ffrq" value="<%=ffrq %>" class="form-control" placeholder="请输入发放日期">
			</div>






            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="ygflEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;员工福利信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
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
		</form> 
	    <style>#ygflEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxYgflModify();">提交</button>
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
<script>
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.ygflQueryForm.currentPage.value = currentPage;
    document.ygflQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.ygflQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.ygflQueryForm.currentPage.value = pageValue;
    documentygflQueryForm.submit();
}

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
				$('#ygflEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除员工福利信息*/
function ygflDelete(flId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Ygfl/deletes",
			data : {
				flIds : flId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#ygflQueryForm").submit();
					//location.href= basePath + "Ygfl/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
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

})
</script>
</body>
</html>

