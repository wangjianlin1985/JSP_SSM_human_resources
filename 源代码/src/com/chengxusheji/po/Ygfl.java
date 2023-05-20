package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Ygfl {
    /*记录id*/
    private Integer flId;
    public Integer getFlId(){
        return flId;
    }
    public void setFlId(Integer flId){
        this.flId = flId;
    }

    /*福利年份*/
    @NotEmpty(message="福利年份不能为空")
    private String year;
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }

    /*福利员工*/
    private Employee employeeObj;
    public Employee getEmployeeObj() {
        return employeeObj;
    }
    public void setEmployeeObj(Employee employeeObj) {
        this.employeeObj = employeeObj;
    }

    /*福利奖金*/
    @NotNull(message="必须输入福利奖金")
    private Float fljj;
    public Float getFljj() {
        return fljj;
    }
    public void setFljj(Float fljj) {
        this.fljj = fljj;
    }

    /*是否发放*/
    @NotEmpty(message="是否发放不能为空")
    private String sfff;
    public String getSfff() {
        return sfff;
    }
    public void setSfff(String sfff) {
        this.sfff = sfff;
    }

    /*发放日期*/
    @NotEmpty(message="发放日期不能为空")
    private String ffrq;
    public String getFfrq() {
        return ffrq;
    }
    public void setFfrq(String ffrq) {
        this.ffrq = ffrq;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonYgfl=new JSONObject(); 
		jsonYgfl.accumulate("flId", this.getFlId());
		jsonYgfl.accumulate("year", this.getYear());
		jsonYgfl.accumulate("employeeObj", this.getEmployeeObj().getName());
		jsonYgfl.accumulate("employeeObjPri", this.getEmployeeObj().getEmployeeNo());
		jsonYgfl.accumulate("fljj", this.getFljj());
		jsonYgfl.accumulate("sfff", this.getSfff());
		jsonYgfl.accumulate("ffrq", this.getFfrq());
		return jsonYgfl;
    }}