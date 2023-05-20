package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Department {
    /*部门编号*/
    @NotEmpty(message="部门编号不能为空")
    private String departmentNo;
    public String getDepartmentNo(){
        return departmentNo;
    }
    public void setDepartmentNo(String departmentNo){
        this.departmentNo = departmentNo;
    }

    /*部门名称*/
    @NotEmpty(message="部门名称不能为空")
    private String departmentName;
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /*部门职责*/
    @NotEmpty(message="部门职责不能为空")
    private String zhizhe;
    public String getZhizhe() {
        return zhizhe;
    }
    public void setZhizhe(String zhizhe) {
        this.zhizhe = zhizhe;
    }

    /*成立日期*/
    @NotEmpty(message="成立日期不能为空")
    private String bornDate;
    public String getBornDate() {
        return bornDate;
    }
    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonDepartment=new JSONObject(); 
		jsonDepartment.accumulate("departmentNo", this.getDepartmentNo());
		jsonDepartment.accumulate("departmentName", this.getDepartmentName());
		jsonDepartment.accumulate("zhizhe", this.getZhizhe());
		jsonDepartment.accumulate("bornDate", this.getBornDate().length()>19?this.getBornDate().substring(0,19):this.getBornDate());
		return jsonDepartment;
    }}