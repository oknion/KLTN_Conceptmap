package com.oknion.conceptmap.Model;

import java.util.Date;

public class ReportInforDetail {

	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date datetimeDate;
	public int score;
	public int numError;

	public Date getDatetimeDate() {
		return datetimeDate;
	}

	public void setDatetimeDate(Date datetimeDate) {
		this.datetimeDate = datetimeDate;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getNumError() {
		return numError;
	}

	public void setNumError(int numError) {
		this.numError = numError;
	}

}
