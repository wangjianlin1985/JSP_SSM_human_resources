package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Ygjc {
    /*奖惩id*/
    private Integer jcId;
    public Integer getJcId(){
        return jcId;
    }
    public void setJcId(Integer jcId){
        this.jcId = jcId;
    }

    /*奖惩类型*/
    @NotEmpty(message="奖惩类型不能为空")
    private String jclx;
    public String getJclx() {
        return jclx;
    }
    public void setJclx(String jclx) {
        this.jclx = jclx;
    }

    /*奖惩标题*/
    @NotEmpty(message="奖惩标题不能为空")
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*奖惩内容*/
    @NotEmpty(message="奖惩内容不能为空")
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*奖惩员工*/
    private Employee employeeObj;
    public Employee getEmployeeObj() {
        return employeeObj;
    }
    public void setEmployeeObj(Employee employeeObj) {
        this.employeeObj = employeeObj;
    }

    /*奖惩时间*/
    @NotEmpty(message="奖惩时间不能为空")
    private String jcsj;
    public String getJcsj() {
        return jcsj;
    }
    public void setJcsj(String jcsj) {
        this.jcsj = jcsj;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonYgjc=new JSONObject(); 
		jsonYgjc.accumulate("jcId", this.getJcId());
		jsonYgjc.accumulate("jclx", this.getJclx());
		jsonYgjc.accumulate("title", this.getTitle());
		jsonYgjc.accumulate("content", this.getContent());
		jsonYgjc.accumulate("employeeObj", this.getEmployeeObj().getName());
		jsonYgjc.accumulate("employeeObjPri", this.getEmployeeObj().getEmployeeNo());
		jsonYgjc.accumulate("jcsj", this.getJcsj().length()>19?this.getJcsj().substring(0,19):this.getJcsj());
		return jsonYgjc;
    }}