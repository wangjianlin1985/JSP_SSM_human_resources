package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Salary {
    /*薪酬id*/
    private Integer salaryId;
    public Integer getSalaryId(){
        return salaryId;
    }
    public void setSalaryId(Integer salaryId){
        this.salaryId = salaryId;
    }

    /*年份*/
    @NotEmpty(message="年份不能为空")
    private String year;
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }

    /*月份*/
    @NotEmpty(message="月份不能为空")
    private String month;
    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
    }

    /*员工*/
    private Employee employeeObj;
    public Employee getEmployeeObj() {
        return employeeObj;
    }
    public void setEmployeeObj(Employee employeeObj) {
        this.employeeObj = employeeObj;
    }

    /*基本薪金*/
    @NotNull(message="必须输入基本薪金")
    private Float baseWage;
    public Float getBaseWage() {
        return baseWage;
    }
    public void setBaseWage(Float baseWage) {
        this.baseWage = baseWage;
    }

    /*住房补贴*/
    @NotNull(message="必须输入住房补贴")
    private Float zfbt;
    public Float getZfbt() {
        return zfbt;
    }
    public void setZfbt(Float zfbt) {
        this.zfbt = zfbt;
    }

    /*餐饮补助*/
    @NotNull(message="必须输入餐饮补助")
    private Float cybz;
    public Float getCybz() {
        return cybz;
    }
    public void setCybz(Float cybz) {
        this.cybz = cybz;
    }

    /*交通补助*/
    @NotNull(message="必须输入交通补助")
    private Float jtbz;
    public Float getJtbz() {
        return jtbz;
    }
    public void setJtbz(Float jtbz) {
        this.jtbz = jtbz;
    }

    /*其他补助*/
    @NotNull(message="必须输入其他补助")
    private Float qtbz;
    public Float getQtbz() {
        return qtbz;
    }
    public void setQtbz(Float qtbz) {
        this.qtbz = qtbz;
    }

    /*全勤奖金*/
    @NotNull(message="必须输入全勤奖金")
    private Float qqjj;
    public Float getQqjj() {
        return qqjj;
    }
    public void setQqjj(Float qqjj) {
        this.qqjj = qqjj;
    }

    /*请假次数*/
    @NotNull(message="必须输入请假次数")
    private Integer qjcs;
    public Integer getQjcs() {
        return qjcs;
    }
    public void setQjcs(Integer qjcs) {
        this.qjcs = qjcs;
    }

    /*迟到次数*/
    @NotNull(message="必须输入迟到次数")
    private Integer cdcs;
    public Integer getCdcs() {
        return cdcs;
    }
    public void setCdcs(Integer cdcs) {
        this.cdcs = cdcs;
    }

    /*旷工次数*/
    @NotNull(message="必须输入旷工次数")
    private Integer kgcs;
    public Integer getKgcs() {
        return kgcs;
    }
    public void setKgcs(Integer kgcs) {
        this.kgcs = kgcs;
    }

    /*考勤扣除*/
    @NotNull(message="必须输入考勤扣除")
    private Float kqkc;
    public Float getKqkc() {
        return kqkc;
    }
    public void setKqkc(Float kqkc) {
        this.kqkc = kqkc;
    }

    /*个税扣除*/
    @NotNull(message="必须输入个税扣除")
    private Float gskc;
    public Float getGskc() {
        return gskc;
    }
    public void setGskc(Float gskc) {
        this.gskc = gskc;
    }

    /*实际工资*/
    @NotNull(message="必须输入实际工资")
    private Float sjgz;
    public Float getSjgz() {
        return sjgz;
    }
    public void setSjgz(Float sjgz) {
        this.sjgz = sjgz;
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
    	JSONObject jsonSalary=new JSONObject(); 
		jsonSalary.accumulate("salaryId", this.getSalaryId());
		jsonSalary.accumulate("year", this.getYear());
		jsonSalary.accumulate("month", this.getMonth());
		jsonSalary.accumulate("employeeObj", this.getEmployeeObj().getName());
		jsonSalary.accumulate("employeeObjPri", this.getEmployeeObj().getEmployeeNo());
		jsonSalary.accumulate("baseWage", this.getBaseWage());
		jsonSalary.accumulate("zfbt", this.getZfbt());
		jsonSalary.accumulate("cybz", this.getCybz());
		jsonSalary.accumulate("jtbz", this.getJtbz());
		jsonSalary.accumulate("qtbz", this.getQtbz());
		jsonSalary.accumulate("qqjj", this.getQqjj());
		jsonSalary.accumulate("qjcs", this.getQjcs());
		jsonSalary.accumulate("cdcs", this.getCdcs());
		jsonSalary.accumulate("kgcs", this.getKgcs());
		jsonSalary.accumulate("kqkc", this.getKqkc());
		jsonSalary.accumulate("gskc", this.getGskc());
		jsonSalary.accumulate("sjgz", this.getSjgz());
		jsonSalary.accumulate("ffrq", this.getFfrq());
		return jsonSalary;
    }}