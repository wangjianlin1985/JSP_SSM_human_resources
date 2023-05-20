package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Employee;
import com.chengxusheji.po.Ygjc;

import com.chengxusheji.mapper.YgjcMapper;
@Service
public class YgjcService {

	@Resource YgjcMapper ygjcMapper;
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

    /*添加员工奖惩记录*/
    public void addYgjc(Ygjc ygjc) throws Exception {
    	ygjcMapper.addYgjc(ygjc);
    }

    /*按照查询条件分页查询员工奖惩记录*/
    public ArrayList<Ygjc> queryYgjc(String jclx,String title,Employee employeeObj,String jcsj,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!jclx.equals("")) where = where + " and t_ygjc.jclx like '%" + jclx + "%'";
    	if(!title.equals("")) where = where + " and t_ygjc.title like '%" + title + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null  && !employeeObj.getEmployeeNo().equals(""))  where += " and t_ygjc.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(!jcsj.equals("")) where = where + " and t_ygjc.jcsj like '%" + jcsj + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return ygjcMapper.queryYgjc(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Ygjc> queryYgjc(String jclx,String title,Employee employeeObj,String jcsj) throws Exception  { 
     	String where = "where 1=1";
    	if(!jclx.equals("")) where = where + " and t_ygjc.jclx like '%" + jclx + "%'";
    	if(!title.equals("")) where = where + " and t_ygjc.title like '%" + title + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null && !employeeObj.getEmployeeNo().equals(""))  where += " and t_ygjc.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(!jcsj.equals("")) where = where + " and t_ygjc.jcsj like '%" + jcsj + "%'";
    	return ygjcMapper.queryYgjcList(where);
    }

    /*查询所有员工奖惩记录*/
    public ArrayList<Ygjc> queryAllYgjc()  throws Exception {
        return ygjcMapper.queryYgjcList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String jclx,String title,Employee employeeObj,String jcsj) throws Exception {
     	String where = "where 1=1";
    	if(!jclx.equals("")) where = where + " and t_ygjc.jclx like '%" + jclx + "%'";
    	if(!title.equals("")) where = where + " and t_ygjc.title like '%" + title + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null && !employeeObj.getEmployeeNo().equals(""))  where += " and t_ygjc.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(!jcsj.equals("")) where = where + " and t_ygjc.jcsj like '%" + jcsj + "%'";
        recordNumber = ygjcMapper.queryYgjcCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取员工奖惩记录*/
    public Ygjc getYgjc(int jcId) throws Exception  {
        Ygjc ygjc = ygjcMapper.getYgjc(jcId);
        return ygjc;
    }

    /*更新员工奖惩记录*/
    public void updateYgjc(Ygjc ygjc) throws Exception {
        ygjcMapper.updateYgjc(ygjc);
    }

    /*删除一条员工奖惩记录*/
    public void deleteYgjc (int jcId) throws Exception {
        ygjcMapper.deleteYgjc(jcId);
    }

    /*删除多条员工奖惩信息*/
    public int deleteYgjcs (String jcIds) throws Exception {
    	String _jcIds[] = jcIds.split(",");
    	for(String _jcId: _jcIds) {
    		ygjcMapper.deleteYgjc(Integer.parseInt(_jcId));
    	}
    	return _jcIds.length;
    }
}
