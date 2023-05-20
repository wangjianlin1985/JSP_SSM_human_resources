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
import com.chengxusheji.service.JixiaoService;
import com.chengxusheji.po.Jixiao;
import com.chengxusheji.service.EmployeeService;
import com.chengxusheji.po.Employee;

//Jixiao管理控制层
@Controller
@RequestMapping("/Jixiao")
public class JixiaoController extends BaseController {

    /*业务层对象*/
    @Resource JixiaoService jixiaoService;

    @Resource EmployeeService employeeService;
	@InitBinder("employeeObj")
	public void initBinderemployeeObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("employeeObj.");
	}
	@InitBinder("jixiao")
	public void initBinderJixiao(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("jixiao.");
	}
	/*跳转到添加Jixiao视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Jixiao());
		/*查询所有的Employee信息*/
		List<Employee> employeeList = employeeService.queryAllEmployee();
		request.setAttribute("employeeList", employeeList);
		return "Jixiao_add";
	}

	/*客户端ajax方式提交添加绩效考核信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Jixiao jixiao, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
		if(jixiaoService.queryJixiao(jixiao.getYear(), jixiao.getJidu(), jixiao.getEmployeeObj(), "").size() > 0) {
			message = "该季度此员工的绩效考核已经登记过了！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
        jixiaoService.addJixiao(jixiao);
        message = "绩效考核添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询绩效考核信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String year,String jidu,@ModelAttribute("employeeObj") Employee employeeObj,String khjg,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (year == null) year = "";
		if (jidu == null) jidu = "";
		if (khjg == null) khjg = "";
		if(rows != 0)jixiaoService.setRows(rows);
		List<Jixiao> jixiaoList = jixiaoService.queryJixiao(year, jidu, employeeObj, khjg, page);
	    /*计算总的页数和总的记录数*/
	    jixiaoService.queryTotalPageAndRecordNumber(year, jidu, employeeObj, khjg);
	    /*获取到总的页码数目*/
	    int totalPage = jixiaoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = jixiaoService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Jixiao jixiao:jixiaoList) {
			JSONObject jsonJixiao = jixiao.getJsonObject();
			jsonArray.put(jsonJixiao);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询绩效考核信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Jixiao> jixiaoList = jixiaoService.queryAllJixiao();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Jixiao jixiao:jixiaoList) {
			JSONObject jsonJixiao = new JSONObject();
			jsonJixiao.accumulate("jixiaoId", jixiao.getJixiaoId());
			jsonArray.put(jsonJixiao);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询绩效考核信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String year,String jidu,@ModelAttribute("employeeObj") Employee employeeObj,String khjg,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (year == null) year = "";
		if (jidu == null) jidu = "";
		if (khjg == null) khjg = "";
		List<Jixiao> jixiaoList = jixiaoService.queryJixiao(year, jidu, employeeObj, khjg, currentPage);
	    /*计算总的页数和总的记录数*/
	    jixiaoService.queryTotalPageAndRecordNumber(year, jidu, employeeObj, khjg);
	    /*获取到总的页码数目*/
	    int totalPage = jixiaoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = jixiaoService.getRecordNumber();
	    request.setAttribute("jixiaoList",  jixiaoList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("year", year);
	    request.setAttribute("jidu", jidu);
	    request.setAttribute("employeeObj", employeeObj);
	    request.setAttribute("khjg", khjg);
	    List<Employee> employeeList = employeeService.queryAllEmployee();
	    request.setAttribute("employeeList", employeeList);
		return "Jixiao/jixiao_frontquery_result"; 
	}
	
	
	/*前台按照查询条件分页查询绩效考核信息*/
	@RequestMapping(value = { "/empFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String empFrontlist(String year,String jidu,@ModelAttribute("employeeObj") Employee employeeObj,String khjg,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (year == null) year = "";
		if (jidu == null) jidu = "";
		if (khjg == null) khjg = "";
		employeeObj = new Employee();
		employeeObj.setEmployeeNo(session.getAttribute("user_name").toString());
		
		List<Jixiao> jixiaoList = jixiaoService.queryJixiao(year, jidu, employeeObj, khjg, currentPage);
	    /*计算总的页数和总的记录数*/
	    jixiaoService.queryTotalPageAndRecordNumber(year, jidu, employeeObj, khjg);
	    /*获取到总的页码数目*/
	    int totalPage = jixiaoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = jixiaoService.getRecordNumber();
	    request.setAttribute("jixiaoList",  jixiaoList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("year", year);
	    request.setAttribute("jidu", jidu);
	    request.setAttribute("employeeObj", employeeObj);
	    request.setAttribute("khjg", khjg);
	    List<Employee> employeeList = employeeService.queryAllEmployee();
	    request.setAttribute("employeeList", employeeList);
		return "Jixiao/jixiao_empFrontquery_result"; 
	}
	

     /*前台查询Jixiao信息*/
	@RequestMapping(value="/{jixiaoId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer jixiaoId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键jixiaoId获取Jixiao对象*/
        Jixiao jixiao = jixiaoService.getJixiao(jixiaoId);

        List<Employee> employeeList = employeeService.queryAllEmployee();
        request.setAttribute("employeeList", employeeList);
        request.setAttribute("jixiao",  jixiao);
        return "Jixiao/jixiao_frontshow";
	}

	/*ajax方式显示绩效考核修改jsp视图页*/
	@RequestMapping(value="/{jixiaoId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer jixiaoId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键jixiaoId获取Jixiao对象*/
        Jixiao jixiao = jixiaoService.getJixiao(jixiaoId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonJixiao = jixiao.getJsonObject();
		out.println(jsonJixiao.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新绩效考核信息*/
	@RequestMapping(value = "/{jixiaoId}/update", method = RequestMethod.POST)
	public void update(@Validated Jixiao jixiao, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			jixiaoService.updateJixiao(jixiao);
			message = "绩效考核更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "绩效考核更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除绩效考核信息*/
	@RequestMapping(value="/{jixiaoId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer jixiaoId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  jixiaoService.deleteJixiao(jixiaoId);
	            request.setAttribute("message", "绩效考核删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "绩效考核删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条绩效考核记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String jixiaoIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = jixiaoService.deleteJixiaos(jixiaoIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出绩效考核信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String year,String jidu,@ModelAttribute("employeeObj") Employee employeeObj,String khjg, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(year == null) year = "";
        if(jidu == null) jidu = "";
        if(khjg == null) khjg = "";
        List<Jixiao> jixiaoList = jixiaoService.queryJixiao(year,jidu,employeeObj,khjg);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Jixiao信息记录"; 
        String[] headers = { "考核年份","考核季度","考核员工","考核结果"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<jixiaoList.size();i++) {
        	Jixiao jixiao = jixiaoList.get(i); 
        	dataset.add(new String[]{jixiao.getYear(),jixiao.getJidu(),jixiao.getEmployeeObj().getName(),jixiao.getKhjg()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Jixiao.xls");//filename是下载的xls的名，建议最好用英文 
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
