package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Employee;
import com.chengxusheji.po.Contract;

import com.chengxusheji.mapper.ContractMapper;
@Service
public class ContractService {

	@Resource ContractMapper contractMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加员工合同记录*/
    public void addContract(Contract contract) throws Exception {
    	contractMapper.addContract(contract);
    }

    /*按照查询条件分页查询员工合同记录*/
    public ArrayList<Contract> queryContract(String contractNo,Employee employeeObj,String htlx,String startTime,String endTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!contractNo.equals("")) where = where + " and t_contract.contractNo like '%" + contractNo + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null  && !employeeObj.getEmployeeNo().equals(""))  where += " and t_contract.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(!htlx.equals("")) where = where + " and t_contract.htlx like '%" + htlx + "%'";
    	if(!startTime.equals("")) where = where + " and t_contract.startTime like '%" + startTime + "%'";
    	if(!endTime.equals("")) where = where + " and t_contract.endTime like '%" + endTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return contractMapper.queryContract(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Contract> queryContract(String contractNo,Employee employeeObj,String htlx,String startTime,String endTime) throws Exception  { 
     	String where = "where 1=1";
    	if(!contractNo.equals("")) where = where + " and t_contract.contractNo like '%" + contractNo + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null && !employeeObj.getEmployeeNo().equals(""))  where += " and t_contract.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(!htlx.equals("")) where = where + " and t_contract.htlx like '%" + htlx + "%'";
    	if(!startTime.equals("")) where = where + " and t_contract.startTime like '%" + startTime + "%'";
    	if(!endTime.equals("")) where = where + " and t_contract.endTime like '%" + endTime + "%'";
    	return contractMapper.queryContractList(where);
    }

    /*查询所有员工合同记录*/
    public ArrayList<Contract> queryAllContract()  throws Exception {
        return contractMapper.queryContractList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String contractNo,Employee employeeObj,String htlx,String startTime,String endTime) throws Exception {
     	String where = "where 1=1";
    	if(!contractNo.equals("")) where = where + " and t_contract.contractNo like '%" + contractNo + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null && !employeeObj.getEmployeeNo().equals(""))  where += " and t_contract.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(!htlx.equals("")) where = where + " and t_contract.htlx like '%" + htlx + "%'";
    	if(!startTime.equals("")) where = where + " and t_contract.startTime like '%" + startTime + "%'";
    	if(!endTime.equals("")) where = where + " and t_contract.endTime like '%" + endTime + "%'";
        recordNumber = contractMapper.queryContractCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取员工合同记录*/
    public Contract getContract(String contractNo) throws Exception  {
        Contract contract = contractMapper.getContract(contractNo);
        return contract;
    }

    /*更新员工合同记录*/
    public void updateContract(Contract contract) throws Exception {
        contractMapper.updateContract(contract);
    }

    /*删除一条员工合同记录*/
    public void deleteContract (String contractNo) throws Exception {
        contractMapper.deleteContract(contractNo);
    }

    /*删除多条员工合同信息*/
    public int deleteContracts (String contractNos) throws Exception {
    	String _contractNos[] = contractNos.split(",");
    	for(String _contractNo: _contractNos) {
    		contractMapper.deleteContract(_contractNo);
    	}
    	return _contractNos.length;
    }
}
