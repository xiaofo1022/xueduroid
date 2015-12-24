package com.xiaofo1022.xueduroid.model;

import java.util.Date;
import java.util.List;

public class Answer {
	private int id;
	private Date insertDatetime;
	private Date updateDatetime;
	private String updateDatetimeLabel;
	private String title;
	private String answer;
	private int isActive;
	private int searchCount;
	private int isEasterEgg;
	private String easterCode;
	private String nextEasterTip;
	private int fansId;
	private FansAnswer fansAnswer;
	private int happyCount;
	private List<SupplementAnswer> supplementAnswerList;

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
	public int getSearchCount() {
		return searchCount;
	}
	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}
	public int getIsEasterEgg() {
		return isEasterEgg;
	}
	public void setIsEasterEgg(int isEasterEgg) {
		this.isEasterEgg = isEasterEgg;
	}
	public String getEasterCode() {
		return easterCode;
	}
	public void setEasterCode(String easterCode) {
		this.easterCode = easterCode;
	}
	public String getNextEasterTip() {
		return nextEasterTip;
	}
	public void setNextEasterTip(String nextEasterTip) {
		this.nextEasterTip = nextEasterTip;
	}
	public int getFansId() {
		return fansId;
	}
	public void setFansId(int fansId) {
		this.fansId = fansId;
	}
	public FansAnswer getFansAnswer() {
		return fansAnswer;
	}
	public void setFansAnswer(FansAnswer fansAnswer) {
		this.fansAnswer = fansAnswer;
	}
	public String getUpdateDatetimeLabel() {
		return updateDatetimeLabel;
	}
	public void setUpdateDatetimeLabel(String updateDatetimeLabel) {
		this.updateDatetimeLabel = updateDatetimeLabel;
	}
	public int getHappyCount() {
		return happyCount;
	}
	public void setHappyCount(int happyCount) {
		this.happyCount = happyCount;
	}
	public List<SupplementAnswer> getSupplementAnswerList() {
		return supplementAnswerList;
	}
	public void setSupplementAnswerList(List<SupplementAnswer> supplementAnswerList) {
		this.supplementAnswerList = supplementAnswerList;
	}
}
