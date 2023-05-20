package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Jixiao {
    /*绩效id*/
    private Integer jixiaoId;
    public Integer getJixiaoId(){
        return jixiaoId;
    }
    public void setJixiaoId(Integer jixiaoId){
        this.jixiaoId = jixiaoId;
    }

    /*考核年份*/
    @NotEmpty(message="考核年份不能为空")
    private String year;
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }

    /*考核季度*/
    @NotEmpty(message="考核季度不能为空")
    private String jidu;
    public String getJidu() {
        return jidu;
    }
    public void setJidu(String jidu) {
        this.jidu = jidu;
    }

    /*考核员工*/
    private Employee employeeObj;
    public Employee getEmployeeObj() {
        return employeeObj;
    }
    public void setEmployeeObj(Employee employeeObj) {
        this.employeeObj = employeeObj;
    }

    /*考核结果*/
    @NotEmpty(message="考核结果不能为空")
    private String khjg;
    public String getKhjg() {
        return khjg;
    }
    public void setKhjg(String khjg) {
        this.khjg = khjg;
    }

    /*考核备注*/
    private String khbz;
    public String getKhbz() {
        return khbz;
    }
    public void setKhbz(String khbz) {
        this.khbz = khbz;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonJixiao=new JSONObject(); 
		jsonJixiao.accumulate("jixiaoId", this.getJixiaoId());
		jsonJixiao.accumulate("year", this.getYear());
		jsonJixiao.accumulate("jidu", this.getJidu());
		jsonJixiao.accumulate("employeeObj", this.getEmployeeObj().getName());
		jsonJixiao.accumulate("employeeObjPri", this.getEmployeeObj().getEmployeeNo());
		jsonJixiao.accumulate("khjg", this.getKhjg());
		jsonJixiao.accumulate("khbz", this.getKhbz());
		return jsonJixiao;
    }}