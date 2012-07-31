package com.kinview.util;

import java.io.Serializable;

public class Event implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int eventTypeID=0;
	private String eventTypeName="";
	private int mainTypeID=0;
	private String mainTypeName="";
	private int subTypeID=0;
	private String subTypeText="";
	private String reportStandard="";
	private String checkStandard="";
	
	public int getEventTypeID() {
		return eventTypeID;
	}
	public void setEventTypeID(int eventTypeID) {
		this.eventTypeID = eventTypeID;
	}
	public String getEventTypeName() {
		return eventTypeName;
	}
	public void setEventTypeName(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}
	public int getMainTypeID() {
		return mainTypeID;
	}
	public void setMainTypeID(int mainTypeID) {
		this.mainTypeID = mainTypeID;
	}
	public String getMainTypeName() {
		return mainTypeName;
	}
	public void setMainTypeName(String mainTypeName) {
		this.mainTypeName = mainTypeName;
	}
	public int getSubTypeID() {
		return subTypeID;
	}
	public void setSubTypeID(int subTypeID) {
		this.subTypeID = subTypeID;
	}
	public String getSubTypeText() {
		return subTypeText;
	}
	public void setSubTypeText(String subTypeText) {
		this.subTypeText = subTypeText;
	}
	public String getReportStandard() {
		return reportStandard;
	}
	public void setReportStandard(String reportStandard) {
		this.reportStandard = reportStandard;
	}
	public String getCheckStandard() {
		return checkStandard;
	}
	public void setCheckStandard(String checkStandard) {
		this.checkStandard = checkStandard;
	}
	
	
}
