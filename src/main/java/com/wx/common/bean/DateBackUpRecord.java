package com.wx.common.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class DateBackUpRecord extends CommonBean implements Serializable {


	private static final long serialVersionUID = -250058597947506913L;
	
	private Integer did;
	private String operator;
	private String filename ;
	private String filepath ;
	private Timestamp times ;
	private Integer deadline;
	private String type;
	private long timesformat;
	
	public long getTimesformat() {
		return timesformat;
	}

	public void setTimesformat(long timesformat) {
		this.timesformat = timesformat;
	}

	public Integer getDid() {
		return did;
	}

	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public Timestamp getTimes() {
		return times;
	}

	public Integer getDeadline() {
		return deadline;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public void setTimes(Timestamp times) {
		this.times = times;
	}
	

	
	
	
}
