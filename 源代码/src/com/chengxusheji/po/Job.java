package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Job {
    /*岗位id*/
    private Integer jobId;
    public Integer getJobId(){
        return jobId;
    }
    public void setJobId(Integer jobId){
        this.jobId = jobId;
    }

    /*招聘部门*/
    private Department departmentObj;
    public Department getDepartmentObj() {
        return departmentObj;
    }
    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
    }

    /*招聘岗位*/
    @NotEmpty(message="招聘岗位不能为空")
    private String positionName;
    public String getPositionName() {
        return positionName;
    }
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    /*招聘人数*/
    @NotNull(message="必须输入招聘人数")
    private Integer personNum;
    public Integer getPersonNum() {
        return personNum;
    }
    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }

    /*需求专业*/
    @NotEmpty(message="需求专业不能为空")
    private String needSpecial;
    public String getNeedSpecial() {
        return needSpecial;
    }
    public void setNeedSpecial(String needSpecial) {
        this.needSpecial = needSpecial;
    }

    /*学历*/
    @NotEmpty(message="学历不能为空")
    private String xueli;
    public String getXueli() {
        return xueli;
    }
    public void setXueli(String xueli) {
        this.xueli = xueli;
    }

    /*招聘对象*/
    @NotEmpty(message="招聘对象不能为空")
    private String zpdx;
    public String getZpdx() {
        return zpdx;
    }
    public void setZpdx(String zpdx) {
        this.zpdx = zpdx;
    }

    /*招聘备注*/
    private String jobMemo;
    public String getJobMemo() {
        return jobMemo;
    }
    public void setJobMemo(String jobMemo) {
        this.jobMemo = jobMemo;
    }

    /*发布时间*/
    @NotEmpty(message="发布时间不能为空")
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonJob=new JSONObject(); 
		jsonJob.accumulate("jobId", this.getJobId());
		jsonJob.accumulate("departmentObj", this.getDepartmentObj().getDepartmentName());
		jsonJob.accumulate("departmentObjPri", this.getDepartmentObj().getDepartmentNo());
		jsonJob.accumulate("positionName", this.getPositionName());
		jsonJob.accumulate("personNum", this.getPersonNum());
		jsonJob.accumulate("needSpecial", this.getNeedSpecial());
		jsonJob.accumulate("xueli", this.getXueli());
		jsonJob.accumulate("zpdx", this.getZpdx());
		jsonJob.accumulate("jobMemo", this.getJobMemo());
		jsonJob.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		return jsonJob;
    }}