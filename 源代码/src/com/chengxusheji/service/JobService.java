package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Department;
import com.chengxusheji.po.Job;

import com.chengxusheji.mapper.JobMapper;
@Service
public class JobService {

	@Resource JobMapper jobMapper;
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

    /*添加岗位招聘记录*/
    public void addJob(Job job) throws Exception {
    	jobMapper.addJob(job);
    }

    /*按照查询条件分页查询岗位招聘记录*/
    public ArrayList<Job> queryJob(Department departmentObj,String positionName,String xueli,String zpdx,String addTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != departmentObj &&  departmentObj.getDepartmentNo() != null  && !departmentObj.getDepartmentNo().equals(""))  where += " and t_job.departmentObj='" + departmentObj.getDepartmentNo() + "'";
    	if(!positionName.equals("")) where = where + " and t_job.positionName like '%" + positionName + "%'";
    	if(!xueli.equals("")) where = where + " and t_job.xueli like '%" + xueli + "%'";
    	if(!zpdx.equals("")) where = where + " and t_job.zpdx like '%" + zpdx + "%'";
    	if(!addTime.equals("")) where = where + " and t_job.addTime like '%" + addTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return jobMapper.queryJob(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Job> queryJob(Department departmentObj,String positionName,String xueli,String zpdx,String addTime) throws Exception  { 
     	String where = "where 1=1";
    	if(null != departmentObj &&  departmentObj.getDepartmentNo() != null && !departmentObj.getDepartmentNo().equals(""))  where += " and t_job.departmentObj='" + departmentObj.getDepartmentNo() + "'";
    	if(!positionName.equals("")) where = where + " and t_job.positionName like '%" + positionName + "%'";
    	if(!xueli.equals("")) where = where + " and t_job.xueli like '%" + xueli + "%'";
    	if(!zpdx.equals("")) where = where + " and t_job.zpdx like '%" + zpdx + "%'";
    	if(!addTime.equals("")) where = where + " and t_job.addTime like '%" + addTime + "%'";
    	return jobMapper.queryJobList(where);
    }

    /*查询所有岗位招聘记录*/
    public ArrayList<Job> queryAllJob()  throws Exception {
        return jobMapper.queryJobList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(Department departmentObj,String positionName,String xueli,String zpdx,String addTime) throws Exception {
     	String where = "where 1=1";
    	if(null != departmentObj &&  departmentObj.getDepartmentNo() != null && !departmentObj.getDepartmentNo().equals(""))  where += " and t_job.departmentObj='" + departmentObj.getDepartmentNo() + "'";
    	if(!positionName.equals("")) where = where + " and t_job.positionName like '%" + positionName + "%'";
    	if(!xueli.equals("")) where = where + " and t_job.xueli like '%" + xueli + "%'";
    	if(!zpdx.equals("")) where = where + " and t_job.zpdx like '%" + zpdx + "%'";
    	if(!addTime.equals("")) where = where + " and t_job.addTime like '%" + addTime + "%'";
        recordNumber = jobMapper.queryJobCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取岗位招聘记录*/
    public Job getJob(int jobId) throws Exception  {
        Job job = jobMapper.getJob(jobId);
        return job;
    }

    /*更新岗位招聘记录*/
    public void updateJob(Job job) throws Exception {
        jobMapper.updateJob(job);
    }

    /*删除一条岗位招聘记录*/
    public void deleteJob (int jobId) throws Exception {
        jobMapper.deleteJob(jobId);
    }

    /*删除多条岗位招聘信息*/
    public int deleteJobs (String jobIds) throws Exception {
    	String _jobIds[] = jobIds.split(",");
    	for(String _jobId: _jobIds) {
    		jobMapper.deleteJob(Integer.parseInt(_jobId));
    	}
    	return _jobIds.length;
    }
}
