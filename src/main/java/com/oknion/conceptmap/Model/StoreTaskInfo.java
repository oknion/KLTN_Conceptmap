package com.oknion.conceptmap.Model;

public class StoreTaskInfo {

	private double cmId;
	private String taskName;
	private String taskDes;
	private String datetime;
	private String listTaskUsername;
	private int taskId;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public double getCmId() {
		return cmId;
	}

	public void setCmId(double cmId) {
		this.cmId = cmId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDes() {
		return taskDes;
	}

	public void setTaskDes(String taskDes) {
		this.taskDes = taskDes;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getListTaskUsername() {
		return listTaskUsername;
	}

	public void setListTaskUsername(String listTaskUsername) {
		this.listTaskUsername = listTaskUsername;
	}

}
