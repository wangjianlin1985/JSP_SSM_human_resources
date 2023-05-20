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
import com.chengxusheji.service.TrainService;
import com.chengxusheji.po.Train;

//Train管理控制层
@Controller
@RequestMapping("/Train")
public class TrainController extends BaseController {

    /*业务层对象*/
    @Resource TrainService trainService;

	@InitBinder("train")
	public void initBinderTrain(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("train.");
	}
	/*跳转到添加Train视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Train());
		return "Train_add";
	}

	/*客户端ajax方式提交添加公司培训信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Train train, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        trainService.addTrain(train);
        message = "公司培训添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询公司培训信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String trainName,String startTime,String endTime,String teacher,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (trainName == null) trainName = "";
		if (startTime == null) startTime = "";
		if (endTime == null) endTime = "";
		if (teacher == null) teacher = "";
		if(rows != 0)trainService.setRows(rows);
		List<Train> trainList = trainService.queryTrain(trainName, startTime, endTime, teacher, page);
	    /*计算总的页数和总的记录数*/
	    trainService.queryTotalPageAndRecordNumber(trainName, startTime, endTime, teacher);
	    /*获取到总的页码数目*/
	    int totalPage = trainService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = trainService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Train train:trainList) {
			JSONObject jsonTrain = train.getJsonObject();
			jsonArray.put(jsonTrain);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询公司培训信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Train> trainList = trainService.queryAllTrain();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Train train:trainList) {
			JSONObject jsonTrain = new JSONObject();
			jsonTrain.accumulate("trainId", train.getTrainId());
			jsonTrain.accumulate("trainName", train.getTrainName());
			jsonArray.put(jsonTrain);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询公司培训信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String trainName,String startTime,String endTime,String teacher,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (trainName == null) trainName = "";
		if (startTime == null) startTime = "";
		if (endTime == null) endTime = "";
		if (teacher == null) teacher = "";
		List<Train> trainList = trainService.queryTrain(trainName, startTime, endTime, teacher, currentPage);
	    /*计算总的页数和总的记录数*/
	    trainService.queryTotalPageAndRecordNumber(trainName, startTime, endTime, teacher);
	    /*获取到总的页码数目*/
	    int totalPage = trainService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = trainService.getRecordNumber();
	    request.setAttribute("trainList",  trainList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("trainName", trainName);
	    request.setAttribute("startTime", startTime);
	    request.setAttribute("endTime", endTime);
	    request.setAttribute("teacher", teacher);
		return "Train/train_frontquery_result"; 
	}

     /*前台查询Train信息*/
	@RequestMapping(value="/{trainId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer trainId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键trainId获取Train对象*/
        Train train = trainService.getTrain(trainId);

        request.setAttribute("train",  train);
        return "Train/train_frontshow";
	}

	/*ajax方式显示公司培训修改jsp视图页*/
	@RequestMapping(value="/{trainId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer trainId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键trainId获取Train对象*/
        Train train = trainService.getTrain(trainId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonTrain = train.getJsonObject();
		out.println(jsonTrain.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新公司培训信息*/
	@RequestMapping(value = "/{trainId}/update", method = RequestMethod.POST)
	public void update(@Validated Train train, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			trainService.updateTrain(train);
			message = "公司培训更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "公司培训更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除公司培训信息*/
	@RequestMapping(value="/{trainId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer trainId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  trainService.deleteTrain(trainId);
	            request.setAttribute("message", "公司培训删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "公司培训删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条公司培训记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String trainIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = trainService.deleteTrains(trainIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出公司培训信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String trainName,String startTime,String endTime,String teacher, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(trainName == null) trainName = "";
        if(startTime == null) startTime = "";
        if(endTime == null) endTime = "";
        if(teacher == null) teacher = "";
        List<Train> trainList = trainService.queryTrain(trainName,startTime,endTime,teacher);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Train信息记录"; 
        String[] headers = { "培训名称","开始时间","结束时间","讲师","培训人数"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<trainList.size();i++) {
        	Train train = trainList.get(i); 
        	dataset.add(new String[]{train.getTrainName(),train.getStartTime(),train.getEndTime(),train.getTeacher(),train.getPersonNum() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"Train.xls");//filename是下载的xls的名，建议最好用英文 
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
