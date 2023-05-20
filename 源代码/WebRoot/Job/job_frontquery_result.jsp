<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Job" %>
<%@ page import="com.chengxusheji.po.Department" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Job> jobList = (List<Job>)request.getAttribute("jobList");
    //获取所有的departmentObj信息
    List<Department> departmentList = (List<Department>)request.getAttribute("departmentList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    Department departmentObj = (Department)request.getAttribute("departmentObj");
    String positionName = (String)request.getAttribute("positionName"); //招聘岗位查询关键字
    String xueli = (String)request.getAttribute("xueli"); //学历查询关键字
    String zpdx = (String)request.getAttribute("zpdx"); //招聘对象查询关键字
    String addTime = (String)request.getAttribute("addTime"); //发布时间查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>岗位招聘查询</title>
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
			    	<li role="presentation" class="active"><a href="#jobListPanel" aria-controls="jobListPanel" role="tab" data-toggle="tab">岗位招聘列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>Job/job_frontAdd.jsp" style="display:none;">添加岗位招聘</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="jobListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>招聘部门</td><td>招聘岗位</td><td>招聘人数</td><td>需求专业</td><td>学历</td><td>招聘对象</td><td>发布时间</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<jobList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		Job job = jobList.get(i); //获取到岗位招聘对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=job.getDepartmentObj().getDepartmentName() %></td>
 											<td><%=job.getPositionName() %></td>
 											<td><%=job.getPersonNum() %></td>
 											<td><%=job.getNeedSpecial() %></td>
 											<td><%=job.getXueli() %></td>
 											<td><%=job.getZpdx() %></td>
 											<td><%=job.getAddTime() %></td>
 											<td>
 												<a href="<%=basePath  %>Job/<%=job.getJobId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="jobEdit('<%=job.getJobId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="jobDelete('<%=job.getJobId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
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
    		<h1>岗位招聘查询</h1>
		</div>
		<form name="jobQueryForm" id="jobQueryForm" action="<%=basePath %>Job/frontlist" class="mar_t15" method="post">
            <div class="form-group">
            	<label for="departmentObj_departmentNo">招聘部门：</label>
                <select id="departmentObj_departmentNo" name="departmentObj.departmentNo" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(Department departmentTemp:departmentList) {
	 					String selected = "";
 					if(departmentObj!=null && departmentObj.getDepartmentNo()!=null && departmentObj.getDepartmentNo().equals(departmentTemp.getDepartmentNo()))
 						selected = "selected";
	 				%>
 				 <option value="<%=departmentTemp.getDepartmentNo() %>" <%=selected %>><%=departmentTemp.getDepartmentName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="positionName">招聘岗位:</label>
				<input type="text" id="positionName" name="positionName" value="<%=positionName %>" class="form-control" placeholder="请输入招聘岗位">
			</div>






			<div class="form-group">
				<label for="xueli">学历:</label>
				<input type="text" id="xueli" name="xueli" value="<%=xueli %>" class="form-control" placeholder="请输入学历">
			</div>






			<div class="form-group">
				<label for="zpdx">招聘对象:</label>
				<input type="text" id="zpdx" name="zpdx" value="<%=zpdx %>" class="form-control" placeholder="请输入招聘对象">
			</div>






			<div class="form-group">
				<label for="addTime">发布时间:</label>
				<input type="text" id="addTime" name="addTime" class="form-control"  placeholder="请选择发布时间" value="<%=addTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="jobEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;岗位招聘信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
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
		</form> 
	    <style>#jobEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxJobModify();">提交</button>
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
    document.jobQueryForm.currentPage.value = currentPage;
    document.jobQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.jobQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.jobQueryForm.currentPage.value = pageValue;
    documentjobQueryForm.submit();
}

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
				$('#jobEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除岗位招聘信息*/
function jobDelete(jobId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Job/deletes",
			data : {
				jobIds : jobId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#jobQueryForm").submit();
					//location.href= basePath + "Job/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
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
})
</script>
</body>
</html>

