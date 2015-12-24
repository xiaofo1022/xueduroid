package com.xiaofo1022.xueduroid.model;

import java.util.Date;

public class SupplementAnswer {
	private int id;
	private Date insertDatetime;
	private Date updateDatetime;
	private String updateDatetimeLabel;
	private String fansName;
	private String answer;
	private int isActive;
	private int isApproved;
	private int answerId;
	private String parentAnswerTitle;
	private String title;
	
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
	public String getUpdateDatetimeLabel() {
		return updateDatetimeLabel;
	}
	public void setUpdateDatetimeLabel(String updateDatetimeLabel) {
		this.updateDatetimeLabel = updateDatetimeLabel;
	}
	public String getFansName() {
		return fansName;
	}
	public void setFansName(String fansName) {
		this.fansName = fansName;
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
	public int getAnswerId() {
		return answerId;
	}
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
	public String getParentAnswerTitle() {
		return parentAnswerTitle;
	}
	public void setParentAnswerTitle(String parentAnswerTitle) {
		this.parentAnswerTitle = parentAnswerTitle;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
