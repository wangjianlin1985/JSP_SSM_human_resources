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
import com.chengxusheji.service.YgjcService;
import com.chengxusheji.po.Ygjc;
import com.chengxusheji.service.EmployeeService;
import com.chengxusheji.po.Employee;

//Ygjc管理控制层
@Controller
@RequestMapping("/Ygjc")
public class YgjcController extends BaseController {

    /*业务层对象*/
    @Resource YgjcService ygjcService;

    @Resource EmployeeService employeeService;
	@InitBinder("employeeObj")
	public void initBinderemployeeObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("employeeObj.");
	}
	@InitBinder("ygjc")
	public void initBinderYgjc(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("ygjc.");
	}
	/*跳转到添加Ygjc视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Ygjc());
		/*查询所有的Employee信息*/
		List<Employee> employeeList = employeeService.queryAllEmployee();
		request.setAttribute("employeeList", employeeList);
		return "Ygjc_add";
	}

	/*客户端ajax方式提交添加员工奖惩信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Ygjc ygjc, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        ygjcService.addYgjc(ygjc);
        message = "员工奖惩添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询员工奖惩信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String jclx,String title,@ModelAttribute("employeeObj") Employee employeeObj,String jcsj,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (jclx == null) jclx = "";
		if (title == null) title = "";
		if (jcsj == null) jcsj = "";
		if(rows != 0)ygjcService.setRows(rows);
		List<Ygjc> ygjcList = ygjcService.queryYgjc(jclx, title, employeeObj, jcsj, page);
	    /*计算总的页数和总的记录数*/
	    ygjcService.queryTotalPageAndRecordNumber(jclx, title, employeeObj, jcsj);
	    /*获取到总的页码数目*/
	    int totalPage = ygjcService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = ygjcService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Ygjc ygjc:ygjcList) {
			JSONObject jsonYgjc = ygjc.getJsonObject();
			jsonArray.put(jsonYgjc);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询员工奖惩信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Ygjc> ygjcList = ygjcService.queryAllYgjc();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Ygjc ygjc:ygjcList) {
			JSONObject jsonYgjc = new JSONObject();
			jsonYgjc.accumulate("jcId", ygjc.getJcId());
			jsonYgjc.accumulate("title", ygjc.getTitle());
			jsonArray.put(jsonYgjc);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询员工奖惩信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String jclx,String title,@ModelAttribute("employeeObj") Employee employeeObj,String jcsj,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (jclx == null) jclx = "";
		if (title == null) title = "";
		if (jcsj == null) jcsj = "";
		List<Ygjc> ygjcList = ygjcService.queryYgjc(jclx, title, employeeObj, jcsj, currentPage);
	    /*计算总的页数和总的记录数*/
	    ygjcService.queryTotalPageAndRecordNumber(jclx, title, employeeObj, jcsj);
	    /*获取到总的页码数目*/
	    int totalPage = ygjcService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = ygjcService.getRecordNumber();
	    request.setAttribute("ygjcList",  ygjcList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("jclx", jclx);
	    request.setAttribute("title", title);
	    request.setAttribute("employeeObj", employeeObj);
	    request.setAttribute("jcsj", jcsj);
	    List<Employee> employeeList = employeeService.queryAllEmployee();
	    request.setAttribute("employeeList", employeeList);
		return "Ygjc/ygjc_frontquery_result"; 
	}
	
	
	/*前台按照查询条件分页查询员工奖惩信息*/
	@RequestMapping(value = { "/empFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String empFrontlist(String jclx,String title,@ModelAttribute("employeeObj") Employee employeeObj,String jcsj,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (jclx == null) jclx = "";
		if (title == null) title = "";
		if (jcsj == null) jcsj = "";
		employeeObj = new Employee();
		employeeObj.setEmployeeNo(session.getAttribute("user_name").toString());
		
		List<Ygjc> ygjcList = ygjcService.queryYgjc(jclx, title, employeeObj, jcsj, currentPage);
	    /*计算总的页数和总的记录数*/
	    ygjcService.queryTotalPageAndRecordNumber(jclx, title, employeeObj, jcsj);
	    /*获取到总的页码数目*/
	    int totalPage = ygjcService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = ygjcService.getRecordNumber();
	    request.setAttribute("ygjcList",  ygjcList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("jclx", jclx);
	    request.setAttribute("title", title);
	    request.setAttribute("employeeObj", employeeObj);
	    request.setAttribute("jcsj", jcsj);
	    List<Employee> employeeList = employeeService.queryAllEmployee();
	    request.setAttribute("employeeList", employeeList);
		return "Ygjc/ygjc_empFrontquery_result"; 
	}
	

     /*前台查询Ygjc信息*/
	@RequestMapping(value="/{jcId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer jcId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键jcId获取Ygjc对象*/
        Ygjc ygjc = ygjcService.getYgjc(jcId);

        List<Employee> employeeList = employeeService.queryAllEmployee();
        request.setAttribute("employeeList", employeeList);
        request.setAttribute("ygjc",  ygjc);
        return "Ygjc/ygjc_frontshow";
	}

	/*ajax方式显示员工奖惩修改jsp视图页*/
	@RequestMapping(value="/{jcId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer jcId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键jcId获取Ygjc对象*/
        Ygjc ygjc = ygjcService.getYgjc(jcId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonYgjc = ygjc.getJsonObject();
		out.println(jsonYgjc.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新员工奖惩信息*/
	@RequestMapping(value = "/{jcId}/update", method = RequestMethod.POST)
	public void update(@Validated Ygjc ygjc, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			ygjcService.updateYgjc(ygjc);
			message = "员工奖惩更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "员工奖惩更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除员工奖惩信息*/
	@RequestMapping(value="/{jcId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer jcId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  ygjcService.deleteYgjc(jcId);
	            request.setAttribute("message", "员工奖惩删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "员工奖惩删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条员工奖惩记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String jcIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = ygjcService.deleteYgjcs(jcIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出员工奖惩信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String jclx,String title,@ModelAttribute("employeeObj") Employee employeeObj,String jcsj, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(jclx == null) jclx = "";
        if(title == null) title = "";
        if(jcsj == null) jcsj = "";
        List<Ygjc> ygjcList = ygjcService.queryYgjc(jclx,title,employeeObj,jcsj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Ygjc信息记录"; 
        String[] headers = { "奖惩类型","奖惩标题","奖惩员工","奖惩时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<ygjcList.size();i++) {
        	Ygjc ygjc = ygjcList.get(i); 
        	dataset.add(new String[]{ygjc.getJclx(),ygjc.getTitle(),ygjc.getEmployeeObj().getName(),ygjc.getJcsj()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Ygjc.xls");//filename是下载的xls的名，建议最好用英文 
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
