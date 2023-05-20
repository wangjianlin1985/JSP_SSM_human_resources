package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Employee;
import com.chengxusheji.po.Jixiao;

import com.chengxusheji.mapper.JixiaoMapper;
@Service
public class JixiaoService {

	@Resource JixiaoMapper jixiaoMapper;
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

    /*添加绩效考核记录*/
    public void addJixiao(Jixiao jixiao) throws Exception {
    	jixiaoMapper.addJixiao(jixiao);
    }

    /*按照查询条件分页查询绩效考核记录*/
    public ArrayList<Jixiao> queryJixiao(String year,String jidu,Employee employeeObj,String khjg,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!year.equals("")) where = where + " and t_jixiao.year like '%" + year + "%'";
    	if(!jidu.equals("")) where = where + " and t_jixiao.jidu like '%" + jidu + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null  && !employeeObj.getEmployeeNo().equals(""))  where += " and t_jixiao.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(!khjg.equals("")) where = where + " and t_jixiao.khjg like '%" + khjg + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return jixiaoMapper.queryJixiao(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Jixiao> queryJixiao(String year,String jidu,Employee employeeObj,String khjg) throws Exception  { 
     	String where = "where 1=1";
    	if(!year.equals("")) where = where + " and t_jixiao.year like '%" + year + "%'";
    	if(!jidu.equals("")) where = where + " and t_jixiao.jidu like '%" + jidu + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null && !employeeObj.getEmployeeNo().equals(""))  where += " and t_jixiao.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(!khjg.equals("")) where = where + " and t_jixiao.khjg like '%" + khjg + "%'";
    	return jixiaoMapper.queryJixiaoList(where);
    }

    /*查询所有绩效考核记录*/
    public ArrayList<Jixiao> queryAllJixiao()  throws Exception {
        return jixiaoMapper.queryJixiaoList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String year,String jidu,Employee employeeObj,String khjg) throws Exception {
     	String where = "where 1=1";
    	if(!year.equals("")) where = where + " and t_jixiao.year like '%" + year + "%'";
    	if(!jidu.equals("")) where = where + " and t_jixiao.jidu like '%" + jidu + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null && !employeeObj.getEmployeeNo().equals(""))  where += " and t_jixiao.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(!khjg.equals("")) where = where + " and t_jixiao.khjg like '%" + khjg + "%'";
        recordNumber = jixiaoMapper.queryJixiaoCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取绩效考核记录*/
    public Jixiao getJixiao(int jixiaoId) throws Exception  {
        Jixiao jixiao = jixiaoMapper.getJixiao(jixiaoId);
        return jixiao;
    }

    /*更新绩效考核记录*/
    public void updateJixiao(Jixiao jixiao) throws Exception {
        jixiaoMapper.updateJixiao(jixiao);
    }

    /*删除一条绩效考核记录*/
    public void deleteJixiao (int jixiaoId) throws Exception {
        jixiaoMapper.deleteJixiao(jixiaoId);
    }

    /*删除多条绩效考核信息*/
    public int deleteJixiaos (String jixiaoIds) throws Exception {
    	String _jixiaoIds[] = jixiaoIds.split(",");
    	for(String _jixiaoId: _jixiaoIds) {
    		jixiaoMapper.deleteJixiao(Integer.parseInt(_jixiaoId));
    	}
    	return _jixiaoIds.length;
    }
}
