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
import com.chengxusheji.service.ContractService;
import com.chengxusheji.po.Contract;
import com.chengxusheji.service.EmployeeService;
import com.chengxusheji.po.Employee;

//Contract管理控制层
@Controller
@RequestMapping("/Contract")
public class ContractController extends BaseController {

    /*业务层对象*/
    @Resource ContractService contractService;

    @Resource EmployeeService employeeService;
	@InitBinder("employeeObj")
	public void initBinderemployeeObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("employeeObj.");
	}
	@InitBinder("contract")
	public void initBinderContract(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("contract.");
	}
	/*跳转到添加Contract视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Contract());
		/*查询所有的Employee信息*/
		List<Employee> employeeList = employeeService.queryAllEmployee();
		request.setAttribute("employeeList", employeeList);
		return "Contract_add";
	}

	/*客户端ajax方式提交添加员工合同信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Contract contract, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		if(contractService.getContract(contract.getContractNo()) != null) {
			message = "合同编号已经存在！";
			writeJsonResponse(response, success, message);
			return ;
		}
		contract.setHtwj(this.handleFileUpload(request, "htwjFile"));
        contractService.addContract(contract);
        message = "员工合同添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询员工合同信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String contractNo,@ModelAttribute("employeeObj") Employee employeeObj,String htlx,String startTime,String endTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (contractNo == null) contractNo = "";
		if (htlx == null) htlx = "";
		if (startTime == null) startTime = "";
		if (endTime == null) endTime = "";
		if(rows != 0)contractService.setRows(rows);
		List<Contract> contractList = contractService.queryContract(contractNo, employeeObj, htlx, startTime, endTime, page);
	    /*计算总的页数和总的记录数*/
	    contractService.queryTotalPageAndRecordNumber(contractNo, employeeObj, htlx, startTime, endTime);
	    /*获取到总的页码数目*/
	    int totalPage = contractService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = contractService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Contract contract:contractList) {
			JSONObject jsonContract = contract.getJsonObject();
			jsonArray.put(jsonContract);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询员工合同信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Contract> contractList = contractService.queryAllContract();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Contract contract:contractList) {
			JSONObject jsonContract = new JSONObject();
			jsonContract.accumulate("contractNo", contract.getContractNo());
			jsonArray.put(jsonContract);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询员工合同信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String contractNo,@ModelAttribute("employeeObj") Employee employeeObj,String htlx,String startTime,String endTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (contractNo == null) contractNo = "";
		if (htlx == null) htlx = "";
		if (startTime == null) startTime = "";
		if (endTime == null) endTime = "";
		List<Contract> contractList = contractService.queryContract(contractNo, employeeObj, htlx, startTime, endTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    contractService.queryTotalPageAndRecordNumber(contractNo, employeeObj, htlx, startTime, endTime);
	    /*获取到总的页码数目*/
	    int totalPage = contractService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = contractService.getRecordNumber();
	    request.setAttribute("contractList",  contractList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("contractNo", contractNo);
	    request.setAttribute("employeeObj", employeeObj);
	    request.setAttribute("htlx", htlx);
	    request.setAttribute("startTime", startTime);
	    request.setAttribute("endTime", endTime);
	    List<Employee> employeeList = employeeService.queryAllEmployee();
	    request.setAttribute("employeeList", employeeList);
		return "Contract/contract_frontquery_result"; 
	}

     /*前台查询Contract信息*/
	@RequestMapping(value="/{contractNo}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable String contractNo,Model model,HttpServletRequest request) throws Exception {
		/*根据主键contractNo获取Contract对象*/
        Contract contract = contractService.getContract(contractNo);

        List<Employee> employeeList = employeeService.queryAllEmployee();
        request.setAttribute("employeeList", employeeList);
        request.setAttribute("contract",  contract);
        return "Contract/contract_frontshow";
	}

	/*ajax方式显示员工合同修改jsp视图页*/
	@RequestMapping(value="/{contractNo}/update",method=RequestMethod.GET)
	public void update(@PathVariable String contractNo,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键contractNo获取Contract对象*/
        Contract contract = contractService.getContract(contractNo);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonContract = contract.getJsonObject();
		out.println(jsonContract.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新员工合同信息*/
	@RequestMapping(value = "/{contractNo}/update", method = RequestMethod.POST)
	public void update(@Validated Contract contract, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String htwjFileName = this.handleFileUpload(request, "htwjFile");
		if(!htwjFileName.equals(""))contract.setHtwj(htwjFileName);
		try {
			contractService.updateContract(contract);
			message = "员工合同更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "员工合同更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除员工合同信息*/
	@RequestMapping(value="/{contractNo}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String contractNo,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  contractService.deleteContract(contractNo);
	            request.setAttribute("message", "员工合同删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "员工合同删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条员工合同记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String contractNos,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = contractService.deleteContracts(contractNos);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出员工合同信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String contractNo,@ModelAttribute("employeeObj") Employee employeeObj,String htlx,String startTime,String endTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(contractNo == null) contractNo = "";
        if(htlx == null) htlx = "";
        if(startTime == null) startTime = "";
        if(endTime == null) endTime = "";
        List<Contract> contractList = contractService.queryContract(contractNo,employeeObj,htlx,startTime,endTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Contract信息记录"; 
        String[] headers = { "合同编号","合同员工","合同类型","合同开始时间","合同结束时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<contractList.size();i++) {
        	Contract contract = contractList.get(i); 
        	dataset.add(new String[]{contract.getContractNo(),contract.getEmployeeObj().getName(),contract.getHtlx(),contract.getStartTime(),contract.getEndTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Contract.xls");//filename是下载的xls的名，建议最好用英文 
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
