package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Train;

import com.chengxusheji.mapper.TrainMapper;
@Service
public class TrainService {

	@Resource TrainMapper trainMapper;
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

    /*添加公司培训记录*/
    public void addTrain(Train train) throws Exception {
    	trainMapper.addTrain(train);
    }

    /*按照查询条件分页查询公司培训记录*/
    public ArrayList<Train> queryTrain(String trainName,String startTime,String endTime,String teacher,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!trainName.equals("")) where = where + " and t_train.trainName like '%" + trainName + "%'";
    	if(!startTime.equals("")) where = where + " and t_train.startTime like '%" + startTime + "%'";
    	if(!endTime.equals("")) where = where + " and t_train.endTime like '%" + endTime + "%'";
    	if(!teacher.equals("")) where = where + " and t_train.teacher like '%" + teacher + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return trainMapper.queryTrain(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Train> queryTrain(String trainName,String startTime,String endTime,String teacher) throws Exception  { 
     	String where = "where 1=1";
    	if(!trainName.equals("")) where = where + " and t_train.trainName like '%" + trainName + "%'";
    	if(!startTime.equals("")) where = where + " and t_train.startTime like '%" + startTime + "%'";
    	if(!endTime.equals("")) where = where + " and t_train.endTime like '%" + endTime + "%'";
    	if(!teacher.equals("")) where = where + " and t_train.teacher like '%" + teacher + "%'";
    	return trainMapper.queryTrainList(where);
    }

    /*查询所有公司培训记录*/
    public ArrayList<Train> queryAllTrain()  throws Exception {
        return trainMapper.queryTrainList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String trainName,String startTime,String endTime,String teacher) throws Exception {
     	String where = "where 1=1";
    	if(!trainName.equals("")) where = where + " and t_train.trainName like '%" + trainName + "%'";
    	if(!startTime.equals("")) where = where + " and t_train.startTime like '%" + startTime + "%'";
    	if(!endTime.equals("")) where = where + " and t_train.endTime like '%" + endTime + "%'";
    	if(!teacher.equals("")) where = where + " and t_train.teacher like '%" + teacher + "%'";
        recordNumber = trainMapper.queryTrainCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取公司培训记录*/
    public Train getTrain(int trainId) throws Exception  {
        Train train = trainMapper.getTrain(trainId);
        return train;
    }

    /*更新公司培训记录*/
    public void updateTrain(Train train) throws Exception {
        trainMapper.updateTrain(train);
    }

    /*删除一条公司培训记录*/
    public void deleteTrain (int trainId) throws Exception {
        trainMapper.deleteTrain(trainId);
    }

    /*删除多条公司培训信息*/
    public int deleteTrains (String trainIds) throws Exception {
    	String _trainIds[] = trainIds.split(",");
    	for(String _trainId: _trainIds) {
    		trainMapper.deleteTrain(Integer.parseInt(_trainId));
    	}
    	return _trainIds.length;
    }
}
