package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Jixiao;

public interface JixiaoMapper {
	/*添加绩效考核信息*/
	public void addJixiao(Jixiao jixiao) throws Exception;

	/*按照查询条件分页查询绩效考核记录*/
	public ArrayList<Jixiao> queryJixiao(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有绩效考核记录*/
	public ArrayList<Jixiao> queryJixiaoList(@Param("where") String where) throws Exception;

	/*按照查询条件的绩效考核记录数*/
	public int queryJixiaoCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条绩效考核记录*/
	public Jixiao getJixiao(int jixiaoId) throws Exception;

	/*更新绩效考核记录*/
	public void updateJixiao(Jixiao jixiao) throws Exception;

	/*删除绩效考核记录*/
	public void deleteJixiao(int jixiaoId) throws Exception;

}
