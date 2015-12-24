package com.xiaofo1022.xueduroid.model;

import java.util.Date;

public class FansAnswer {
	private int id;
	private Date insertDatetime;
	private Date updateDatetime;
	private String updateDatetimeLabel;
	private String fansName;
	private String title;
	private String answer;
	private int isActive;
	private int isApproved;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getInsertDatetime() {
		return insertDatetime;
	}
	public void setInsertDatetime(Date insertDatetime) {
		this.insertDatetime = insertDatetime;
	}
	public Date getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	public String getFansName() {
		return fansName;
	}
	public void setFansName(String fansName) {
		this.fansName = fansName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public int getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(int isApproved) {
		this.isApproved = isApproved;
	}
	public String getUpdateDatetimeLabel() {
		return updateDatetimeLabel;
	}
	public void setUpdateDatetimeLabel(String updateDatetimeLabel) {
		this.updateDatetimeLabel = updateDatetimeLabel;
	}
}
