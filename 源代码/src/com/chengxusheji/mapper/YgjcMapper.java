package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Ygjc;

public interface YgjcMapper {
	/*添加员工奖惩信息*/
	public void addYgjc(Ygjc ygjc) throws Exception;

	/*按照查询条件分页查询员工奖惩记录*/
	public ArrayList<Ygjc> queryYgjc(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有员工奖惩记录*/
	public ArrayList<Ygjc> queryYgjcList(@Param("where") String where) throws Exception;

	/*按照查询条件的员工奖惩记录数*/
	public int queryYgjcCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条员工奖惩记录*/
	public Ygjc getYgjc(int jcId) throws Exception;

	/*更新员工奖惩记录*/
	public void updateYgjc(Ygjc ygjc) throws Exception;

	/*删除员工奖惩记录*/
	public void deleteYgjc(int jcId) throws Exception;

}
