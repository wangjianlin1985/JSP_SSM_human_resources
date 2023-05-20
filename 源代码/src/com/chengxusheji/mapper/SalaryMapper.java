package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Salary;

public interface SalaryMapper {
	/*添加员工薪酬信息*/
	public void addSalary(Salary salary) throws Exception;

	/*按照查询条件分页查询员工薪酬记录*/
	public ArrayList<Salary> querySalary(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有员工薪酬记录*/
	public ArrayList<Salary> querySalaryList(@Param("where") String where) throws Exception;

	/*按照查询条件的员工薪酬记录数*/
	public int querySalaryCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条员工薪酬记录*/
	public Salary getSalary(int salaryId) throws Exception;

	/*更新员工薪酬记录*/
	public void updateSalary(Salary salary) throws Exception;

	/*删除员工薪酬记录*/
	public void deleteSalary(int salaryId) throws Exception;

}
