package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.JobService;
import com.chengxusheji.po.Job;
import com.chengxusheji.service.DepartmentService;
import com.chengxusheji.po.Department;

//Job管理控制层
@Controller
@RequestMapping("/Job")
public class JobController extends BaseController {

    /*业务层对象*/
    @Resource JobService jobService;

    @Resource DepartmentService departmentService;
	@InitBinder("departmentObj")
	public void initBinderdepartmentObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("departmentObj.");
	}
	@InitBinder("job")
	public void initBinderJob(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("job.");
	}
	/*跳转到添加Job视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Job());
		/*查询所有的Department信息*/
		List<Department> departmentList = departmentService.queryAllDepartment();
		request.setAttribute("departmentList", departmentList);
		return "Job_add";
	}

	/*客户端ajax方式提交添加岗位招聘信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Job job, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        jobService.addJob(job);
        message = "岗位招聘添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询岗位招聘信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("departmentObj") Department departmentObj,String positionName,String xueli,String zpdx,String addTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (positionName == null) positionName = "";
		if (xueli == null) xueli = "";
		if (zpdx == null) zpdx = "";
		if (addTime == null) addTime = "";
		if(rows != 0)jobService.setRows(rows);
		List<Job> jobList = jobService.queryJob(departmentObj, positionName, xueli, zpdx, addTime, page);
	    /*计算总的页数和总的记录数*/
	    jobService.queryTotalPageAndRecordNumber(departmentObj, positionName, xueli, zpdx, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = jobService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = jobService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Job job:jobList) {
			JSONObject jsonJob = job.getJsonObject();
			jsonArray.put(jsonJob);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询岗位招聘信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Job> jobList = jobService.queryAllJob();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Job job:jobList) {
			JSONObject jsonJob = new JSONObject();
			jsonJob.accumulate("jobId", job.getJobId());
			jsonJob.accumulate("positionName", job.getPositionName());
			jsonArray.put(jsonJob);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询岗位招聘信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("departmentObj") Department departmentObj,String positionName,String xueli,String zpdx,String addTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (positionName == null) positionName = "";
		if (xueli == null) xueli = "";
		if (zpdx == null) zpdx = "";
		if (addTime == null) addTime = "";
		List<Job> jobList = jobService.queryJob(departmentObj, positionName, xueli, zpdx, addTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    jobService.queryTotalPageAndRecordNumber(departmentObj, positionName, xueli, zpdx, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = jobService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = jobService.getRecordNumber();
	    request.setAttribute("jobList",  jobList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("departmentObj", departmentObj);
	    request.setAttribute("positionName", positionName);
	    request.setAttribute("xueli", xueli);
	    request.setAttribute("zpdx", zpdx);
	    request.setAttribute("addTime", addTime);
	    List<Department> departmentList = departmentService.queryAllDepartment();
	    request.setAttribute("departmentList", departmentList);
		return "Job/job_frontquery_result"; 
	}

     /*前台查询Job信息*/
	@RequestMapping(value="/{jobId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer jobId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键jobId获取Job对象*/
        Job job = jobService.getJob(jobId);

        List<Department> departmentList = departmentService.queryAllDepartment();
        request.setAttribute("departmentList", departmentList);
        request.setAttribute("job",  job);
        return "Job/job_frontshow";
	}

	/*ajax方式显示岗位招聘修改jsp视图页*/
	@RequestMapping(value="/{jobId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer jobId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键jobId获取Job对象*/
        Job job = jobService.getJob(jobId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonJob = job.getJsonObject();
		out.println(jsonJob.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新岗位招聘信息*/
	@RequestMapping(value = "/{jobId}/update", method = RequestMethod.POST)
	public void update(@Validated Job job, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			jobService.updateJob(job);
			message = "岗位招聘更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "岗位招聘更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除岗位招聘信息*/
	@RequestMapping(value="/{jobId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer jobId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  jobService.deleteJob(jobId);
	            request.setAttribute("message", "岗位招聘删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "岗位招聘删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条岗位招聘记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String jobIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = jobService.deleteJobs(jobIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出岗位招聘信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("departmentObj") Department departmentObj,String positionName,String xueli,String zpdx,String addTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(positionName == null) positionName = "";
        if(xueli == null) xueli = "";
        if(zpdx == null) zpdx = "";
        if(addTime == null) addTime = "";
        List<Job> jobList = jobService.queryJob(departmentObj,positionName,xueli,zpdx,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Job信息记录"; 
        String[] headers = { "招聘部门","招聘岗位","招聘人数","需求专业","学历","招聘对象","发布时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<jobList.size();i++) {
        	Job job = jobList.get(i); 
        	dataset.add(new String[]{job.getDepartmentObj().getDepartmentName(),job.getPositionName(),job.getPersonNum() + "",job.getNeedSpecial(),job.getXueli(),job.getZpdx(),job.getAddTime()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Job.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
