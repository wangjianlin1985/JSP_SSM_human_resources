package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Train;

public interface TrainMapper {
	/*添加公司培训信息*/
	public void addTrain(Train train) throws Exception;

	/*按照查询条件分页查询公司培训记录*/
	public ArrayList<Train> queryTrain(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有公司培训记录*/
	public ArrayList<Train> queryTrainList(@Param("where") String where) throws Exception;

	/*按照查询条件的公司培训记录数*/
	public int queryTrainCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条公司培训记录*/
	public Train getTrain(int trainId) throws Exception;

	/*更新公司培训记录*/
	public void updateTrain(Train train) throws Exception;

	/*删除公司培训记录*/
	public void deleteTrain(int trainId) throws Exception;

}
