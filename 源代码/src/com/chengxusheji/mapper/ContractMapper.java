package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Contract;

public interface ContractMapper {
	/*添加员工合同信息*/
	public void addContract(Contract contract) throws Exception;

	/*按照查询条件分页查询员工合同记录*/
	public ArrayList<Contract> queryContract(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有员工合同记录*/
	public ArrayList<Contract> queryContractList(@Param("where") String where) throws Exception;

	/*按照查询条件的员工合同记录数*/
	public int queryContractCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条员工合同记录*/
	public Contract getContract(String contractNo) throws Exception;

	/*更新员工合同记录*/
	public void updateContract(Contract contract) throws Exception;

	/*删除员工合同记录*/
	public void deleteContract(String contractNo) throws Exception;

}
