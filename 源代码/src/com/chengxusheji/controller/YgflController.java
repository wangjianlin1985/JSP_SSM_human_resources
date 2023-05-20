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
import org.springframework.web.bind.annotation.RequestParam;

import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.JixiaoService;
import com.chengxusheji.service.YgflService;
import com.chengxusheji.po.Ygfl;
import com.chengxusheji.service.EmployeeService;
import com.chengxusheji.po.Employee;

//Ygfl管理控制层
@Controller
@RequestMapping("/Ygfl")
public class YgflController extends BaseController {

    /*业务层对象*/
    @Resource YgflService ygflService;
    @Resource JixiaoService jixiaoService;

    @Resource EmployeeService employeeService;
	@InitBinder("employeeObj")
	public void initBinderemployeeObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("employeeObj.");
	}
	@InitBinder("ygfl")
	public void initBinderYgfl(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("ygfl.");
	}
	/*跳转到添加Ygfl视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Ygfl());
		/*查询所有的Employee信息*/
		List<Employee> employeeList = employeeService.queryAllEmployee();
		request.setAttribute("employeeList", employeeList);
		return "Ygfl_add";
	}

	/*客户端ajax方式提交添加员工福利信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Ygfl ygfl, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        ygflService.addYgfl(ygfl);
        message = "员工福利添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	/*客户端ajax方式员工福利评比生成*/
	@RequestMapping(value = "/make", method = RequestMethod.POST)
	public void make(@RequestParam("year")String year,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		
		ArrayList<Employee> employeeList = employeeService.queryAllEmployee();
		for(Employee employee: employeeList) {
			int goodSize = jixiaoService.queryJixiao(year, "", employee, "优").size() ;
			float fljj = 0.0f; //福利奖金
			
			if(goodSize == 2) fljj = 3000; //2个季度优奖金3000
			if(goodSize == 3) fljj = 5000; //3个季度优奖金5000
			if(goodSize == 4) fljj = 8000; //4个季度优奖金8000
			
			Ygfl ygfl = new Ygfl();
			ygfl.setEmployeeObj(employee);
			ygfl.setFfrq("--");
			ygfl.setFljj(fljj);
			ygfl.setSfff("否");
			ygfl.setYear(year);
			
			if(fljj != 0.0f) {
				ygflService.addYgfl(ygfl);
			}
			
			
		}
		
        message = "员工福利评比结果生成成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	
	/*ajax方式按照查询条件分页查询员工福利信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String year,@ModelAttribute("employeeObj") Employee employeeObj,String sfff,String ffrq,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (year == null) year = "";
		if (sfff == null) sfff = "";
		if (ffrq == null) ffrq = "";
		if(rows != 0)ygflService.setRows(rows);
		List<Ygfl> ygflList = ygflService.queryYgfl(year, employeeObj, sfff, ffrq, page);
	    /*计算总的页数和总的记录数*/
	    ygflService.queryTotalPageAndRecordNumber(year, employeeObj, sfff, ffrq);
	    /*获取到总的页码数目*/
	    int totalPage = ygflService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = ygflService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Ygfl ygfl:ygflList) {
			JSONObject jsonYgfl = ygfl.getJsonObject();
			jsonArray.put(jsonYgfl);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询员工福利信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Ygfl> ygflList = ygflService.queryAllYgfl();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Ygfl ygfl:ygflList) {
			JSONObject jsonYgfl = new JSONObject();
			jsonYgfl.accumulate("flId", ygfl.getFlId());
			jsonArray.put(jsonYgfl);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询员工福利信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String year,@ModelAttribute("employeeObj") Employee employeeObj,String sfff,String ffrq,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (year == null) year = "";
		if (sfff == null) sfff = "";
		if (ffrq == null) ffrq = "";
		List<Ygfl> ygflList = ygflService.queryYgfl(year, employeeObj, sfff, ffrq, currentPage);
	    /*计算总的页数和总的记录数*/
	    ygflService.queryTotalPageAndRecordNumber(year, employeeObj, sfff, ffrq);
	    /*获取到总的页码数目*/
	    int totalPage = ygflService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = ygflService.getRecordNumber();
	    request.setAttribute("ygflList",  ygflList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("year", year);
	    request.setAttribute("employeeObj", employeeObj);
	    request.setAttribute("sfff", sfff);
	    request.setAttribute("ffrq", ffrq);
	    List<Employee> employeeList = employeeService.queryAllEmployee();
	    request.setAttribute("employeeList", employeeList);
		return "Ygfl/ygfl_frontquery_result"; 
	}

	
	
	/*前台按照查询条件分页查询员工福利信息*/
	@RequestMapping(value = { "/empFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String empFrontlist(String year,@ModelAttribute("employeeObj") Employee employeeObj,String sfff,String ffrq,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (year == null) year = "";
		if (sfff == null) sfff = "";
		if (ffrq == null) ffrq = "";
		employeeObj = new Employee();
		employeeObj.setEmployeeNo(session.getAttribute("user_name").toString());
		List<Ygfl> ygflList = ygflService.queryYgfl(year, employeeObj, sfff, ffrq, currentPage);
	    /*计算总的页数和总的记录数*/
	    ygflService.queryTotalPageAndRecordNumber(year, employeeObj, sfff, ffrq);
	    /*获取到总的页码数目*/
	    int totalPage = ygflService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = ygflService.getRecordNumber();
	    request.setAttribute("ygflList",  ygflList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("year", year);
	    request.setAttribute("employeeObj", employeeObj);
	    request.setAttribute("sfff", sfff);
	    request.setAttribute("ffrq", ffrq);
	    List<Employee> employeeList = employeeService.queryAllEmployee();
	    request.setAttribute("employeeList", employeeList);
		return "Ygfl/ygfl_empFrontquery_result"; 
	}

	
	
     /*前台查询Ygfl信息*/
	@RequestMapping(value="/{flId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer flId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键flId获取Ygfl对象*/
        Ygfl ygfl = ygflService.getYgfl(flId);

        List<Employee> employeeList = employeeService.queryAllEmployee();
        request.setAttribute("employeeList", employeeList);
        request.setAttribute("ygfl",  ygfl);
        return "Ygfl/ygfl_frontshow";
	}

	/*ajax方式显示员工福利修改jsp视图页*/
	@RequestMapping(value="/{flId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer flId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键flId获取Ygfl对象*/
        Ygfl ygfl = ygflService.getYgfl(flId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonYgfl = ygfl.getJsonObject();
		out.println(jsonYgfl.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新员工福利信息*/
	@RequestMapping(value = "/{flId}/update", method = RequestMethod.POST)
	public void update(@Validated Ygfl ygfl, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			ygflService.updateYgfl(ygfl);
			message = "员工福利更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "员工福利更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除员工福利信息*/
	@RequestMapping(value="/{flId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer flId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  ygflService.deleteYgfl(flId);
	            request.setAttribute("message", "员工福利删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "员工福利删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条员工福利记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String flIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = ygflService.deleteYgfls(flIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出员工福利信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String year,@ModelAttribute("employeeObj") Employee employeeObj,String sfff,String ffrq, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(year == null) year = "";
        if(sfff == null) sfff = "";
        if(ffrq == null) ffrq = "";
        List<Ygfl> ygflList = ygflService.queryYgfl(year,employeeObj,sfff,ffrq);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Ygfl信息记录"; 
        String[] headers = { "福利年份","福利员工","福利奖金","是否发放","发放日期"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<ygflList.size();i++) {
        	Ygfl ygfl = ygflList.get(i); 
        	dataset.add(new String[]{ygfl.getYear(),ygfl.getEmployeeObj().getName(),ygfl.getFljj() + "",ygfl.getSfff(),ygfl.getFfrq()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Ygfl.xls");//filename是下载的xls的名，建议最好用英文 
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
