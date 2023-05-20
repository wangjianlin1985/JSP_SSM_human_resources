package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Employee;
import com.chengxusheji.po.Ygfl;

import com.chengxusheji.mapper.YgflMapper;
@Service
public class YgflService {

	@Resource YgflMapper ygflMapper;
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

    /*添加员工福利记录*/
    public void addYgfl(Ygfl ygfl) throws Exception {
    	ygflMapper.addYgfl(ygfl);
    }

    /*按照查询条件分页查询员工福利记录*/
    public ArrayList<Ygfl> queryYgfl(String year,Employee employeeObj,String sfff,String ffrq,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!year.equals("")) where = where + " and t_ygfl.year like '%" + year + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null  && !employeeObj.getEmployeeNo().equals(""))  where += " and t_ygfl.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(!sfff.equals("")) where = where + " and t_ygfl.sfff like '%" + sfff + "%'";
    	if(!ffrq.equals("")) where = where + " and t_ygfl.ffrq like '%" + ffrq + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return ygflMapper.queryYgfl(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Ygfl> queryYgfl(String year,Employee employeeObj,String sfff,String ffrq) throws Exception  { 
     	String where = "where 1=1";
    	if(!year.equals("")) where = where + " and t_ygfl.year like '%" + year + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null && !employeeObj.getEmployeeNo().equals(""))  where += " and t_ygfl.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(!sfff.equals("")) where = where + " and t_ygfl.sfff like '%" + sfff + "%'";
    	if(!ffrq.equals("")) where = where + " and t_ygfl.ffrq like '%" + ffrq + "%'";
    	return ygflMapper.queryYgflList(where);
    }

    /*查询所有员工福利记录*/
    public ArrayList<Ygfl> queryAllYgfl()  throws Exception {
        return ygflMapper.queryYgflList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String year,Employee employeeObj,String sfff,String ffrq) throws Exception {
     	String where = "where 1=1";
    	if(!year.equals("")) where = where + " and t_ygfl.year like '%" + year + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null && !employeeObj.getEmployeeNo().equals(""))  where += " and t_ygfl.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(!sfff.equals("")) where = where + " and t_ygfl.sfff like '%" + sfff + "%'";
    	if(!ffrq.equals("")) where = where + " and t_ygfl.ffrq like '%" + ffrq + "%'";
        recordNumber = ygflMapper.queryYgflCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取员工福利记录*/
    public Ygfl getYgfl(int flId) throws Exception  {
        Ygfl ygfl = ygflMapper.getYgfl(flId);
        return ygfl;
    }

    /*更新员工福利记录*/
    public void updateYgfl(Ygfl ygfl) throws Exception {
        ygflMapper.updateYgfl(ygfl);
    }

    /*删除一条员工福利记录*/
    public void deleteYgfl (int flId) throws Exception {
        ygflMapper.deleteYgfl(flId);
    }

    /*删除多条员工福利信息*/
    public int deleteYgfls (String flIds) throws Exception {
    	String _flIds[] = flIds.split(",");
    	for(String _flId: _flIds) {
    		ygflMapper.deleteYgfl(Integer.parseInt(_flId));
    	}
    	return _flIds.length;
    }
}
