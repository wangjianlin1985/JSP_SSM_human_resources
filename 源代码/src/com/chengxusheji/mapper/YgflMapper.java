package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Ygfl;

public interface YgflMapper {
	/*添加员工福利信息*/
	public void addYgfl(Ygfl ygfl) throws Exception;

	/*按照查询条件分页查询员工福利记录*/
	public ArrayList<Ygfl> queryYgfl(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有员工福利记录*/
	public ArrayList<Ygfl> queryYgflList(@Param("where") String where) throws Exception;

	/*按照查询条件的员工福利记录数*/
	public int queryYgflCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条员工福利记录*/
	public Ygfl getYgfl(int flId) throws Exception;

	/*更新员工福利记录*/
	public void updateYgfl(Ygfl ygfl) throws Exception;

	/*删除员工福利记录*/
	public void deleteYgfl(int flId) throws Exception;

}
