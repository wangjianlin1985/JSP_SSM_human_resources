package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Train {
    /*培训id*/
    private Integer trainId;
    public Integer getTrainId(){
        return trainId;
    }
    public void setTrainId(Integer trainId){
        this.trainId = trainId;
    }

    /*培训名称*/
    @NotEmpty(message="培训名称不能为空")
    private String trainName;
    public String getTrainName() {
        return trainName;
    }
    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    /*培训目的*/
    @NotEmpty(message="培训目的不能为空")
    private String trainPurpose;
    public String getTrainPurpose() {
        return trainPurpose;
    }
    public void setTrainPurpose(String trainPurpose) {
        this.trainPurpose = trainPurpose;
    }

    /*开始时间*/
    @NotEmpty(message="开始时间不能为空")
    private String startTime;
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /*结束时间*/
    @NotEmpty(message="结束时间不能为空")
    private String endTime;
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /*讲师*/
    @NotEmpty(message="讲师不能为空")
    private String teacher;
    public String getTeacher() {
        return teacher;
    }
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    /*培训人数*/
    @NotNull(message="必须输入培训人数")
    private Integer personNum;
    public Integer getPersonNum() {
        return personNum;
    }
    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }

    /*培训内容*/
    @NotEmpty(message="培训内容不能为空")
    private String trainContent;
    public String getTrainContent() {
        return trainContent;
    }
    public void setTrainContent(String trainContent) {
        this.trainContent = trainContent;
    }

    /*培训备注*/
    private String trainMemo;
    public String getTrainMemo() {
        return trainMemo;
    }
    public void setTrainMemo(String trainMemo) {
        this.trainMemo = trainMemo;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonTrain=new JSONObject(); 
		jsonTrain.accumulate("trainId", this.getTrainId());
		jsonTrain.accumulate("trainName", this.getTrainName());
		jsonTrain.accumulate("trainPurpose", this.getTrainPurpose());
		jsonTrain.accumulate("startTime", this.getStartTime().length()>19?this.getStartTime().substring(0,19):this.getStartTime());
		jsonTrain.accumulate("endTime", this.getEndTime().length()>19?this.getEndTime().substring(0,19):this.getEndTime());
		jsonTrain.accumulate("teacher", this.getTeacher());
		jsonTrain.accumulate("personNum", this.getPersonNum());
		jsonTrain.accumulate("trainContent", this.getTrainContent());
		jsonTrain.accumulate("trainMemo", this.getTrainMemo());
		return jsonTrain;
    }}