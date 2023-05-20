package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Contract {
    /*合同编号*/
    @NotEmpty(message="合同编号不能为空")
    private String contractNo;
    public String getContractNo(){
        return contractNo;
    }
    public void setContractNo(String contractNo){
        this.contractNo = contractNo;
    }

    /*合同员工*/
    private Employee employeeObj;
    public Employee getEmployeeObj() {
        return employeeObj;
    }
    public void setEmployeeObj(Employee employeeObj) {
        this.employeeObj = employeeObj;
    }

    /*合同类型*/
    @NotEmpty(message="合同类型不能为空")
    private String htlx;
    public String getHtlx() {
        return htlx;
    }
    public void setHtlx(String htlx) {
        this.htlx = htlx;
    }

    /*合同开始时间*/
    @NotEmpty(message="合同开始时间不能为空")
    private String startTime;
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /*合同结束时间*/
    @NotEmpty(message="合同结束时间不能为空")
    private String endTime;
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /*合同内容*/
    @NotEmpty(message="合同内容不能为空")
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*合同文件*/
    private String htwj;
    public String getHtwj() {
        return htwj;
    }
    public void setHtwj(String htwj) {
        this.htwj = htwj;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonContract=new JSONObject(); 
		jsonContract.accumulate("contractNo", this.getContractNo());
		jsonContract.accumulate("employeeObj", this.getEmployeeObj().getName());
		jsonContract.accumulate("employeeObjPri", this.getEmployeeObj().getEmployeeNo());
		jsonContract.accumulate("htlx", this.getHtlx());
		jsonContract.accumulate("startTime", this.getStartTime().length()>19?this.getStartTime().substring(0,19):this.getStartTime());
		jsonContract.accumulate("endTime", this.getEndTime().length()>19?this.getEndTime().substring(0,19):this.getEndTime());
		jsonContract.accumulate("content", this.getContent());
		jsonContract.accumulate("htwj", this.getHtwj());
		return jsonContract;
    }}